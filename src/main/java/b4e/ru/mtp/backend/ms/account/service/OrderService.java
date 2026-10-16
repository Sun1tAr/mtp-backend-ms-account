package b4e.ru.mtp.backend.ms.account.service;

import b4e.ru.mtp.backend.ms.account.api.dto.ExecuteOrderRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetOrderStatusResponse;

public interface OrderService {

    void executeOrder(ExecuteOrderRequest request);
    GetOrderStatusResponse getOrderStatus(ExecuteOrderRequest request);


}
