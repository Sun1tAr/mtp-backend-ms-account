package b4e.ru.mtp.backend.ms.account.controller;

import b4e.ru.mtp.backend.commons.dto.acc.ExecuteOrderRequest;
import b4e.ru.mtp.backend.commons.dto.acc.GetOrderStatusResponse;
import b4e.ru.mtp.backend.ms.account.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/execute")
    public ResponseEntity<?> executeOrder(@RequestBody ExecuteOrderRequest request) {
        orderService.executeOrder(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/status")
    public ResponseEntity<?> getOrderStatus(@RequestBody ExecuteOrderRequest request) {
        GetOrderStatusResponse orderStatus = orderService.getOrderStatus(request);
        return ResponseEntity.ok().body(orderStatus);
    }


}
