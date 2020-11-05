package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.Movement;
import org.pikasoft.mooin.mooinbase.repository.MovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Movement}.
 */
@Service
@Transactional
public class MovementService {

    private final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;

    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    /**
     * Save a movement.
     *
     * @param movement the entity to save.
     * @return the persisted entity.
     */
    public Movement save(Movement movement) {
        log.debug("Request to save Movement : {}", movement);
        return movementRepository.save(movement);
    }

    /**
     * Get all the movements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Movement> findAll(Pageable pageable) {
        log.debug("Request to get all Movements");
        return movementRepository.findAll(pageable);
    }


    /**
     * Get one movement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Movement> findOne(Long id) {
        log.debug("Request to get Movement : {}", id);
        return movementRepository.findById(id);
    }

    /**
     * Delete the movement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Movement : {}", id);
        movementRepository.deleteById(id);
    }
}
