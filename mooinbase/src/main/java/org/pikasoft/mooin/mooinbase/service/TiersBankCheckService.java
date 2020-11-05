package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.TiersBankCheck;
import org.pikasoft.mooin.mooinbase.repository.TiersBankCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TiersBankCheck}.
 */
@Service
@Transactional
public class TiersBankCheckService {

    private final Logger log = LoggerFactory.getLogger(TiersBankCheckService.class);

    private final TiersBankCheckRepository tiersBankCheckRepository;

    public TiersBankCheckService(TiersBankCheckRepository tiersBankCheckRepository) {
        this.tiersBankCheckRepository = tiersBankCheckRepository;
    }

    /**
     * Save a tiersBankCheck.
     *
     * @param tiersBankCheck the entity to save.
     * @return the persisted entity.
     */
    public TiersBankCheck save(TiersBankCheck tiersBankCheck) {
        log.debug("Request to save TiersBankCheck : {}", tiersBankCheck);
        return tiersBankCheckRepository.save(tiersBankCheck);
    }

    /**
     * Get all the tiersBankChecks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TiersBankCheck> findAll(Pageable pageable) {
        log.debug("Request to get all TiersBankChecks");
        return tiersBankCheckRepository.findAll(pageable);
    }


    /**
     * Get one tiersBankCheck by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TiersBankCheck> findOne(Long id) {
        log.debug("Request to get TiersBankCheck : {}", id);
        return tiersBankCheckRepository.findById(id);
    }

    /**
     * Delete the tiersBankCheck by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TiersBankCheck : {}", id);
        tiersBankCheckRepository.deleteById(id);
    }
}
