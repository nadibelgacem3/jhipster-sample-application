package org.pikasoft.mooin.mooincashier.service;

import org.pikasoft.mooin.mooincashier.domain.CashierProduct;
import org.pikasoft.mooin.mooincashier.repository.CashierProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CashierProduct}.
 */
@Service
@Transactional
public class CashierProductService {

    private final Logger log = LoggerFactory.getLogger(CashierProductService.class);

    private final CashierProductRepository cashierProductRepository;

    public CashierProductService(CashierProductRepository cashierProductRepository) {
        this.cashierProductRepository = cashierProductRepository;
    }

    /**
     * Save a cashierProduct.
     *
     * @param cashierProduct the entity to save.
     * @return the persisted entity.
     */
    public CashierProduct save(CashierProduct cashierProduct) {
        log.debug("Request to save CashierProduct : {}", cashierProduct);
        return cashierProductRepository.save(cashierProduct);
    }

    /**
     * Get all the cashierProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CashierProduct> findAll(Pageable pageable) {
        log.debug("Request to get all CashierProducts");
        return cashierProductRepository.findAll(pageable);
    }


    /**
     * Get one cashierProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CashierProduct> findOne(Long id) {
        log.debug("Request to get CashierProduct : {}", id);
        return cashierProductRepository.findById(id);
    }

    /**
     * Delete the cashierProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CashierProduct : {}", id);
        cashierProductRepository.deleteById(id);
    }
}
