package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierApproItem;
import org.pikasoft.mooin.mooincashier.repository.CashierApproItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierApproItem}.
 */
@Service
@Transactional
public class CashierApproItemService {

    private final Logger log = LoggerFactory.getLogger(CashierApproItemService.class);

    private final CashierApproItemRepository cashierApproItemRepository;

    public CashierApproItemService(CashierApproItemRepository cashierApproItemRepository) {
        this.cashierApproItemRepository = cashierApproItemRepository;
    }

    /**
     * Save a cashierApproItem.
     *
     * @param cashierApproItem the entity to save.
     * @return the persisted entity.
     */
    public CashierApproItem save(CashierApproItem cashierApproItem) {
        log.debug("Request to save CashierApproItem : {}", cashierApproItem);
        return cashierApproItemRepository.save(cashierApproItem);
    }

    /**
     * Get all the cashierApproItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierApproItem> findAll(Pageable pageable) {
        log.debug("Request to get all CashierApproItems");
        return cashierApproItemRepository.findAll(pageable);
    }


    /**
     * Get one cashierApproItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierApproItem> findOne(Long id) {
        log.debug("Request to get CashierApproItem : {}", id);
        return cashierApproItemRepository.findById(id);
    }

    /**
     * Delete the cashierApproItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierApproItem : {}", id);
        cashierApproItemRepository.deleteById(id);
    }
}
