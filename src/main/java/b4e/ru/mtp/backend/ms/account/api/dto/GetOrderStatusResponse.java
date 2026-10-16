package b4e.ru.mtp.backend.ms.account.api.dto;

import b4e.ru.mtp.backend.ms.account.domain.OrderStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetOrderStatusResponse {

    private OrderStatusCode orderStatus;

}
