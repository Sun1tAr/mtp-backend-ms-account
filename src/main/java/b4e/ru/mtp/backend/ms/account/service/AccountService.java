package b4e.ru.mtp.backend.ms.account.service;

import b4e.ru.mtp.backend.ms.account.api.dto.CreateAccountRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccByIdResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccountsResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourceRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourcesResponse;
import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;

import java.util.UUID;

public interface AccountService {

    void createAccount(CreateAccountRequest request);
    GetAccountsResponse getAccounts();
    GetAccByIdResponse getAccountById(String id);
    GetMoneyResourcesResponse getMoneyResources(GetMoneyResourceRequest request);

    AccountEntity getAccountById(UUID id);
    void updateAccount(AccountEntity account);


}
