package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierAppro;
import org.pikasoft.mooin.mooincashier.repository.CashierApproRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierAppro}.
 */
@Service
@Transactional
public class CashierApproService {

    private final Logger log = LoggerFactory.getLogger(CashierApproService.class);

    private final CashierApproRepository cashierApproRepository;

    public CashierApproService(CashierApproRepository cashierApproRepository) {
        this.cashierApproRepository = cashierApproRepository;
    }

    /**
     * Save a cashierAppro.
     *
     * @param cashierAppro the entity to save.
     * @return the persisted entity.
     */
    public CashierAppro save(CashierAppro cashierAppro) {
        log.debug("Request to save CashierAppro : {}", cashierAppro);
        return cashierApproRepository.save(cashierAppro);
    }

    /**
     * Get all the cashierAppros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierAppro> findAll(Pageable pageable) {
        log.debug("Request to get all CashierAppros");
        return cashierApproRepository.findAll(pageable);
    }


    /**
     * Get one cashierAppro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierAppro> findOne(Long id) {
        log.debug("Request to get CashierAppro : {}", id);
        return cashierApproRepository.findById(id);
    }

    /**
     * Delete the cashierAppro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierAppro : {}", id);
        cashierApproRepository.deleteById(id);
    }
}
