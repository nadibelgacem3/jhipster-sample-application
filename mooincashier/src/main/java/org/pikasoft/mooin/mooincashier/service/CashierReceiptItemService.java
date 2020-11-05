package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptItem;
import org.pikasoft.mooin.mooincashier.repository.CashierReceiptItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierReceiptItem}.
 */
@Service
@Transactional
public class CashierReceiptItemService {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptItemService.class);

    private final CashierReceiptItemRepository cashierReceiptItemRepository;

    public CashierReceiptItemService(CashierReceiptItemRepository cashierReceiptItemRepository) {
        this.cashierReceiptItemRepository = cashierReceiptItemRepository;
    }

    /**
     * Save a cashierReceiptItem.
     *
     * @param cashierReceiptItem the entity to save.
     * @return the persisted entity.
     */
    public CashierReceiptItem save(CashierReceiptItem cashierReceiptItem) {
        log.debug("Request to save CashierReceiptItem : {}", cashierReceiptItem);
        return cashierReceiptItemRepository.save(cashierReceiptItem);
    }

    /**
     * Get all the cashierReceiptItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierReceiptItem> findAll(Pageable pageable) {
        log.debug("Request to get all CashierReceiptItems");
        return cashierReceiptItemRepository.findAll(pageable);
    }


    /**
     * Get one cashierReceiptItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierReceiptItem> findOne(Long id) {
        log.debug("Request to get CashierReceiptItem : {}", id);
        return cashierReceiptItemRepository.findById(id);
    }

    /**
     * Delete the cashierReceiptItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierReceiptItem : {}", id);
        cashierReceiptItemRepository.deleteById(id);
    }
}
