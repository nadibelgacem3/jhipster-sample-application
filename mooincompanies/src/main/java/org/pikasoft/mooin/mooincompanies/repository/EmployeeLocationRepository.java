package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.EmployeeLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLocationRepository extends JpaRepository<EmployeeLocation, Long> {
}
