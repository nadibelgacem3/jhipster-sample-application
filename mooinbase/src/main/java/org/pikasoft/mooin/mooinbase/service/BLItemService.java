package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.BLItem;
import org.pikasoft.mooin.mooinbase.repository.BLItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BLItem}.
 */
@Service
@Transactional
public class BLItemService {

    private final Logger log = LoggerFactory.getLogger(BLItemService.class);

    private final BLItemRepository bLItemRepository;

    public BLItemService(BLItemRepository bLItemRepository) {
        this.bLItemRepository = bLItemRepository;
    }

    /**
     * Save a bLItem.
     *
     * @param bLItem the entity to save.
     * @return the persisted entity.
     */
    public BLItem save(BLItem bLItem) {
        log.debug("Request to save BLItem : {}", bLItem);
        return bLItemRepository.save(bLItem);
    }

    /**
     * Get all the bLItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BLItem> findAll(Pageable pageable) {
        log.debug("Request to get all BLItems");
        return bLItemRepository.findAll(pageable);
    }


    /**
     * Get one bLItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BLItem> findOne(Long id) {
        log.debug("Request to get BLItem : {}", id);
        return bLItemRepository.findById(id);
    }

    /**
     * Delete the bLItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BLItem : {}", id);
        bLItemRepository.deleteById(id);
    }
}
