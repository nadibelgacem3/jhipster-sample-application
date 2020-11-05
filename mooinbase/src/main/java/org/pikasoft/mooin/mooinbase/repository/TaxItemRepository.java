package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.TaxItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxItemRepository extends JpaRepository<TaxItem, Long> {
}
