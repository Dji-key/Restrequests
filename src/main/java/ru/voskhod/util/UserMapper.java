package ru.voskhod.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.voskhod.dto.UserDto;
import ru.voskhod.entity.User;

import java.util.Base64;

@Component
public class UserMapper {

    private ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public void toEntity(UserDto userDto, User user) {
        mapper.map(userDto, user);
        if (userDto.getImage() != null) {
            user.setImage(Base64.getDecoder().decode(userDto.getImage()));
        }
    }

    public User toEntity(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        if (userDto.getImage() != null) {
            user.setImage(Base64.getDecoder().decode(userDto.getImage()));
        }
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        if (user.getImage() != null) {
            userDto.setImage(Base64.getEncoder().encodeToString(user.getImage()));
        }
        return userDto;
    }
}
