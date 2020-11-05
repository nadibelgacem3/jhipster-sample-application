package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.Cashier;
import org.pikasoft.mooin.mooincashier.repository.CashierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cashier}.
 */
@Service
@Transactional
public class CashierService {

    private final Logger log = LoggerFactory.getLogger(CashierService.class);

    private final CashierRepository cashierRepository;

    public CashierService(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    /**
     * Save a cashier.
     *
     * @param cashier the entity to save.
     * @return the persisted entity.
     */
    public Cashier save(Cashier cashier) {
        log.debug("Request to save Cashier : {}", cashier);
        return cashierRepository.save(cashier);
    }

    /**
     * Get all the cashiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cashier> findAll(Pageable pageable) {
        log.debug("Request to get all Cashiers");
        return cashierRepository.findAll(pageable);
    }


    /**
     * Get one cashier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cashier> findOne(Long id) {
        log.debug("Request to get Cashier : {}", id);
        return cashierRepository.findById(id);
    }

    /**
     * Delete the cashier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cashier : {}", id);
        cashierRepository.deleteById(id);
    }
}
