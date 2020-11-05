package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.TransactionComp;
import org.pikasoft.mooin.mooincompanies.repository.TransactionCompRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionComp}.
 */
@Service
@Transactional
public class TransactionCompService {

    private final Logger log = LoggerFactory.getLogger(TransactionCompService.class);

    private final TransactionCompRepository transactionCompRepository;

    public TransactionCompService(TransactionCompRepository transactionCompRepository) {
        this.transactionCompRepository = transactionCompRepository;
    }

    /**
     * Save a transactionComp.
     *
     * @param transactionComp the entity to save.
     * @return the persisted entity.
     */
    public TransactionComp save(TransactionComp transactionComp) {
        log.debug("Request to save TransactionComp : {}", transactionComp);
        return transactionCompRepository.save(transactionComp);
    }

    /**
     * Get all the transactionComps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionComp> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionComps");
        return transactionCompRepository.findAll(pageable);
    }


    /**
     * Get one transactionComp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionComp> findOne(Long id) {
        log.debug("Request to get TransactionComp : {}", id);
        return transactionCompRepository.findById(id);
    }

    /**
     * Delete the transactionComp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionComp : {}", id);
        transactionCompRepository.deleteById(id);
    }
}
