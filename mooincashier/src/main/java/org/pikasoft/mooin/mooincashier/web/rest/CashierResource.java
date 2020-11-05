package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.Cashier;
import org.pikasoft.mooin.mooincashier.service.CashierService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.Cashier}.
 */
@RestController
@RequestMapping("/api")
public class CashierResource {

    private final Logger log = LoggerFactory.getLogger(CashierResource.class);

    private static final String ENTITY_NAME = "mooincashierCashier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierService cashierService;

    public CashierResource(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    /**
     * {@code POST  /cashiers} : Create a new cashier.
     *
     * @param cashier the cashier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashier, or with status {@code 400 (Bad Request)} if the cashier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashiers")
    public ResponseEntity<Cashier> createCashier(@Valid @RequestBody Cashier cashier) throws URISyntaxException {
        log.debug("REST request to save Cashier : {}", cashier);
        if (cashier.getId() != null) {
            throw new BadRequestAlertException("A new cashier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cashier result = cashierService.save(cashier);
        return ResponseEntity.created(new URI("/api/cashiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashiers} : Updates an existing cashier.
     *
     * @param cashier the cashier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashier,
     * or with status {@code 400 (Bad Request)} if the cashier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashiers")
    public ResponseEntity<Cashier> updateCashier(@Valid @RequestBody Cashier cashier) throws URISyntaxException {
        log.debug("REST request to update Cashier : {}", cashier);
        if (cashier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cashier result = cashierService.save(cashier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashier.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashiers} : get all the cashiers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashiers in body.
     */
    @GetMapping("/cashiers")
    public ResponseEntity<List<Cashier>> getAllCashiers(Pageable pageable) {
        log.debug("REST request to get a page of Cashiers");
        Page<Cashier> page = cashierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashiers/:id} : get the "id" cashier.
     *
     * @param id the id of the cashier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashiers/{id}")
    public ResponseEntity<Cashier> getCashier(@PathVariable Long id) {
        log.debug("REST request to get Cashier : {}", id);
        Optional<Cashier> cashier = cashierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashier);
    }

    /**
     * {@code DELETE  /cashiers/:id} : delete the "id" cashier.
     *
     * @param id the id of the cashier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashiers/{id}")
    public ResponseEntity<Void> deleteCashier(@PathVariable Long id) {
        log.debug("REST request to delete Cashier : {}", id);
        cashierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
