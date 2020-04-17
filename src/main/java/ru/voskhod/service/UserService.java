package ru.voskhod.service;

import ru.voskhod.dto.UserDto;
import ru.voskhod.dto.UserPageDto;
import ru.voskhod.exception.NoSuchUserException;

import java.io.IOException;

public interface UserService {

    UserPageDto downloadPage(int page) throws IOException;

    UserPageDto getPage(int page);

    UserDto downloadUser(Long id) throws IOException, NoSuchUserException;

    UserDto getUser(Long id) throws NoSuchUserException;
}
