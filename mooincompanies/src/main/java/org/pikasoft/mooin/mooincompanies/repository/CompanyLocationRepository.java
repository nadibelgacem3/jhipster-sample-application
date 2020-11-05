package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.CompanyLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CompanyLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long> {
}
