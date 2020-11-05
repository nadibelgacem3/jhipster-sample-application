package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptPay;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptPayService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierReceiptPay}.
 */
@RestController
@RequestMapping("/api")
public class CashierReceiptPayResource {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptPayResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierReceiptPay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierReceiptPayService cashierReceiptPayService;

    public CashierReceiptPayResource(CashierReceiptPayService cashierReceiptPayService) {
        this.cashierReceiptPayService = cashierReceiptPayService;
    }

    /**
     * {@code POST  /cashier-receipt-pays} : Create a new cashierReceiptPay.
     *
     * @param cashierReceiptPay the cashierReceiptPay to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierReceiptPay, or with status {@code 400 (Bad Request)} if the cashierReceiptPay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-receipt-pays")
    public ResponseEntity<CashierReceiptPay> createCashierReceiptPay(@Valid @RequestBody CashierReceiptPay cashierReceiptPay) throws URISyntaxException {
        log.debug("REST request to save CashierReceiptPay : {}", cashierReceiptPay);
        if (cashierReceiptPay.getId() != null) {
            throw new BadRequestAlertException("A new cashierReceiptPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierReceiptPay result = cashierReceiptPayService.save(cashierReceiptPay);
        return ResponseEntity.created(new URI("/api/cashier-receipt-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-receipt-pays} : Updates an existing cashierReceiptPay.
     *
     * @param cashierReceiptPay the cashierReceiptPay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierReceiptPay,
     * or with status {@code 400 (Bad Request)} if the cashierReceiptPay is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierReceiptPay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-receipt-pays")
    public ResponseEntity<CashierReceiptPay> updateCashierReceiptPay(@Valid @RequestBody CashierReceiptPay cashierReceiptPay) throws URISyntaxException {
        log.debug("REST request to update CashierReceiptPay : {}", cashierReceiptPay);
        if (cashierReceiptPay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierReceiptPay result = cashierReceiptPayService.save(cashierReceiptPay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierReceiptPay.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-receipt-pays} : get all the cashierReceiptPays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierReceiptPays in body.
     */
    @GetMapping("/cashier-receipt-pays")
    public ResponseEntity<List<CashierReceiptPay>> getAllCashierReceiptPays(Pageable pageable) {
        log.debug("REST request to get a page of CashierReceiptPays");
        Page<CashierReceiptPay> page = cashierReceiptPayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-receipt-pays/:id} : get the "id" cashierReceiptPay.
     *
     * @param id the id of the cashierReceiptPay to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierReceiptPay, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-receipt-pays/{id}")
    public ResponseEntity<CashierReceiptPay> getCashierReceiptPay(@PathVariable Long id) {
        log.debug("REST request to get CashierReceiptPay : {}", id);
        Optional<CashierReceiptPay> cashierReceiptPay = cashierReceiptPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierReceiptPay);
    }

    /**
     * {@code DELETE  /cashier-receipt-pays/:id} : delete the "id" cashierReceiptPay.
     *
     * @param id the id of the cashierReceiptPay to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-receipt-pays/{id}")
    public ResponseEntity<Void> deleteCashierReceiptPay(@PathVariable Long id) {
        log.debug("REST request to delete CashierReceiptPay : {}", id);
        cashierReceiptPayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
