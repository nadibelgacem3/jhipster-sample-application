package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierApproItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierApproItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierApproItemRepository extends JpaRepository<CashierApproItem, Long> {
}
