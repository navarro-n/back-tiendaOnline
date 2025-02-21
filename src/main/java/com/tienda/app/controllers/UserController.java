package com.tienda.app.controllers;

import com.tienda.app.models.User;
import com.tienda.app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/*
*
* http://localhost:8080/ -> Esta es la URL base para conectarnos a SpringBoot
* http://localhost:8080/api -> Si tenemos el context-path en app.properties, la URL base cambia
*
* * */

// http://localhost:8080/api/users

/*
*
* 200 -> Todo bien
* 201 -> Todo bien pero para update
* 204 -> Todo bien pero para borrar
* 400 -> Error de identification
* 401 -> Error de datos incorrectos
* 403 -> Permiso denegado
* 404 -> No se ha encontrado
* 500 -> Error en el servidor. Esto ocurre por fallo en el código
*
* */
@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

}
//TODO hacer todo lo que hay en el UserService
