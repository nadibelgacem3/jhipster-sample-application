package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.CompanyModule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyModuleRepository extends JpaRepository<CompanyModule, Long> {
}
