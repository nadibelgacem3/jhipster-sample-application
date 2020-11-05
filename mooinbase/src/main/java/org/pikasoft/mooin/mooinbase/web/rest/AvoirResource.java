package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.Avoir;
import org.pikasoft.mooin.mooinbase.service.AvoirService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.Avoir}.
 */
@RestController
@RequestMapping("/api")
public class AvoirResource {

    private final Logger log = LoggerFactory.getLogger(AvoirResource.class);

    private static final String ENTITY_NAME = "mooinbaseAvoir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvoirService avoirService;

    public AvoirResource(AvoirService avoirService) {
        this.avoirService = avoirService;
    }

    /**
     * {@code POST  /avoirs} : Create a new avoir.
     *
     * @param avoir the avoir to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avoir, or with status {@code 400 (Bad Request)} if the avoir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avoirs")
    public ResponseEntity<Avoir> createAvoir(@Valid @RequestBody Avoir avoir) throws URISyntaxException {
        log.debug("REST request to save Avoir : {}", avoir);
        if (avoir.getId() != null) {
            throw new BadRequestAlertException("A new avoir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Avoir result = avoirService.save(avoir);
        return ResponseEntity.created(new URI("/api/avoirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avoirs} : Updates an existing avoir.
     *
     * @param avoir the avoir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avoir,
     * or with status {@code 400 (Bad Request)} if the avoir is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avoir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avoirs")
    public ResponseEntity<Avoir> updateAvoir(@Valid @RequestBody Avoir avoir) throws URISyntaxException {
        log.debug("REST request to update Avoir : {}", avoir);
        if (avoir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Avoir result = avoirService.save(avoir);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avoir.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avoirs} : get all the avoirs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avoirs in body.
     */
    @GetMapping("/avoirs")
    public ResponseEntity<List<Avoir>> getAllAvoirs(Pageable pageable) {
        log.debug("REST request to get a page of Avoirs");
        Page<Avoir> page = avoirService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avoirs/:id} : get the "id" avoir.
     *
     * @param id the id of the avoir to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avoir, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avoirs/{id}")
    public ResponseEntity<Avoir> getAvoir(@PathVariable Long id) {
        log.debug("REST request to get Avoir : {}", id);
        Optional<Avoir> avoir = avoirService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avoir);
    }

    /**
     * {@code DELETE  /avoirs/:id} : delete the "id" avoir.
     *
     * @param id the id of the avoir to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avoirs/{id}")
    public ResponseEntity<Void> deleteAvoir(@PathVariable Long id) {
        log.debug("REST request to delete Avoir : {}", id);
        avoirService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
