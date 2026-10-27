package b4e.ru.mtp.backend.ms.account.service.impl;

import b4e.ru.mtp.backend.ms.account.api.dto.ExecuteOrderRequest;
import b4e.ru.mtp.backend.ms.account.api.dto.GetOrderStatusResponse;
import b4e.ru.mtp.backend.ms.account.domain.OrderStatusCode;
import b4e.ru.mtp.backend.ms.account.entity.OrderEntity;
import b4e.ru.mtp.backend.ms.account.repository.OrderRepository;
import b4e.ru.mtp.backend.ms.account.service.OrderService;
import b4e.ru.mtp.backend.ms.account.service.OrderTxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderTxService txService;

    @Override
    public void executeOrder(ExecuteOrderRequest request) {//fixme test
        log.debug("Начало выполнения заявки [{}]", request.getOrder().getId());
        OrderEntity orderEntity = OrderEntity.builder()
                .id(request.getOrder().getId())
                .source(request.getOrder().getSource())
                .target(request.getOrder().getTarget())
                .type(request.getOrder().getType())
                .amount(request.getOrder().getAmount())
                .status(OrderStatusCode.CREATED)
        .build();
        txService.saveOrder(orderEntity);

        txService.recalculateAccAmounts(orderEntity);
        log.debug("Успешное выполнение заявки [{}]", request.getOrder().getId());
    }

    @Override
    public GetOrderStatusResponse getOrderStatus(ExecuteOrderRequest request) {//fixme test
        GetOrderStatusResponse response = new GetOrderStatusResponse();
        Optional<OrderEntity> order = orderRepository.findById(request.getOrder().getId());
        if (order.isPresent()) {
            response.setOrderStatus(order.get().getStatus());
        } else {
            response.setOrderStatus(OrderStatusCode.UNREGISTERED);
        }
        log.debug("Успешное получение статуса заявки [{}]", request.getOrder().getId());
        return response;
    }


}
