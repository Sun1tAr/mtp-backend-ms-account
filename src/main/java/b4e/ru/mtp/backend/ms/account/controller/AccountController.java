package b4e.ru.mtp.backend.ms.account.controller;

import b4e.ru.mtp.backend.ms.account.api.dto.CreateAccountRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccByIdResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccountsResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourceRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourcesResponse;
import b4e.ru.mtp.backend.ms.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<?> getAccounts() {
        GetAccountsResponse accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        accountService.createAccount(createAccountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/by-id")
    public ResponseEntity<?> getAccountById(@RequestParam String id) {
        GetAccByIdResponse account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/resources")
    public ResponseEntity<?> getResources(@RequestBody GetMoneyResourceRequest request) {
        GetMoneyResourcesResponse response = accountService.getMoneyResources(request);
        return ResponseEntity.ok(response);
    }


}
