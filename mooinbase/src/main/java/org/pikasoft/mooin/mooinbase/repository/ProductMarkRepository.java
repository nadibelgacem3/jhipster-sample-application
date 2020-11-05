package org.pikasoft.mooin.mooinbase.repository;

import org.pikasoft.mooin.mooinbase.domain.ProductMark;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductMark entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMarkRepository extends JpaRepository<ProductMark, Long> {
}
