package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.AvoirItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AvoirItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvoirItemRepository extends JpaRepository<AvoirItem, Long> {
}
