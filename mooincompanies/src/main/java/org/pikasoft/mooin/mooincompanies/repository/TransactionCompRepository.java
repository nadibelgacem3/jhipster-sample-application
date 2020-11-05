package org.pikasoft.mooin.mooincompanies.repository;

import org.pikasoft.mooin.mooincompanies.domain.TransactionComp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransactionComp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionCompRepository extends JpaRepository<TransactionComp, Long> {
}
