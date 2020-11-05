package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.BLItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BLItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BLItemRepository extends JpaRepository<BLItem, Long> {
}
