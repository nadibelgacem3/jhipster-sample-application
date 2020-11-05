package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPaymentItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyBillPaymentItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyBillPaymentItemRepository extends JpaRepository<CompanyBillPaymentItem, Long> {
}
