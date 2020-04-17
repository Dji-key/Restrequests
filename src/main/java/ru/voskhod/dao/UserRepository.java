package ru.voskhod.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.voskhod.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
