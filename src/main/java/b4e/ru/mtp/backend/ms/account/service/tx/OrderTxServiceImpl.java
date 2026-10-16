package b4e.ru.mtp.backend.ms.account.service.tx;

import b4e.ru.mtp.backend.ms.account.domain.OrderStatusCode;
import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;
import b4e.ru.mtp.backend.ms.account.entity.OrderEntity;
import b4e.ru.mtp.backend.ms.account.repository.OrderRepository;
import b4e.ru.mtp.backend.ms.account.service.AccountService;
import b4e.ru.mtp.backend.ms.account.service.OrderTxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderTxServiceImpl implements OrderTxService {

    private final OrderRepository orderRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void recalculateAccAmounts(OrderEntity orderEntity) {
        log.debug("Начало перерасчета суммы на счетах по заявке [{}]", orderEntity.getId());
        AccountEntity source = null;
        AccountEntity target = null;
        switch (orderEntity.getType()) {
            case REVENUE -> {
                target = accountService.getAccountById(orderEntity.getTarget());
            }
            case EXPENSE -> {
                source = accountService.getAccountById(orderEntity.getSource());
            }
            case TRANSFER ->  {
                source = accountService.getAccountById(orderEntity.getSource());
                target = accountService.getAccountById(orderEntity.getTarget());
            }
        }

        if (source != null) {
            source.setAmount(source.getAmount() - orderEntity.getAmount());
            accountService.updateAccount(source);
        }
        if (target != null) {
            target.setAmount(target.getAmount() + orderEntity.getAmount());
            accountService.updateAccount(target);
        }

        orderEntity.setStatus(OrderStatusCode.EXECUTED);
        orderRepository.save(orderEntity);
        log.debug("Успешное завершение перерасчета суммы на счетах по заявке [{}]", orderEntity.getId());
    }

}
