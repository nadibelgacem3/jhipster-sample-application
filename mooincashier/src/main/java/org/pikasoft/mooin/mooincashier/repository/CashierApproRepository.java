package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierAppro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierAppro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierApproRepository extends JpaRepository<CashierAppro, Long> {
}
