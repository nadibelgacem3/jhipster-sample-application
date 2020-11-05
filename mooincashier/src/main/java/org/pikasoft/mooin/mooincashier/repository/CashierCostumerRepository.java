package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierCostumer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierCostumer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierCostumerRepository extends JpaRepository<CashierCostumer, Long> {
}
