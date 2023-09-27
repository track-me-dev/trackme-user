package com.app.trackmeuser.model.mapper;

import com.app.trackmeuser.model.User;
import com.app.trackmeuser.model.dto.SignUpRequestDTO;
import com.app.trackmeuser.model.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = UserQualifier.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedByName = {"EncodePassword"})
    User toEntity(SignUpRequestDTO dto);

    UserResponseDTO toDTO(User entity);
}
