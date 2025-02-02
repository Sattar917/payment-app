package az.sattarhadjiev.mstransfer.mapper;


import az.sattarhadjiev.mstransfer.dto.response.AccountResponse;
import az.sattarhadjiev.mstransfer.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toDto(Account account);

    List<AccountResponse> toDtoList(List<Account> accounts);
}
