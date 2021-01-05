package org.haulmont.repo;

import org.haulmont.dao.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Repository
public interface CreditRepo extends JpaRepository<Credit, UUID> {
    List<Credit> findCreditsByDeletedIsFalse();
}
