package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.TaxItem;
import org.pikasoft.mooin.mooinbase.service.TaxItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.TaxItem}.
 */
@RestController
@RequestMapping("/api")
public class TaxItemResource {

    private final Logger log = LoggerFactory.getLogger(TaxItemResource.class);

    private static final String ENTITY_NAME = "mooinbaseTaxItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxItemService taxItemService;

    public TaxItemResource(TaxItemService taxItemService) {
        this.taxItemService = taxItemService;
    }

    /**
     * {@code POST  /tax-items} : Create a new taxItem.
     *
     * @param taxItem the taxItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxItem, or with status {@code 400 (Bad Request)} if the taxItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-items")
    public ResponseEntity<TaxItem> createTaxItem(@Valid @RequestBody TaxItem taxItem) throws URISyntaxException {
        log.debug("REST request to save TaxItem : {}", taxItem);
        if (taxItem.getId() != null) {
            throw new BadRequestAlertException("A new taxItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxItem result = taxItemService.save(taxItem);
        return ResponseEntity.created(new URI("/api/tax-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-items} : Updates an existing taxItem.
     *
     * @param taxItem the taxItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxItem,
     * or with status {@code 400 (Bad Request)} if the taxItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-items")
    public ResponseEntity<TaxItem> updateTaxItem(@Valid @RequestBody TaxItem taxItem) throws URISyntaxException {
        log.debug("REST request to update TaxItem : {}", taxItem);
        if (taxItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxItem result = taxItemService.save(taxItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taxItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tax-items} : get all the taxItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxItems in body.
     */
    @GetMapping("/tax-items")
    public ResponseEntity<List<TaxItem>> getAllTaxItems(Pageable pageable) {
        log.debug("REST request to get a page of TaxItems");
        Page<TaxItem> page = taxItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-items/:id} : get the "id" taxItem.
     *
     * @param id the id of the taxItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-items/{id}")
    public ResponseEntity<TaxItem> getTaxItem(@PathVariable Long id) {
        log.debug("REST request to get TaxItem : {}", id);
        Optional<TaxItem> taxItem = taxItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxItem);
    }

    /**
     * {@code DELETE  /tax-items/:id} : delete the "id" taxItem.
     *
     * @param id the id of the taxItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-items/{id}")
    public ResponseEntity<Void> deleteTaxItem(@PathVariable Long id) {
        log.debug("REST request to delete TaxItem : {}", id);
        taxItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
