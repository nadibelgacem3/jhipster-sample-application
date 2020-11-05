package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.QuoteItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuoteItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {
}
