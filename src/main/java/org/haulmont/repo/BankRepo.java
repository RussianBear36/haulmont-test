package org.haulmont.repo;

import org.haulmont.dao.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankRepo extends JpaRepository<Bank, UUID> {
}
