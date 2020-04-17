package ru.voskhod.service;

import ru.voskhod.dto.UserPageDto;

import java.io.IOException;

public interface UserService {

    UserPageDto downloadPage(int page) throws IOException;

    UserPageDto getPage(int page);
}
