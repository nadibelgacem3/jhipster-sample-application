package org.pikasoft.mooin.mooincashier.repository;

import org.pikasoft.mooin.mooincashier.domain.CashierLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CashierLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CashierLocationRepository extends JpaRepository<CashierLocation, Long> {
}
