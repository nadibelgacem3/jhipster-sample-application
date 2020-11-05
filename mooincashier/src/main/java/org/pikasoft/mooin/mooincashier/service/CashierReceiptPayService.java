package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptPay;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptPayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierReceiptPay}.
 */
@Service
@Transactional
public class CashierReceiptPayService {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptPayService.class);

    private final CashierReceiptPayRepository cashierReceiptPayRepository;

    public CashierReceiptPayService(CashierReceiptPayRepository cashierReceiptPayRepository) {
        this.cashierReceiptPayRepository = cashierReceiptPayRepository;
    }

    /**
     * Save a cashierReceiptPay.
     *
     * @param cashierReceiptPay the entity to save.
     * @return the persisted entity.
     */
    public CashierReceiptPay save(CashierReceiptPay cashierReceiptPay) {
        log.debug("Request to save CashierReceiptPay : {}", cashierReceiptPay);
        return cashierReceiptPayRepository.save(cashierReceiptPay);
    }

    /**
     * Get all the cashierReceiptPays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierReceiptPay> findAll(Pageable pageable) {
        log.debug("Request to get all CashierReceiptPays");
        return cashierReceiptPayRepository.findAll(pageable);
    }


    /**
     * Get one cashierReceiptPay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierReceiptPay> findOne(Long id) {
        log.debug("Request to get CashierReceiptPay : {}", id);
        return cashierReceiptPayRepository.findById(id);
    }

    /**
     * Delete the cashierReceiptPay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierReceiptPay : {}", id);
        cashierReceiptPayRepository.deleteById(id);
    }
}
