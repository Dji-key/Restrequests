package ru.voskhod.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.voskhod.exception.NoSuchUserException;
import ru.voskhod.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getPage(@RequestParam int page) {
        try {
            logger.info("Attempt download page");
            return new ResponseEntity<>(userService.downloadPage(page), HttpStatus.OK);
        } catch (IOException e) {
            logger.warn("Download page failed, attempt get page from database");
            return new ResponseEntity<>(userService.getPage(page), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        try {
            try {
                logger.info("Attempt download user");
                return new ResponseEntity<>(userService.downloadUser(id), HttpStatus.OK);
            } catch (IOException e) {
                logger.warn("Download use failed, attempt get user from database");
                return new ResponseEntity<>(userService.getUser(id), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (NoSuchUserException e) {
            logger.error("User not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
