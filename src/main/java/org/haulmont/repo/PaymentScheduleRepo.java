package org.haulmont.repo;

import org.haulmont.dao.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentScheduleRepo extends JpaRepository<PaymentSchedule, UUID> {
}
