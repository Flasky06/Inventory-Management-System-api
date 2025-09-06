package com.tritva.Inventory_Management.mapper;

import com.tritva.Inventory_Management.model.dto.UserDto;
import com.tritva.Inventory_Management.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a User entity to a UserDto.
     *
     * @param user The User entity to map.
     * @return The mapped UserDto.
     */
    UserDto userToUserDto(User user);
}
