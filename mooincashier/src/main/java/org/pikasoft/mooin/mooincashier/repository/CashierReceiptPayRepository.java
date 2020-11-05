package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptPay;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierReceiptPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierReceiptPayRepository extends JpaRepository<CashierReceiptPay, Long> {
}
