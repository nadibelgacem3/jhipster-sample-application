package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.EmployeePayment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeePayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePaymentRepository extends JpaRepository<EmployeePayment, Long> {
}
