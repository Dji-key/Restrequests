package ru.voskhod.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.voskhod.entity.User;
import ru.voskhod.exception.NoSuchUserException;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private UserRepository userRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(Iterable<Long> indices) {
        return userRepository.findAllById(indices);
    }

    @Override
    public Page<User> findAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, 6);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) throws NoSuchUserException {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElseThrow(NoSuchUserException::new);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> save(Iterable<User> users) {
        return userRepository.saveAll(users);
    }
}
