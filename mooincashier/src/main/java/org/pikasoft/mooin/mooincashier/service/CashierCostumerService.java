package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierCostumer;
import org.pikasoft.mooin.mooincashier.repository.CashierCostumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierCostumer}.
 */
@Service
@Transactional
public class CashierCostumerService {

    private final Logger log = LoggerFactory.getLogger(CashierCostumerService.class);

    private final CashierCostumerRepository cashierCostumerRepository;

    public CashierCostumerService(CashierCostumerRepository cashierCostumerRepository) {
        this.cashierCostumerRepository = cashierCostumerRepository;
    }

    /**
     * Save a cashierCostumer.
     *
     * @param cashierCostumer the entity to save.
     * @return the persisted entity.
     */
    public CashierCostumer save(CashierCostumer cashierCostumer) {
        log.debug("Request to save CashierCostumer : {}", cashierCostumer);
        return cashierCostumerRepository.save(cashierCostumer);
    }

    /**
     * Get all the cashierCostumers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierCostumer> findAll(Pageable pageable) {
        log.debug("Request to get all CashierCostumers");
        return cashierCostumerRepository.findAll(pageable);
    }


    /**
     * Get one cashierCostumer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierCostumer> findOne(Long id) {
        log.debug("Request to get CashierCostumer : {}", id);
        return cashierCostumerRepository.findById(id);
    }

    /**
     * Delete the cashierCostumer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierCostumer : {}", id);
        cashierCostumerRepository.deleteById(id);
    }
}
