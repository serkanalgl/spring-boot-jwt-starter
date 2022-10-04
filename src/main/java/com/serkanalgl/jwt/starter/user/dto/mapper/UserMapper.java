package com.serkanalgl.jwt.starter.user.dto.mapper;

import com.serkanalgl.jwt.starter.user.dto.UserCreateDTO;
import com.serkanalgl.jwt.starter.user.dto.UserDTO;
import com.serkanalgl.jwt.starter.user.dto.mapper.use.EncodedMapping;
import com.serkanalgl.jwt.starter.user.dto.mapper.use.PasswordEncoderMapper;
import com.serkanalgl.jwt.starter.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    public UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO convert(User user);

    User convert(UserDTO userDto);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User convert(UserCreateDTO userCreateDto);

}
