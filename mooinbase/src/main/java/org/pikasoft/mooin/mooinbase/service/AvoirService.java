package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.Avoir;
import org.pikasoft.mooin.mooinbase.repository.AvoirRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Avoir}.
 */
@Service
@Transactional
public class AvoirService {

    private final Logger log = LoggerFactory.getLogger(AvoirService.class);

    private final AvoirRepository avoirRepository;

    public AvoirService(AvoirRepository avoirRepository) {
        this.avoirRepository = avoirRepository;
    }

    /**
     * Save a avoir.
     *
     * @param avoir the entity to save.
     * @return the persisted entity.
     */
    public Avoir save(Avoir avoir) {
        log.debug("Request to save Avoir : {}", avoir);
        return avoirRepository.save(avoir);
    }

    /**
     * Get all the avoirs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Avoir> findAll(Pageable pageable) {
        log.debug("Request to get all Avoirs");
        return avoirRepository.findAll(pageable);
    }


    /**
     * Get one avoir by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Avoir> findOne(Long id) {
        log.debug("Request to get Avoir : {}", id);
        return avoirRepository.findById(id);
    }

    /**
     * Delete the avoir by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Avoir : {}", id);
        avoirRepository.deleteById(id);
    }
}
