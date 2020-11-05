package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.InvoicePaymentHistory;
import org.pikasoft.mooin.mooinbase.repository.InvoicePaymentHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InvoicePaymentHistory}.
 */
@Service
@Transactional
public class InvoicePaymentHistoryService {

    private final Logger log = LoggerFactory.getLogger(InvoicePaymentHistoryService.class);

    private final InvoicePaymentHistoryRepository invoicePaymentHistoryRepository;

    public InvoicePaymentHistoryService(InvoicePaymentHistoryRepository invoicePaymentHistoryRepository) {
        this.invoicePaymentHistoryRepository = invoicePaymentHistoryRepository;
    }

    /**
     * Save a invoicePaymentHistory.
     *
     * @param invoicePaymentHistory the entity to save.
     * @return the persisted entity.
     */
    public InvoicePaymentHistory save(InvoicePaymentHistory invoicePaymentHistory) {
        log.debug("Request to save InvoicePaymentHistory : {}", invoicePaymentHistory);
        return invoicePaymentHistoryRepository.save(invoicePaymentHistory);
    }

    /**
     * Get all the invoicePaymentHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoicePaymentHistory> findAll(Pageable pageable) {
        log.debug("Request to get all InvoicePaymentHistories");
        return invoicePaymentHistoryRepository.findAll(pageable);
    }


    /**
     * Get one invoicePaymentHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoicePaymentHistory> findOne(Long id) {
        log.debug("Request to get InvoicePaymentHistory : {}", id);
        return invoicePaymentHistoryRepository.findById(id);
    }

    /**
     * Delete the invoicePaymentHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoicePaymentHistory : {}", id);
        invoicePaymentHistoryRepository.deleteById(id);
    }
}
