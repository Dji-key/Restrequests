package ru.voskhod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voskhod.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getPage(@RequestParam int page) {
        try {
            return new ResponseEntity<>(userService.downloadPage(page), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(userService.getPage(page), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return null;
    }
}
