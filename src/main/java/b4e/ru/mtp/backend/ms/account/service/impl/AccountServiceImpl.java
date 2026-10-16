package b4e.ru.mtp.backend.ms.account.service.impl;


import b4e.ru.mtp.backend.ms.account.api.dto.CreateAccountRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccByIdResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetAccountsResponse;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourceRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetMoneyResourcesResponse;
import b4e.ru.mtp.backend.ms.account.domain.Account;
import b4e.ru.mtp.backend.ms.account.domain.MoneyResource;
import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;
import b4e.ru.mtp.backend.ms.account.exception.MtpErrorCode;
import b4e.ru.mtp.backend.ms.account.exception.MtpException;
import b4e.ru.mtp.backend.ms.account.mapper.AccountMapper;
import b4e.ru.mtp.backend.ms.account.repository.AccountRepository;
import b4e.ru.mtp.backend.ms.account.service.AccountService;
import b4e.ru.mtp.backend.ms.account.service.RequestContextService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RequestContextService requestContextService;
    private final AccountMapper mapper;

    @Transactional
    @Override
    public void createAccount(CreateAccountRequest request) {
        Long userId = requestContextService.getUserId();
        AccountEntity account = AccountEntity.builder()
                .amount(0L)
                .title(request.getTitle())
                .description(request.getDescription())
                .ownerId(userId)
                .build();
        accountRepository.save(account);
    }

    @Override
    public GetAccountsResponse getAccounts() {
        List<AccountEntity> usersAccs = accountRepository.findUsersAccs(requestContextService.getUserId());
        List<Account> list = usersAccs.stream().map(mapper::mapEntityToAccount).toList();
        return new GetAccountsResponse(list);
    }

    @Override
    public GetAccByIdResponse getAccountById(String id) {
        Long userId = requestContextService.getUserId();
        Optional<AccountEntity> optional = accountRepository.findUserAccById(userId, UUID.fromString(id));
        if (optional.isEmpty()) {
            throw new MtpException(MtpErrorCode.ACCOUNT_NOT_FOUND);
        }
        Account account = mapper.mapEntityToAccount(optional.get());
        return new GetAccByIdResponse(account);
    }

    @Override
    public GetMoneyResourcesResponse getMoneyResources(GetMoneyResourceRequest request) {
        Long userId = requestContextService.getUserId();
        List<AccountEntity> entityList = accountRepository.findByIdsForUser(request.getFindIds(), userId);
        List<MoneyResource> list = entityList.stream().map(mapper::mapEntityToResource).toList();
        return new GetMoneyResourcesResponse(list);
    }

    @Override
    public AccountEntity getAccountById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new MtpException(MtpErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Override
    public void updateAccount(AccountEntity account) {
        accountRepository.save(account);
    }
}
