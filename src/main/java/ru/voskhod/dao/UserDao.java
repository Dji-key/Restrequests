package ru.voskhod.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.voskhod.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    List<User> findAll(Iterable<Long> indices);

    Page<User> findAll(int page);

    User save(User user);

    List<User> save(Iterable<User> users);
}
