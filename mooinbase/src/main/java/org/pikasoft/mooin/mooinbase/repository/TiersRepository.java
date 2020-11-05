package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.Tiers;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tiers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiersRepository extends JpaRepository<Tiers, Long> {
}
