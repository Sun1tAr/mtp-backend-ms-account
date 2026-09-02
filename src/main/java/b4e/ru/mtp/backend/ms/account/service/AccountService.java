package b4e.ru.mtp.backend.ms.account.service;

import b4e.ru.mtp.backend.commons.dto.acc.CreateAccountRequest;
import b4e.ru.mtp.backend.commons.dto.acc.GetAccByIdResponse;
import b4e.ru.mtp.backend.commons.dto.acc.GetAccountsResponse;
import b4e.ru.mtp.backend.commons.dto.common.GetMoneyResourceRequest;
import b4e.ru.mtp.backend.commons.dto.common.GetMoneyResourcesResponse;
import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    void createAccount(CreateAccountRequest request);
    GetAccountsResponse getAccounts();
    GetAccByIdResponse getAccountById(String id);
    GetMoneyResourcesResponse getMoneyResources(GetMoneyResourceRequest request);

    AccountEntity getAccountById(UUID id);
    void updateAccount(AccountEntity account);


}
