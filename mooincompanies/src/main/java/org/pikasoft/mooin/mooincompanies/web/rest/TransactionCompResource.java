package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.TransactionComp;
import org.pikasoft.mooin.mooincompanies.service.TransactionCompService;
import org.pikasoft.mooin.mooincompanies.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.TransactionComp}.
 */
@RestController
@RequestMapping("/api")
public class TransactionCompResource {

    private final Logger log = LoggerFactory.getLogger(TransactionCompResource.class);

    private static final String ENTITY_NAME = "mooincompaniesTransactionComp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionCompService transactionCompService;

    public TransactionCompResource(TransactionCompService transactionCompService) {
        this.transactionCompService = transactionCompService;
    }

    /**
     * {@code POST  /transaction-comps} : Create a new transactionComp.
     *
     * @param transactionComp the transactionComp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionComp, or with status {@code 400 (Bad Request)} if the transactionComp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-comps")
    public ResponseEntity<TransactionComp> createTransactionComp(@Valid @RequestBody TransactionComp transactionComp) throws URISyntaxException {
        log.debug("REST request to save TransactionComp : {}", transactionComp);
        if (transactionComp.getId() != null) {
            throw new BadRequestAlertException("A new transactionComp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionComp result = transactionCompService.save(transactionComp);
        return ResponseEntity.created(new URI("/api/transaction-comps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-comps} : Updates an existing transactionComp.
     *
     * @param transactionComp the transactionComp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionComp,
     * or with status {@code 400 (Bad Request)} if the transactionComp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionComp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-comps")
    public ResponseEntity<TransactionComp> updateTransactionComp(@Valid @RequestBody TransactionComp transactionComp) throws URISyntaxException {
        log.debug("REST request to update TransactionComp : {}", transactionComp);
        if (transactionComp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionComp result = transactionCompService.save(transactionComp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionComp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-comps} : get all the transactionComps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionComps in body.
     */
    @GetMapping("/transaction-comps")
    public ResponseEntity<List<TransactionComp>> getAllTransactionComps(Pageable pageable) {
        log.debug("REST request to get a page of TransactionComps");
        Page<TransactionComp> page = transactionCompService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-comps/:id} : get the "id" transactionComp.
     *
     * @param id the id of the transactionComp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionComp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-comps/{id}")
    public ResponseEntity<TransactionComp> getTransactionComp(@PathVariable Long id) {
        log.debug("REST request to get TransactionComp : {}", id);
        Optional<TransactionComp> transactionComp = transactionCompService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionComp);
    }

    /**
     * {@code DELETE  /transaction-comps/:id} : delete the "id" transactionComp.
     *
     * @param id the id of the transactionComp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-comps/{id}")
    public ResponseEntity<Void> deleteTransactionComp(@PathVariable Long id) {
        log.debug("REST request to delete TransactionComp : {}", id);
        transactionCompService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
