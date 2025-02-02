package az.sattarhadjiev.mstransfer.controller;

import az.sattarhadjiev.mstransfer.dto.request.PurchaseRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.RefundRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.TopupRequestDto;
import az.sattarhadjiev.mstransfer.dto.response.TransferResponseDto;
import az.sattarhadjiev.mstransfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfers")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransferController {

    TransferService transferService;

    @PutMapping("/topup")
    public ResponseEntity<TransferResponseDto> topUp(@RequestHeader Long userId,
                                                     @Valid @RequestBody TopupRequestDto request) {
        return ResponseEntity.ok(transferService.topUp(userId, request));
    }

    @PutMapping("/purchase")
    public ResponseEntity<TransferResponseDto> purchase(@RequestHeader Long userId,
                                                        @Valid @RequestBody PurchaseRequestDto request) {
        return ResponseEntity.ok(transferService.purchase(userId, request));
    }

    @PutMapping("/refund")
    public ResponseEntity<TransferResponseDto> refund(@RequestHeader Long userId,
                                                      @Valid @RequestBody RefundRequestDto request) {
        return ResponseEntity.ok(transferService.refund(userId, request));
    }
}
