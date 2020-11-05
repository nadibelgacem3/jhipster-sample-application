package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.TVA;
import org.pikasoft.mooin.mooincompanies.repository.TVARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TVA}.
 */
@Service
@Transactional
public class TVAService {

    private final Logger log = LoggerFactory.getLogger(TVAService.class);

    private final TVARepository tVARepository;

    public TVAService(TVARepository tVARepository) {
        this.tVARepository = tVARepository;
    }

    /**
     * Save a tVA.
     *
     * @param tVA the entity to save.
     * @return the persisted entity.
     */
    public TVA save(TVA tVA) {
        log.debug("Request to save TVA : {}", tVA);
        return tVARepository.save(tVA);
    }

    /**
     * Get all the tVAS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TVA> findAll(Pageable pageable) {
        log.debug("Request to get all TVAS");
        return tVARepository.findAll(pageable);
    }


    /**
     * Get one tVA by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TVA> findOne(Long id) {
        log.debug("Request to get TVA : {}", id);
        return tVARepository.findById(id);
    }

    /**
     * Delete the tVA by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TVA : {}", id);
        tVARepository.deleteById(id);
    }
}
