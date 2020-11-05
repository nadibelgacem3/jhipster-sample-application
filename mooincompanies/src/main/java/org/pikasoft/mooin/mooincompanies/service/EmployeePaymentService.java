package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.EmployeePayment;
import org.pikasoft.mooin.mooincompanies.repository.EmployeePaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeePayment}.
 */
@Service
@Transactional
public class EmployeePaymentService {

    private final Logger log = LoggerFactory.getLogger(EmployeePaymentService.class);

    private final EmployeePaymentRepository employeePaymentRepository;

    public EmployeePaymentService(EmployeePaymentRepository employeePaymentRepository) {
        this.employeePaymentRepository = employeePaymentRepository;
    }

    /**
     * Save a employeePayment.
     *
     * @param employeePayment the entity to save.
     * @return the persisted entity.
     */
    public EmployeePayment save(EmployeePayment employeePayment) {
        log.debug("Request to save EmployeePayment : {}", employeePayment);
        return employeePaymentRepository.save(employeePayment);
    }

    /**
     * Get all the employeePayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeePayment> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeePayments");
        return employeePaymentRepository.findAll(pageable);
    }


    /**
     * Get one employeePayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeePayment> findOne(Long id) {
        log.debug("Request to get EmployeePayment : {}", id);
        return employeePaymentRepository.findById(id);
    }

    /**
     * Delete the employeePayment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeePayment : {}", id);
        employeePaymentRepository.deleteById(id);
    }
}
