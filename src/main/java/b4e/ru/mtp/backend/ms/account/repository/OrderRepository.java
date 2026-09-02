package b4e.ru.mtp.backend.ms.account.repository;

import b4e.ru.mtp.backend.ms.account.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
