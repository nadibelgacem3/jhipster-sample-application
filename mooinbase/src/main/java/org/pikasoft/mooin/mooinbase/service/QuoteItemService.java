package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.QuoteItem;
import org.pikasoft.mooin.mooinbase.repository.QuoteItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuoteItem}.
 */
@Service
@Transactional
public class QuoteItemService {

    private final Logger log = LoggerFactory.getLogger(QuoteItemService.class);

    private final QuoteItemRepository quoteItemRepository;

    public QuoteItemService(QuoteItemRepository quoteItemRepository) {
        this.quoteItemRepository = quoteItemRepository;
    }

    /**
     * Save a quoteItem.
     *
     * @param quoteItem the entity to save.
     * @return the persisted entity.
     */
    public QuoteItem save(QuoteItem quoteItem) {
        log.debug("Request to save QuoteItem : {}", quoteItem);
        return quoteItemRepository.save(quoteItem);
    }

    /**
     * Get all the quoteItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuoteItem> findAll(Pageable pageable) {
        log.debug("Request to get all QuoteItems");
        return quoteItemRepository.findAll(pageable);
    }


    /**
     * Get one quoteItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuoteItem> findOne(Long id) {
        log.debug("Request to get QuoteItem : {}", id);
        return quoteItemRepository.findById(id);
    }

    /**
     * Delete the quoteItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuoteItem : {}", id);
        quoteItemRepository.deleteById(id);
    }
}
