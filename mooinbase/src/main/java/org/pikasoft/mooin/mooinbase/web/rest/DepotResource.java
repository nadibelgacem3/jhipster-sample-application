package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.Depot;
import org.pikasoft.mooin.mooinbase.service.DepotService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.Depot}.
 */
@RestController
@RequestMapping("/api")
public class DepotResource {

    private final Logger log = LoggerFactory.getLogger(DepotResource.class);

    private static final String ENTITY_NAME = "mooinbaseDepot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepotService depotService;

    public DepotResource(DepotService depotService) {
        this.depotService = depotService;
    }

    /**
     * {@code POST  /depots} : Create a new depot.
     *
     * @param depot the depot to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depot, or with status {@code 400 (Bad Request)} if the depot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depots")
    public ResponseEntity<Depot> createDepot(@Valid @RequestBody Depot depot) throws URISyntaxException {
        log.debug("REST request to save Depot : {}", depot);
        if (depot.getId() != null) {
            throw new BadRequestAlertException("A new depot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Depot result = depotService.save(depot);
        return ResponseEntity.created(new URI("/api/depots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depots} : Updates an existing depot.
     *
     * @param depot the depot to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depot,
     * or with status {@code 400 (Bad Request)} if the depot is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depot couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depots")
    public ResponseEntity<Depot> updateDepot(@Valid @RequestBody Depot depot) throws URISyntaxException {
        log.debug("REST request to update Depot : {}", depot);
        if (depot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Depot result = depotService.save(depot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depot.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /depots} : get all the depots.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depots in body.
     */
    @GetMapping("/depots")
    public ResponseEntity<List<Depot>> getAllDepots(Pageable pageable) {
        log.debug("REST request to get a page of Depots");
        Page<Depot> page = depotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /depots/:id} : get the "id" depot.
     *
     * @param id the id of the depot to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depot, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depots/{id}")
    public ResponseEntity<Depot> getDepot(@PathVariable Long id) {
        log.debug("REST request to get Depot : {}", id);
        Optional<Depot> depot = depotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depot);
    }

    /**
     * {@code DELETE  /depots/:id} : delete the "id" depot.
     *
     * @param id the id of the depot to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depots/{id}")
    public ResponseEntity<Void> deleteDepot(@PathVariable Long id) {
        log.debug("REST request to delete Depot : {}", id);
        depotService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
