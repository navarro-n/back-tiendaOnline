package com.tienda.app.services;

import com.tienda.app.dtos.auth.CheckTokenRequest;
import com.tienda.app.dtos.auth.LoginRequest;
import com.tienda.app.dtos.auth.LoginResponse;
import com.tienda.app.dtos.auth.RegisterRequest;
import com.tienda.app.models.Role;
import com.tienda.app.models.User;

import com.tienda.app.models.UserInfo;
import com.tienda.app.repositories.RoleRepository;
import com.tienda.app.repositories.UserInfoRepository;
import com.tienda.app.repositories.UserRepository;
import com.tienda.app.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
Retorna al front
{
token: "jlsfosjr"
username:
password:
}
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().getRoleName().name())
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public User createUser(RegisterRequest userFromFront) {

        if (this.userRepository.existsByUsername(userFromFront.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }

        else {
            Role role = this.roleRepository.findByRoleName(userFromFront.getRoleName()).orElseThrow(
                    () -> new IllegalArgumentException("Role no permitido")
            );

            User user = new User();
            user.setUsername(userFromFront.getUsername());
            user.setPassword(
                    this.passwordEncoder.encode(userFromFront.getPassword())
            );
            user.setRole(role);
            user =this.userRepository.save(user);

            UserInfo userInfo = new UserInfo();
            userInfo.setUser(user);
            userInfo.setFirstName(userFromFront.getFirstName());
            userInfo.setLastName(userFromFront.getLastName());
            userInfo.setAddress(userFromFront.getAddress());

            this.userInfoRepository.save(userInfo);
            return user;

        }

    }
    public LoginResponse login(LoginRequest credentials) {
        //Comprobamos si el usuario existe
        User user = this.userRepository.findByUsername(credentials.getUsername()).orElseThrow(() ->
                new BadCredentialsException("User not found"));
        //Comprobamos si la contrañsea no coincide con la que tenemos en la base de datos
        if (!this.passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }


        LoginResponse loginData = new LoginResponse();
        loginData.setUsername(credentials.getUsername());
        loginData.setRole(user.getRole().getRoleName());
        loginData.setToken(this.jwtUtil.generateToken(credentials.getUsername()));

        return loginData;
    }

    public boolean checkToken(CheckTokenRequest checkTokenRequest) {
        return this.jwtUtil.validateToken(
                checkTokenRequest.getToken(),
                checkTokenRequest.getUsername()
        );
    }

}
