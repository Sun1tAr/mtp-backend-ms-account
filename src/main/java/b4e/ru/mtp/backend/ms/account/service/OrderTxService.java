package b4e.ru.mtp.backend.ms.account.service;

import b4e.ru.mtp.backend.ms.account.entity.OrderEntity;

public interface OrderTxService {

    OrderEntity saveOrder(OrderEntity orderEntity);
    void recalculateAccAmounts(OrderEntity orderEntity);

}
