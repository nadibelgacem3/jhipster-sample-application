package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.TVAItem;
import org.pikasoft.mooin.mooinbase.repository.TVAItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TVAItem}.
 */
@Service
@Transactional
public class TVAItemService {

    private final Logger log = LoggerFactory.getLogger(TVAItemService.class);

    private final TVAItemRepository tVAItemRepository;

    public TVAItemService(TVAItemRepository tVAItemRepository) {
        this.tVAItemRepository = tVAItemRepository;
    }

    /**
     * Save a tVAItem.
     *
     * @param tVAItem the entity to save.
     * @return the persisted entity.
     */
    public TVAItem save(TVAItem tVAItem) {
        log.debug("Request to save TVAItem : {}", tVAItem);
        return tVAItemRepository.save(tVAItem);
    }

    /**
     * Get all the tVAItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TVAItem> findAll(Pageable pageable) {
        log.debug("Request to get all TVAItems");
        return tVAItemRepository.findAll(pageable);
    }


    /**
     * Get one tVAItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TVAItem> findOne(Long id) {
        log.debug("Request to get TVAItem : {}", id);
        return tVAItemRepository.findById(id);
    }

    /**
     * Delete the tVAItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TVAItem : {}", id);
        tVAItemRepository.deleteById(id);
    }
}
