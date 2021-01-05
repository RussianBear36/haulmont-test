package org.haulmont.repo;

import org.haulmont.dao.CreditOffer;
import org.haulmont.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditOfferRepo extends JpaRepository<CreditOffer, UUID> {
    List<CreditOffer> findAllByUser(User user);
}
