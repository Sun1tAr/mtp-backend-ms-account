package b4e.ru.mtp.backend.ms.account.service;

import b4e.ru.mtp.backend.commons.dto.acc.ExecuteOrderRequest;
import b4e.ru.mtp.backend.commons.dto.acc.GetOrderStatusResponse;
import b4e.ru.mtp.backend.ms.account.entity.OrderEntity;

public interface OrderService {

    void executeOrder(ExecuteOrderRequest request);
    GetOrderStatusResponse getOrderStatus(ExecuteOrderRequest request);


}
