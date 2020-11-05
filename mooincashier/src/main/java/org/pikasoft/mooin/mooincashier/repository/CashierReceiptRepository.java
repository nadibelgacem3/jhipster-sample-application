package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierReceipt;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierReceipt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierReceiptRepository extends JpaRepository<CashierReceipt, Long> {
}
