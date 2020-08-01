package com.rocketlog.mapper;

import com.rocketlog.dto.request.UserRequestDTO;
import com.rocketlog.dto.response.UserResponseDTO;
import com.rocketlog.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "fullName", target = "fullName"),
            @Mapping(source = "password", target = "password"),
    })
    User map(UserRequestDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "fullName", target = "fullName"),
    })
    UserResponseDTO map(User user);

    List<UserResponseDTO> map(List<User> user);
}
