package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.Tax;
import org.pikasoft.mooin.mooincompanies.service.TaxService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.Tax}.
 */
@RestController
@RequestMapping("/api")
public class TaxResource {

    private final Logger log = LoggerFactory.getLogger(TaxResource.class);

    private static final String ENTITY_NAME = "mooincompaniesTax";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxService taxService;

    public TaxResource(TaxService taxService) {
        this.taxService = taxService;
    }

    /**
     * {@code POST  /taxes} : Create a new tax.
     *
     * @param tax the tax to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tax, or with status {@code 400 (Bad Request)} if the tax has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxes")
    public ResponseEntity<Tax> createTax(@Valid @RequestBody Tax tax) throws URISyntaxException {
        log.debug("REST request to save Tax : {}", tax);
        if (tax.getId() != null) {
            throw new BadRequestAlertException("A new tax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tax result = taxService.save(tax);
        return ResponseEntity.created(new URI("/api/taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taxes} : Updates an existing tax.
     *
     * @param tax the tax to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tax,
     * or with status {@code 400 (Bad Request)} if the tax is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tax couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taxes")
    public ResponseEntity<Tax> updateTax(@Valid @RequestBody Tax tax) throws URISyntaxException {
        log.debug("REST request to update Tax : {}", tax);
        if (tax.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tax result = taxService.save(tax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tax.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /taxes} : get all the taxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxes in body.
     */
    @GetMapping("/taxes")
    public ResponseEntity<List<Tax>> getAllTaxes(Pageable pageable) {
        log.debug("REST request to get a page of Taxes");
        Page<Tax> page = taxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /taxes/:id} : get the "id" tax.
     *
     * @param id the id of the tax to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tax, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taxes/{id}")
    public ResponseEntity<Tax> getTax(@PathVariable Long id) {
        log.debug("REST request to get Tax : {}", id);
        Optional<Tax> tax = taxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tax);
    }

    /**
     * {@code DELETE  /taxes/:id} : delete the "id" tax.
     *
     * @param id the id of the tax to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taxes/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        log.debug("REST request to delete Tax : {}", id);
        taxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
