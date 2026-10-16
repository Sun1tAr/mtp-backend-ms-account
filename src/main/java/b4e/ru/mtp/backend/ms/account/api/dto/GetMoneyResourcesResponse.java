package b4e.ru.mtp.backend.ms.account.api.dto;

import b4e.ru.mtp.backend.ms.account.domain.MoneyResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetMoneyResourcesResponse {

    List<MoneyResource> resources;

}
