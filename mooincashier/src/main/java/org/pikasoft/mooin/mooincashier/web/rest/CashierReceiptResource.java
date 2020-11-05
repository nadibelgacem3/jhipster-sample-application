package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierReceipt;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptService;
import org.pikasoft.mooin.mooincashier.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierReceipt}.
 */
@RestController
@RequestMapping("/api")
public class CashierReceiptResource {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierReceipt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierReceiptService cashierReceiptService;

    public CashierReceiptResource(CashierReceiptService cashierReceiptService) {
        this.cashierReceiptService = cashierReceiptService;
    }

    /**
     * {@code POST  /cashier-receipts} : Create a new cashierReceipt.
     *
     * @param cashierReceipt the cashierReceipt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierReceipt, or with status {@code 400 (Bad Request)} if the cashierReceipt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-receipts")
    public ResponseEntity<CashierReceipt> createCashierReceipt(@Valid @RequestBody CashierReceipt cashierReceipt) throws URISyntaxException {
        log.debug("REST request to save CashierReceipt : {}", cashierReceipt);
        if (cashierReceipt.getId() != null) {
            throw new BadRequestAlertException("A new cashierReceipt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierReceipt result = cashierReceiptService.save(cashierReceipt);
        return ResponseEntity.created(new URI("/api/cashier-receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-receipts} : Updates an existing cashierReceipt.
     *
     * @param cashierReceipt the cashierReceipt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierReceipt,
     * or with status {@code 400 (Bad Request)} if the cashierReceipt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierReceipt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-receipts")
    public ResponseEntity<CashierReceipt> updateCashierReceipt(@Valid @RequestBody CashierReceipt cashierReceipt) throws URISyntaxException {
        log.debug("REST request to update CashierReceipt : {}", cashierReceipt);
        if (cashierReceipt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierReceipt result = cashierReceiptService.save(cashierReceipt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierReceipt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-receipts} : get all the cashierReceipts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierReceipts in body.
     */
    @GetMapping("/cashier-receipts")
    public ResponseEntity<List<CashierReceipt>> getAllCashierReceipts(Pageable pageable) {
        log.debug("REST request to get a page of CashierReceipts");
        Page<CashierReceipt> page = cashierReceiptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-receipts/:id} : get the "id" cashierReceipt.
     *
     * @param id the id of the cashierReceipt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierReceipt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-receipts/{id}")
    public ResponseEntity<CashierReceipt> getCashierReceipt(@PathVariable Long id) {
        log.debug("REST request to get CashierReceipt : {}", id);
        Optional<CashierReceipt> cashierReceipt = cashierReceiptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierReceipt);
    }

    /**
     * {@code DELETE  /cashier-receipts/:id} : delete the "id" cashierReceipt.
     *
     * @param id the id of the cashierReceipt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-receipts/{id}")
    public ResponseEntity<Void> deleteCashierReceipt(@PathVariable Long id) {
        log.debug("REST request to delete CashierReceipt : {}", id);
        cashierReceiptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
