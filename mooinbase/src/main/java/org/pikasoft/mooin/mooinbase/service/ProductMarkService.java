package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.ProductMark;
import org.pikasoft.mooin.mooinbase.repository.ProductMarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductMark}.
 */
@Service
@Transactional
public class ProductMarkService {

    private final Logger log = LoggerFactory.getLogger(ProductMarkService.class);

    private final ProductMarkRepository productMarkRepository;

    public ProductMarkService(ProductMarkRepository productMarkRepository) {
        this.productMarkRepository = productMarkRepository;
    }

    /**
     * Save a productMark.
     *
     * @param productMark the entity to save.
     * @return the persisted entity.
     */
    public ProductMark save(ProductMark productMark) {
        log.debug("Request to save ProductMark : {}", productMark);
        return productMarkRepository.save(productMark);
    }

    /**
     * Get all the productMarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductMark> findAll(Pageable pageable) {
        log.debug("Request to get all ProductMarks");
        return productMarkRepository.findAll(pageable);
    }


    /**
     * Get one productMark by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductMark> findOne(Long id) {
        log.debug("Request to get ProductMark : {}", id);
        return productMarkRepository.findById(id);
    }

    /**
     * Delete the productMark by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductMark : {}", id);
        productMarkRepository.deleteById(id);
    }
}
