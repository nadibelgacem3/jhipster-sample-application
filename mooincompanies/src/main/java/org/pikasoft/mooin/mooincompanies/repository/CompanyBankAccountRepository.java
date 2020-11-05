package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBankAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyBankAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyBankAccountRepository extends JpaRepository<CompanyBankAccount, Long> {
}
