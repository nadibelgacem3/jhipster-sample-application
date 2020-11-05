package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.TVA;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TVA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TVARepository extends JpaRepository<TVA, Long> {
}
