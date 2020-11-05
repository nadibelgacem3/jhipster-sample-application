package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPayment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyBillPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyBillPaymentRepository extends JpaRepository<CompanyBillPayment, Long> {
}
