package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.TiersLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TiersLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiersLocationRepository extends JpaRepository<TiersLocation, Long> {
}
