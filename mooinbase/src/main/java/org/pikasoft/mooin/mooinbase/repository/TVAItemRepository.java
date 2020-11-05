package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.TVAItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TVAItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TVAItemRepository extends JpaRepository<TVAItem, Long> {
}
