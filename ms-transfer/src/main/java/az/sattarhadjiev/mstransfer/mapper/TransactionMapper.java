package az.sattarhadjiev.mstransfer.mapper;

import az.sattarhadjiev.mstransfer.dto.response.TransactionResponse;
import az.sattarhadjiev.mstransfer.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toDto(Transaction transaction);

    List<TransactionResponse> toDtoList(List<Transaction> transactions);
}
