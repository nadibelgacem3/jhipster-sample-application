package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.Quote;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Quote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
