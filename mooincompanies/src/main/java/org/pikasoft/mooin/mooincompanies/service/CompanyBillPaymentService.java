package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPayment;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBillPaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyBillPayment}.
 */
@Service
@Transactional
public class CompanyBillPaymentService {

    private final Logger log = LoggerFactory.getLogger(CompanyBillPaymentService.class);

    private final CompanyBillPaymentRepository companyBillPaymentRepository;

    public CompanyBillPaymentService(CompanyBillPaymentRepository companyBillPaymentRepository) {
        this.companyBillPaymentRepository = companyBillPaymentRepository;
    }

    /**
     * Save a companyBillPayment.
     *
     * @param companyBillPayment the entity to save.
     * @return the persisted entity.
     */
    public CompanyBillPayment save(CompanyBillPayment companyBillPayment) {
        log.debug("Request to save CompanyBillPayment : {}", companyBillPayment);
        return companyBillPaymentRepository.save(companyBillPayment);
    }

    /**
     * Get all the companyBillPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyBillPayment> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyBillPayments");
        return companyBillPaymentRepository.findAll(pageable);
    }


    /**
     * Get one companyBillPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyBillPayment> findOne(Long id) {
        log.debug("Request to get CompanyBillPayment : {}", id);
        return companyBillPaymentRepository.findById(id);
    }

    /**
     * Delete the companyBillPayment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyBillPayment : {}", id);
        companyBillPaymentRepository.deleteById(id);
    }
}
