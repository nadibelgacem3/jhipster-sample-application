package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.BL;
import org.pikasoft.mooin.mooinbase.repository.BLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BL}.
 */
@Service
@Transactional
public class BLService {

    private final Logger log = LoggerFactory.getLogger(BLService.class);

    private final BLRepository bLRepository;

    public BLService(BLRepository bLRepository) {
        this.bLRepository = bLRepository;
    }

    /**
     * Save a bL.
     *
     * @param bL the entity to save.
     * @return the persisted entity.
     */
    public BL save(BL bL) {
        log.debug("Request to save BL : {}", bL);
        return bLRepository.save(bL);
    }

    /**
     * Get all the bLS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BL> findAll(Pageable pageable) {
        log.debug("Request to get all BLS");
        return bLRepository.findAll(pageable);
    }


    /**
     * Get one bL by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BL> findOne(Long id) {
        log.debug("Request to get BL : {}", id);
        return bLRepository.findById(id);
    }

    /**
     * Delete the bL by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BL : {}", id);
        bLRepository.deleteById(id);
    }
}
