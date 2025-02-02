package az.sattarhadjiev.msauth.mapper;

import az.sattarhadjiev.msauth.dto.request.RegisterRequestDto;
import az.sattarhadjiev.msauth.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(RegisterRequestDto registerRq);
}