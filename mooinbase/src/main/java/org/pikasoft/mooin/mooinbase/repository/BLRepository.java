package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.BL;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BL entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BLRepository extends JpaRepository<BL, Long> {
}
