package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierProduct;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierProductRepository extends JpaRepository<CashierProduct, Long> {
}
