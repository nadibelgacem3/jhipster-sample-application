package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.AvoirItem;
import org.pikasoft.mooin.mooinbase.repository.AvoirItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AvoirItem}.
 */
@Service
@Transactional
public class AvoirItemService {

    private final Logger log = LoggerFactory.getLogger(AvoirItemService.class);

    private final AvoirItemRepository avoirItemRepository;

    public AvoirItemService(AvoirItemRepository avoirItemRepository) {
        this.avoirItemRepository = avoirItemRepository;
    }

    /**
     * Save a avoirItem.
     *
     * @param avoirItem the entity to save.
     * @return the persisted entity.
     */
    public AvoirItem save(AvoirItem avoirItem) {
        log.debug("Request to save AvoirItem : {}", avoirItem);
        return avoirItemRepository.save(avoirItem);
    }

    /**
     * Get all the avoirItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AvoirItem> findAll(Pageable pageable) {
        log.debug("Request to get all AvoirItems");
        return avoirItemRepository.findAll(pageable);
    }


    /**
     * Get one avoirItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvoirItem> findOne(Long id) {
        log.debug("Request to get AvoirItem : {}", id);
        return avoirItemRepository.findById(id);
    }

    /**
     * Delete the avoirItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AvoirItem : {}", id);
        avoirItemRepository.deleteById(id);
    }
}
