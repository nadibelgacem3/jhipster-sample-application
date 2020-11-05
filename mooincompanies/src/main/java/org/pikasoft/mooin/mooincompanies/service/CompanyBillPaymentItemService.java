package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPaymentItem;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBillPaymentItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyBillPaymentItem}.
 */
@Service
@Transactional
public class CompanyBillPaymentItemService {

    private final Logger log = LoggerFactory.getLogger(CompanyBillPaymentItemService.class);

    private final CompanyBillPaymentItemRepository companyBillPaymentItemRepository;

    public CompanyBillPaymentItemService(CompanyBillPaymentItemRepository companyBillPaymentItemRepository) {
        this.companyBillPaymentItemRepository = companyBillPaymentItemRepository;
    }

    /**
     * Save a companyBillPaymentItem.
     *
     * @param companyBillPaymentItem the entity to save.
     * @return the persisted entity.
     */
    public CompanyBillPaymentItem save(CompanyBillPaymentItem companyBillPaymentItem) {
        log.debug("Request to save CompanyBillPaymentItem : {}", companyBillPaymentItem);
        return companyBillPaymentItemRepository.save(companyBillPaymentItem);
    }

    /**
     * Get all the companyBillPaymentItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyBillPaymentItem> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyBillPaymentItems");
        return companyBillPaymentItemRepository.findAll(pageable);
    }


    /**
     * Get one companyBillPaymentItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyBillPaymentItem> findOne(Long id) {
        log.debug("Request to get CompanyBillPaymentItem : {}", id);
        return companyBillPaymentItemRepository.findById(id);
    }

    /**
     * Delete the companyBillPaymentItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyBillPaymentItem : {}", id);
        companyBillPaymentItemRepository.deleteById(id);
    }
}
