package ru.voskhod.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.voskhod.dto.UserDto;
import ru.voskhod.dto.UserPageDto;
import ru.voskhod.entity.User;

@Component
public class UserJsonParser {

    private ObjectMapper mapper = new ObjectMapper();

    public UserPageDto parsePage(String data) throws JsonProcessingException {
        final String s = mapper.readTree(data).toString();
        return mapper.readValue(s, UserPageDto.class);
    }

    public UserDto parseEntity(String array) throws JsonProcessingException {
        final JsonNode jsonNode = mapper.readTree(array);
        return mapper.readValue(jsonNode.path("data").toString(), UserDto.class);
    }
}
