package ru.voskhod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.voskhod.dao.UserDaoImpl;
import ru.voskhod.dto.UserDto;
import ru.voskhod.dto.UserPageDto;
import ru.voskhod.entity.User;
import ru.voskhod.util.NetworkRequester;
import ru.voskhod.util.UserJsonParser;
import ru.voskhod.util.UserMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;
    private UserJsonParser parser;
    private UserMapper mapper;
    private NetworkRequester networkRequester;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, UserJsonParser parser, UserMapper mapper, NetworkRequester networkRequester) {
        this.userDao = userDao;
        this.parser = parser;
        this.mapper = mapper;
        this.networkRequester = networkRequester;
    }

    @Override
    public UserPageDto downloadPage(int page) throws IOException {

        String pageJson = networkRequester.requestPage(page);
        UserPageDto pageDto = parser.parsePage(pageJson);
        Consumer<UserDto> downloadAvatars = (userDto) -> {
            userDto.setImage(networkRequester.requestBase64Image(userDto.getAvatar()));
        };

        pageDto.getUsers().parallelStream().forEach(downloadAvatars);
        Map<Long, UserDto> remoteUsers = new HashMap<>();
        pageDto.getUsers().forEach(userDto -> remoteUsers.put(userDto.getId(), userDto));

        List<User> users = userDao.findAll(remoteUsers.keySet());
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            Long id = next.getId();
            if (remoteUsers.get(id).equalsToEntity(next)) {
                iterator.remove();
            } else {
                mapper.toEntity(remoteUsers.get(id), next);
            }
            remoteUsers.remove(id);
        }

        remoteUsers.values().forEach(userDto -> users.add(mapper.toEntity(userDto)));

        if (!users.isEmpty()) {
            userDao.save(users);
        }

        return pageDto;
    }

    @Override
    public UserPageDto getPage(int page) {
        Page<User> usersPage = userDao.findAll(page);
        List<User> users = usersPage.get().collect(Collectors.toList());
        return new UserPageDto()
                .setPage(page).setPerPage(6)
                .setTotal(usersPage.getTotalElements())
                .setTotalPages(usersPage.getTotalPages())
                .setUsers(users.stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()));

    }
}
