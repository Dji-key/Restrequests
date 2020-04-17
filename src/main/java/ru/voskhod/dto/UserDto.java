package ru.voskhod.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.voskhod.entity.User;

import java.util.Base64;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;

    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String avatar;

    private String image;

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserDto setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getImage() {
        return image;
    }

    public UserDto setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean equalsToEntity(User user) {
        return Objects.equals(id, user.getId()) &&
                Objects.equals(email, user.getEmail()) &&
                Objects.equals(firstName, user.getFirstName()) &&
                Objects.equals(lastName, user.getLastName()) &&
                Objects.equals(image, user.getImage() == null ? null : Base64.getEncoder().encodeToString(user.getImage()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(image, userDto.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, image);
    }
}
