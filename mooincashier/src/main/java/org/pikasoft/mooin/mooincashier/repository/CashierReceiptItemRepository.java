package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierReceiptItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierReceiptItemRepository extends JpaRepository<CashierReceiptItem, Long> {
}
