package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.Tiers;
import org.pikasoft.mooin.mooinbase.repository.TiersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tiers}.
 */
@Service
@Transactional
public class TiersService {

    private final Logger log = LoggerFactory.getLogger(TiersService.class);

    private final TiersRepository tiersRepository;

    public TiersService(TiersRepository tiersRepository) {
        this.tiersRepository = tiersRepository;
    }

    /**
     * Save a tiers.
     *
     * @param tiers the entity to save.
     * @return the persisted entity.
     */
    public Tiers save(Tiers tiers) {
        log.debug("Request to save Tiers : {}", tiers);
        return tiersRepository.save(tiers);
    }

    /**
     * Get all the tiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Tiers> findAll(Pageable pageable) {
        log.debug("Request to get all Tiers");
        return tiersRepository.findAll(pageable);
    }


    /**
     * Get one tiers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tiers> findOne(Long id) {
        log.debug("Request to get Tiers : {}", id);
        return tiersRepository.findById(id);
    }

    /**
     * Delete the tiers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tiers : {}", id);
        tiersRepository.deleteById(id);
    }
}
