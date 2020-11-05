package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.Avoir;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Avoir entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvoirRepository extends JpaRepository<Avoir, Long> {
}
