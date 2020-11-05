package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.TaxItem;
import org.pikasoft.mooin.mooinbase.repository.TaxItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaxItem}.
 */
@Service
@Transactional
public class TaxItemService {

    private final Logger log = LoggerFactory.getLogger(TaxItemService.class);

    private final TaxItemRepository taxItemRepository;

    public TaxItemService(TaxItemRepository taxItemRepository) {
        this.taxItemRepository = taxItemRepository;
    }

    /**
     * Save a taxItem.
     *
     * @param taxItem the entity to save.
     * @return the persisted entity.
     */
    public TaxItem save(TaxItem taxItem) {
        log.debug("Request to save TaxItem : {}", taxItem);
        return taxItemRepository.save(taxItem);
    }

    /**
     * Get all the taxItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxItem> findAll(Pageable pageable) {
        log.debug("Request to get all TaxItems");
        return taxItemRepository.findAll(pageable);
    }


    /**
     * Get one taxItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxItem> findOne(Long id) {
        log.debug("Request to get TaxItem : {}", id);
        return taxItemRepository.findById(id);
    }

    /**
     * Delete the taxItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxItem : {}", id);
        taxItemRepository.deleteById(id);
    }
}
