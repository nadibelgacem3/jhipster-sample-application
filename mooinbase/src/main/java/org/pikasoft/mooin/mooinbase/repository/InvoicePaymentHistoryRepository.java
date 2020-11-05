package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.InvoicePaymentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InvoicePaymentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoicePaymentHistoryRepository extends JpaRepository<InvoicePaymentHistory, Long> {
}
