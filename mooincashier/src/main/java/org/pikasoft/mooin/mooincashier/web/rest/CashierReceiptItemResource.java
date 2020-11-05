package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierReceiptItem;
import org.pikasoft.mooin.mooincashier.service.CashierReceiptItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierReceiptItem}.
 */
@RestController
@RequestMapping("/api")
public class CashierReceiptItemResource {

    private final Logger log = LoggerFactory.getLogger(CashierReceiptItemResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierReceiptItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierReceiptItemService cashierReceiptItemService;

    public CashierReceiptItemResource(CashierReceiptItemService cashierReceiptItemService) {
        this.cashierReceiptItemService = cashierReceiptItemService;
    }

    /**
     * {@code POST  /cashier-receipt-items} : Create a new cashierReceiptItem.
     *
     * @param cashierReceiptItem the cashierReceiptItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierReceiptItem, or with status {@code 400 (Bad Request)} if the cashierReceiptItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-receipt-items")
    public ResponseEntity<CashierReceiptItem> createCashierReceiptItem(@Valid @RequestBody CashierReceiptItem cashierReceiptItem) throws URISyntaxException {
        log.debug("REST request to save CashierReceiptItem : {}", cashierReceiptItem);
        if (cashierReceiptItem.getId() != null) {
            throw new BadRequestAlertException("A new cashierReceiptItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierReceiptItem result = cashierReceiptItemService.save(cashierReceiptItem);
        return ResponseEntity.created(new URI("/api/cashier-receipt-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-receipt-items} : Updates an existing cashierReceiptItem.
     *
     * @param cashierReceiptItem the cashierReceiptItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierReceiptItem,
     * or with status {@code 400 (Bad Request)} if the cashierReceiptItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierReceiptItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-receipt-items")
    public ResponseEntity<CashierReceiptItem> updateCashierReceiptItem(@Valid @RequestBody CashierReceiptItem cashierReceiptItem) throws URISyntaxException {
        log.debug("REST request to update CashierReceiptItem : {}", cashierReceiptItem);
        if (cashierReceiptItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierReceiptItem result = cashierReceiptItemService.save(cashierReceiptItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierReceiptItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-receipt-items} : get all the cashierReceiptItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierReceiptItems in body.
     */
    @GetMapping("/cashier-receipt-items")
    public ResponseEntity<List<CashierReceiptItem>> getAllCashierReceiptItems(Pageable pageable) {
        log.debug("REST request to get a page of CashierReceiptItems");
        Page<CashierReceiptItem> page = cashierReceiptItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-receipt-items/:id} : get the "id" cashierReceiptItem.
     *
     * @param id the id of the cashierReceiptItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierReceiptItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-receipt-items/{id}")
    public ResponseEntity<CashierReceiptItem> getCashierReceiptItem(@PathVariable Long id) {
        log.debug("REST request to get CashierReceiptItem : {}", id);
        Optional<CashierReceiptItem> cashierReceiptItem = cashierReceiptItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierReceiptItem);
    }

    /**
     * {@code DELETE  /cashier-receipt-items/:id} : delete the "id" cashierReceiptItem.
     *
     * @param id the id of the cashierReceiptItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-receipt-items/{id}")
    public ResponseEntity<Void> deleteCashierReceiptItem(@PathVariable Long id) {
        log.debug("REST request to delete CashierReceiptItem : {}", id);
        cashierReceiptItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
