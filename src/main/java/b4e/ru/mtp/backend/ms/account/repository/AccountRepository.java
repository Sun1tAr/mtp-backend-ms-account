package b4e.ru.mtp.backend.ms.account.repository;

import b4e.ru.mtp.backend.ms.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {


    @Query(value = "select c from AccountEntity c " +
            "where c.ownerId = :userId")
    List<AccountEntity> findUsersAccs(@Param("userId") Long userId);


    @Query(value = "select c from AccountEntity c " +
            "where c.ownerId = :userId and c.id = :id")
    Optional<AccountEntity> findUserAccById(@Param("userId") Long userId, @Param("id") UUID id);


    @Query(value = "select c from AccountEntity c " +
            "where c.id = :id")
    Optional<AccountEntity> findById(@Param("id") UUID id);


    @Query(value = "select a from AccountEntity a " +
            "where a.ownerId = :userId and a.id in :ids")
    List<AccountEntity> findByIdsForUser(@Param("ids") List<UUID> ids, @Param("userId") Long userId);

}
