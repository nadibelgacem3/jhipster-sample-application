package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierAppro;
import org.pikasoft.mooin.mooincashier.service.CashierApproService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierAppro}.
 */
@RestController
@RequestMapping("/api")
public class CashierApproResource {

    private final Logger log = LoggerFactory.getLogger(CashierApproResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierAppro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierApproService cashierApproService;

    public CashierApproResource(CashierApproService cashierApproService) {
        this.cashierApproService = cashierApproService;
    }

    /**
     * {@code POST  /cashier-appros} : Create a new cashierAppro.
     *
     * @param cashierAppro the cashierAppro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierAppro, or with status {@code 400 (Bad Request)} if the cashierAppro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-appros")
    public ResponseEntity<CashierAppro> createCashierAppro(@Valid @RequestBody CashierAppro cashierAppro) throws URISyntaxException {
        log.debug("REST request to save CashierAppro : {}", cashierAppro);
        if (cashierAppro.getId() != null) {
            throw new BadRequestAlertException("A new cashierAppro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierAppro result = cashierApproService.save(cashierAppro);
        return ResponseEntity.created(new URI("/api/cashier-appros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-appros} : Updates an existing cashierAppro.
     *
     * @param cashierAppro the cashierAppro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierAppro,
     * or with status {@code 400 (Bad Request)} if the cashierAppro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierAppro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-appros")
    public ResponseEntity<CashierAppro> updateCashierAppro(@Valid @RequestBody CashierAppro cashierAppro) throws URISyntaxException {
        log.debug("REST request to update CashierAppro : {}", cashierAppro);
        if (cashierAppro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierAppro result = cashierApproService.save(cashierAppro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierAppro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-appros} : get all the cashierAppros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierAppros in body.
     */
    @GetMapping("/cashier-appros")
    public ResponseEntity<List<CashierAppro>> getAllCashierAppros(Pageable pageable) {
        log.debug("REST request to get a page of CashierAppros");
        Page<CashierAppro> page = cashierApproService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-appros/:id} : get the "id" cashierAppro.
     *
     * @param id the id of the cashierAppro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierAppro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-appros/{id}")
    public ResponseEntity<CashierAppro> getCashierAppro(@PathVariable Long id) {
        log.debug("REST request to get CashierAppro : {}", id);
        Optional<CashierAppro> cashierAppro = cashierApproService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierAppro);
    }

    /**
     * {@code DELETE  /cashier-appros/:id} : delete the "id" cashierAppro.
     *
     * @param id the id of the cashierAppro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-appros/{id}")
    public ResponseEntity<Void> deleteCashierAppro(@PathVariable Long id) {
        log.debug("REST request to delete CashierAppro : {}", id);
        cashierApproService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
