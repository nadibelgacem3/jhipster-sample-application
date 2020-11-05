package org.pikasoft.mooin.mooincashier.web.rest;

import org.pikasoft.mooin.mooincashier.domain.CashierLocation;
import org.pikasoft.mooin.mooincashier.service.CashierLocationService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.pikasoft.mooin.mooincashier.domain.CashierLocation}.
 */
@RestController
@RequestMapping("/api")
public class CashierLocationResource {

    private final Logger log = LoggerFactory.getLogger(CashierLocationResource.class);

    private static final String ENTITY_NAME = "mooincashierCashierLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CashierLocationService cashierLocationService;

    public CashierLocationResource(CashierLocationService cashierLocationService) {
        this.cashierLocationService = cashierLocationService;
    }

    /**
     * {@code POST  /cashier-locations} : Create a new cashierLocation.
     *
     * @param cashierLocation the cashierLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cashierLocation, or with status {@code 400 (Bad Request)} if the cashierLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cashier-locations")
    public ResponseEntity<CashierLocation> createCashierLocation(@RequestBody CashierLocation cashierLocation) throws URISyntaxException {
        log.debug("REST request to save CashierLocation : {}", cashierLocation);
        if (cashierLocation.getId() != null) {
            throw new BadRequestAlertException("A new cashierLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashierLocation result = cashierLocationService.save(cashierLocation);
        return ResponseEntity.created(new URI("/api/cashier-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cashier-locations} : Updates an existing cashierLocation.
     *
     * @param cashierLocation the cashierLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cashierLocation,
     * or with status {@code 400 (Bad Request)} if the cashierLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cashierLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cashier-locations")
    public ResponseEntity<CashierLocation> updateCashierLocation(@RequestBody CashierLocation cashierLocation) throws URISyntaxException {
        log.debug("REST request to update CashierLocation : {}", cashierLocation);
        if (cashierLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CashierLocation result = cashierLocationService.save(cashierLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cashierLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cashier-locations} : get all the cashierLocations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cashierLocations in body.
     */
    @GetMapping("/cashier-locations")
    public ResponseEntity<List<CashierLocation>> getAllCashierLocations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("cashier-is-null".equals(filter)) {
            log.debug("REST request to get all CashierLocations where cashier is null");
            return new ResponseEntity<>(cashierLocationService.findAllWhereCashierIsNull(),
                    HttpStatus.OK);
        }
        if ("cashiercostumer-is-null".equals(filter)) {
            log.debug("REST request to get all CashierLocations where cashierCostumer is null");
            return new ResponseEntity<>(cashierLocationService.findAllWhereCashierCostumerIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CashierLocations");
        Page<CashierLocation> page = cashierLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cashier-locations/:id} : get the "id" cashierLocation.
     *
     * @param id the id of the cashierLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cashierLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cashier-locations/{id}")
    public ResponseEntity<CashierLocation> getCashierLocation(@PathVariable Long id) {
        log.debug("REST request to get CashierLocation : {}", id);
        Optional<CashierLocation> cashierLocation = cashierLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cashierLocation);
    }

    /**
     * {@code DELETE  /cashier-locations/:id} : delete the "id" cashierLocation.
     *
     * @param id the id of the cashierLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cashier-locations/{id}")
    public ResponseEntity<Void> deleteCashierLocation(@PathVariable Long id) {
        log.debug("REST request to delete CashierLocation : {}", id);
        cashierLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
