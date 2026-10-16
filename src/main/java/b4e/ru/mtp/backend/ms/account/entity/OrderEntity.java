package b4e.ru.mtp.backend.ms.account.entity;

import b4e.ru.mtp.backend.ms.account.domain.OrderStatusCode;
import b4e.ru.mtp.backend.ms.account.domain.TransactionTypeCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order")
@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "source")
    private UUID source;

    @Column(name = "target")
    private UUID target;

    @Column(name = "amount")
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionTypeCode type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusCode status;

}
