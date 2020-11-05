package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierApproItem;
import org.pikasoft.mooin.mooincashier.service.CashierApproItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierApproItem}.
 */
@RestController
@RequestMapping("/api")
public class CashierApproItemResource {

    private final Logger log = LoggerFactory.getLogger(CashierApproItemResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierApproItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierApproItemService cashierApproItemService;

    public CashierApproItemResource(CashierApproItemService cashierApproItemService) {
        this.cashierApproItemService = cashierApproItemService;
    }

    /**
     * {@code POST  /cashier-appro-items} : Create a new cashierApproItem.
     *
     * @param cashierApproItem the cashierApproItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierApproItem, or with status {@code 400 (Bad Request)} if the cashierApproItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-appro-items")
    public ResponseEntity<CashierApproItem> createCashierApproItem(@Valid @RequestBody CashierApproItem cashierApproItem) throws URISyntaxException {
        log.debug("REST request to save CashierApproItem : {}", cashierApproItem);
        if (cashierApproItem.getId() != null) {
            throw new BadRequestAlertException("A new cashierApproItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierApproItem result = cashierApproItemService.save(cashierApproItem);
        return ResponseEntity.created(new URI("/api/cashier-appro-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-appro-items} : Updates an existing cashierApproItem.
     *
     * @param cashierApproItem the cashierApproItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierApproItem,
     * or with status {@code 400 (Bad Request)} if the cashierApproItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierApproItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-appro-items")
    public ResponseEntity<CashierApproItem> updateCashierApproItem(@Valid @RequestBody CashierApproItem cashierApproItem) throws URISyntaxException {
        log.debug("REST request to update CashierApproItem : {}", cashierApproItem);
        if (cashierApproItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierApproItem result = cashierApproItemService.save(cashierApproItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierApproItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-appro-items} : get all the cashierApproItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierApproItems in body.
     */
    @GetMapping("/cashier-appro-items")
    public ResponseEntity<List<CashierApproItem>> getAllCashierApproItems(Pageable pageable) {
        log.debug("REST request to get a page of CashierApproItems");
        Page<CashierApproItem> page = cashierApproItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-appro-items/:id} : get the "id" cashierApproItem.
     *
     * @param id the id of the cashierApproItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierApproItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-appro-items/{id}")
    public ResponseEntity<CashierApproItem> getCashierApproItem(@PathVariable Long id) {
        log.debug("REST request to get CashierApproItem : {}", id);
        Optional<CashierApproItem> cashierApproItem = cashierApproItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierApproItem);
    }

    /**
     * {@code DELETE  /cashier-appro-items/:id} : delete the "id" cashierApproItem.
     *
     * @param id the id of the cashierApproItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-appro-items/{id}")
    public ResponseEntity<Void> deleteCashierApproItem(@PathVariable Long id) {
        log.debug("REST request to delete CashierApproItem : {}", id);
        cashierApproItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
