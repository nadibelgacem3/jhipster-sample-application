package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.InvoicePaymentHistory;
import org.pikasoft.mooin.mooinbase.service.InvoicePaymentHistoryService;
import org.pikasoft.mooin.mooinbase.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.InvoicePaymentHistory}.
 */
@RestController
@RequestMapping("/api")
public class InvoicePaymentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(InvoicePaymentHistoryResource.class);

    private static final String ENTITY_NAME = "mooinbaseInvoicePaymentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoicePaymentHistoryService invoicePaymentHistoryService;

    public InvoicePaymentHistoryResource(InvoicePaymentHistoryService invoicePaymentHistoryService) {
        this.invoicePaymentHistoryService = invoicePaymentHistoryService;
    }

    /**
     * {@code POST  /invoice-payment-histories} : Create a new invoicePaymentHistory.
     *
     * @param invoicePaymentHistory the invoicePaymentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoicePaymentHistory, or with status {@code 400 (Bad Request)} if the invoicePaymentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-payment-histories")
    public ResponseEntity<InvoicePaymentHistory> createInvoicePaymentHistory(@Valid @RequestBody InvoicePaymentHistory invoicePaymentHistory) throws URISyntaxException {
        log.debug("REST request to save InvoicePaymentHistory : {}", invoicePaymentHistory);
        if (invoicePaymentHistory.getId() != null) {
            throw new BadRequestAlertException("A new invoicePaymentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoicePaymentHistory result = invoicePaymentHistoryService.save(invoicePaymentHistory);
        return ResponseEntity.created(new URI("/api/invoice-payment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-payment-histories} : Updates an existing invoicePaymentHistory.
     *
     * @param invoicePaymentHistory the invoicePaymentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoicePaymentHistory,
     * or with status {@code 400 (Bad Request)} if the invoicePaymentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoicePaymentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-payment-histories")
    public ResponseEntity<InvoicePaymentHistory> updateInvoicePaymentHistory(@Valid @RequestBody InvoicePaymentHistory invoicePaymentHistory) throws URISyntaxException {
        log.debug("REST request to update InvoicePaymentHistory : {}", invoicePaymentHistory);
        if (invoicePaymentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoicePaymentHistory result = invoicePaymentHistoryService.save(invoicePaymentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoicePaymentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invoice-payment-histories} : get all the invoicePaymentHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoicePaymentHistories in body.
     */
    @GetMapping("/invoice-payment-histories")
    public ResponseEntity<List<InvoicePaymentHistory>> getAllInvoicePaymentHistories(Pageable pageable) {
        log.debug("REST request to get a page of InvoicePaymentHistories");
        Page<InvoicePaymentHistory> page = invoicePaymentHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-payment-histories/:id} : get the "id" invoicePaymentHistory.
     *
     * @param id the id of the invoicePaymentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoicePaymentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-payment-histories/{id}")
    public ResponseEntity<InvoicePaymentHistory> getInvoicePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to get InvoicePaymentHistory : {}", id);
        Optional<InvoicePaymentHistory> invoicePaymentHistory = invoicePaymentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoicePaymentHistory);
    }

    /**
     * {@code DELETE  /invoice-payment-histories/:id} : delete the "id" invoicePaymentHistory.
     *
     * @param id the id of the invoicePaymentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-payment-histories/{id}")
    public ResponseEntity<Void> deleteInvoicePaymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete InvoicePaymentHistory : {}", id);
        invoicePaymentHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
