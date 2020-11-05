package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierCostumer;
import org.pikasoft.mooin.mooincashier.service.CashierCostumerService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierCostumer}.
 */
@RestController
@RequestMapping("/api")
public class CashierCostumerResource {

    private final Logger log = LoggerFactory.getLogger(CashierCostumerResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierCostumer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierCostumerService cashierCostumerService;

    public CashierCostumerResource(CashierCostumerService cashierCostumerService) {
        this.cashierCostumerService = cashierCostumerService;
    }

    /**
     * {@code POST  /cashier-costumers} : Create a new cashierCostumer.
     *
     * @param cashierCostumer the cashierCostumer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierCostumer, or with status {@code 400 (Bad Request)} if the cashierCostumer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-costumers")
    public ResponseEntity<CashierCostumer> createCashierCostumer(@Valid @RequestBody CashierCostumer cashierCostumer) throws URISyntaxException {
        log.debug("REST request to save CashierCostumer : {}", cashierCostumer);
        if (cashierCostumer.getId() != null) {
            throw new BadRequestAlertException("A new cashierCostumer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierCostumer result = cashierCostumerService.save(cashierCostumer);
        return ResponseEntity.created(new URI("/api/cashier-costumers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-costumers} : Updates an existing cashierCostumer.
     *
     * @param cashierCostumer the cashierCostumer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierCostumer,
     * or with status {@code 400 (Bad Request)} if the cashierCostumer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierCostumer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-costumers")
    public ResponseEntity<CashierCostumer> updateCashierCostumer(@Valid @RequestBody CashierCostumer cashierCostumer) throws URISyntaxException {
        log.debug("REST request to update CashierCostumer : {}", cashierCostumer);
        if (cashierCostumer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierCostumer result = cashierCostumerService.save(cashierCostumer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierCostumer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-costumers} : get all the cashierCostumers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierCostumers in body.
     */
    @GetMapping("/cashier-costumers")
    public ResponseEntity<List<CashierCostumer>> getAllCashierCostumers(Pageable pageable) {
        log.debug("REST request to get a page of CashierCostumers");
        Page<CashierCostumer> page = cashierCostumerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-costumers/:id} : get the "id" cashierCostumer.
     *
     * @param id the id of the cashierCostumer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierCostumer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-costumers/{id}")
    public ResponseEntity<CashierCostumer> getCashierCostumer(@PathVariable Long id) {
        log.debug("REST request to get CashierCostumer : {}", id);
        Optional<CashierCostumer> cashierCostumer = cashierCostumerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierCostumer);
    }

    /**
     * {@code DELETE  /cashier-costumers/:id} : delete the "id" cashierCostumer.
     *
     * @param id the id of the cashierCostumer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-costumers/{id}")
    public ResponseEntity<Void> deleteCashierCostumer(@PathVariable Long id) {
        log.debug("REST request to delete CashierCostumer : {}", id);
        cashierCostumerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
