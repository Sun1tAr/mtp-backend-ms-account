package b4e.ru.mtp.backend.ms.account.mapper;

import b4e.ru.mtp.backend.commons.dto.domain.Account;
import b4e.ru.mtp.backend.commons.dto.domain.MoneyResource;
import b4e.ru.mtp.backend.commons.dto.domain.MoneyResourceType;
import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    Account mapEntityToAccount(AccountEntity accountEntity);

    MoneyResource mapEntityToResource(AccountEntity accountEntity);

    @AfterMapping
    default void afterMapping(@MappingTarget MoneyResource resource, AccountEntity entity) {
        resource.setType(MoneyResourceType.ACCOUNT);
    }


}
