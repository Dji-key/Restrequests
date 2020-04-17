package ru.voskhod.dao;

import org.springframework.data.domain.Page;
import ru.voskhod.entity.User;
import ru.voskhod.exception.NoSuchUserException;

import java.util.List;

public interface UserDao {

    List<User> findAll(Iterable<Long> indices);

    Page<User> findAll(int page);

    User findById(Long id) throws NoSuchUserException;

    User save(User user);

    List<User> save(Iterable<User> users);
}
