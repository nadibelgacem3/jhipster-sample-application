package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierReceipt;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierReceipt}.
 */
@Service
@Transactional
public class CashierReceiptService {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptService.class);

    private final CashierReceiptRepository cashierReceiptRepository;

    public CashierReceiptService(CashierReceiptRepository cashierReceiptRepository) {
        this.cashierReceiptRepository = cashierReceiptRepository;
    }

    /**
     * Save a cashierReceipt.
     *
     * @param cashierReceipt the entity to save.
     * @return the persisted entity.
     */
    public CashierReceipt save(CashierReceipt cashierReceipt) {
        log.debug("Request to save CashierReceipt : {}", cashierReceipt);
        return cashierReceiptRepository.save(cashierReceipt);
    }

    /**
     * Get all the cashierReceipts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierReceipt> findAll(Pageable pageable) {
        log.debug("Request to get all CashierReceipts");
        return cashierReceiptRepository.findAll(pageable);
    }


    /**
     * Get one cashierReceipt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierReceipt> findOne(Long id) {
        log.debug("Request to get CashierReceipt : {}", id);
        return cashierReceiptRepository.findById(id);
    }

    /**
     * Delete the cashierReceipt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierReceipt : {}", id);
        cashierReceiptRepository.deleteById(id);
    }
}
