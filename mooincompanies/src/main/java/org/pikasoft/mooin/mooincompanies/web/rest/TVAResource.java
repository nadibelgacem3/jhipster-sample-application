package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.TVA;
import org.pikasoft.mooin.mooincompanies.service.TVAService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.TVA}.
 */
@RestController
@RequestMapping("/api")
public class TVAResource {

    private final Logger log = LoggerFactory.getLogger(TVAResource.class);

    private static final String ENTITY_NAME = "mooincompaniesTva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TVAService tVAService;

    public TVAResource(TVAService tVAService) {
        this.tVAService = tVAService;
    }

    /**
     * {@code POST  /tvas} : Create a new tVA.
     *
     * @param tVA the tVA to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tVA, or with status {@code 400 (Bad Request)} if the tVA has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tvas")
    public ResponseEntity<TVA> createTVA(@Valid @RequestBody TVA tVA) throws URISyntaxException {
        log.debug("REST request to save TVA : {}", tVA);
        if (tVA.getId() != null) {
            throw new BadRequestAlertException("A new tVA cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TVA result = tVAService.save(tVA);
        return ResponseEntity.created(new URI("/api/tvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tvas} : Updates an existing tVA.
     *
     * @param tVA the tVA to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tVA,
     * or with status {@code 400 (Bad Request)} if the tVA is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tVA couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tvas")
    public ResponseEntity<TVA> updateTVA(@Valid @RequestBody TVA tVA) throws URISyntaxException {
        log.debug("REST request to update TVA : {}", tVA);
        if (tVA.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TVA result = tVAService.save(tVA);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tVA.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tvas} : get all the tVAS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tVAS in body.
     */
    @GetMapping("/tvas")
    public ResponseEntity<List<TVA>> getAllTVAS(Pageable pageable) {
        log.debug("REST request to get a page of TVAS");
        Page<TVA> page = tVAService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tvas/:id} : get the "id" tVA.
     *
     * @param id the id of the tVA to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tVA, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tvas/{id}")
    public ResponseEntity<TVA> getTVA(@PathVariable Long id) {
        log.debug("REST request to get TVA : {}", id);
        Optional<TVA> tVA = tVAService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tVA);
    }

    /**
     * {@code DELETE  /tvas/:id} : delete the "id" tVA.
     *
     * @param id the id of the tVA to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tvas/{id}")
    public ResponseEntity<Void> deleteTVA(@PathVariable Long id) {
        log.debug("REST request to delete TVA : {}", id);
        tVAService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
