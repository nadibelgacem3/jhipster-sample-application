package org.pikasoft.mooin.mooinecommerce.repository;

import org.pikasoft.mooin.mooinecommerce.domain.OfferOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OfferOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferOrderRepository extends JpaRepository<OfferOrder, Long> {
}
