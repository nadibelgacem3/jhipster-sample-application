package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierLocation;
import org.pikasoft.mooin.mooincashier.repository.CashierLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link CashierLocation}.
 */
@Service
@Transactional
public class CashierLocationService {

    private final Logger log = LoggerFactory.getLogger(CashierLocationService.class);

    private final CashierLocationRepository cashierLocationRepository;

    public CashierLocationService(CashierLocationRepository cashierLocationRepository) {
        this.cashierLocationRepository = cashierLocationRepository;
    }

    /**
     * Save a cashierLocation.
     *
     * @param cashierLocation the entity to save.
     * @return the persisted entity.
     */
    public CashierLocation save(CashierLocation cashierLocation) {
        log.debug("Request to save CashierLocation : {}", cashierLocation);
        return cashierLocationRepository.save(cashierLocation);
    }

    /**
     * Get all the cashierLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierLocation> findAll(Pageable pageable) {
        log.debug("Request to get all CashierLocations");
        return cashierLocationRepository.findAll(pageable);
    }



    /**
     *  Get all the cashierLocations where Cashier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CashierLocation> findAllWhereCashierIsNull() {
        log.debug("Request to get all cashierLocations where Cashier is null");
        return StreamSupport
            .stream(cashierLocationRepository.findAll().spliterator(), false)
            .filter(cashierLocation -> cashierLocation.getCashier() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the cashierLocations where CashierCostumer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CashierLocation> findAllWhereCashierCostumerIsNull() {
        log.debug("Request to get all cashierLocations where CashierCostumer is null");
        return StreamSupport
            .stream(cashierLocationRepository.findAll().spliterator(), false)
            .filter(cashierLocation -> cashierLocation.getCashierCostumer() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one cashierLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierLocation> findOne(Long id) {
        log.debug("Request to get CashierLocation : {}", id);
        return cashierLocationRepository.findById(id);
    }

    /**
     * Delete the cashierLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierLocation : {}", id);
        cashierLocationRepository.deleteById(id);
    }
}
