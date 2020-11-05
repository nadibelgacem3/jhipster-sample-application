package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.BL;
import org.pikasoft.mooin.mooinbase.service.BLService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.BL}.
 */
@RestController
@RequestMapping("/api")
public class BLResource {

    private final Logger log = LoggerFactory.getLogger(BLResource.class);

    private static final String ENTITY_NAME = "mooinbaseBl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BLService bLService;

    public BLResource(BLService bLService) {
        this.bLService = bLService;
    }

    /**
     * {@code POST  /bls} : Create a new bL.
     *
     * @param bL the bL to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bL, or with status {@code 400 (Bad Request)} if the bL has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bls")
    public ResponseEntity<BL> createBL(@Valid @RequestBody BL bL) throws URISyntaxException {
        log.debug("REST request to save BL : {}", bL);
        if (bL.getId() != null) {
            throw new BadRequestAlertException("A new bL cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BL result = bLService.save(bL);
        return ResponseEntity.created(new URI("/api/bls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bls} : Updates an existing bL.
     *
     * @param bL the bL to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bL,
     * or with status {@code 400 (Bad Request)} if the bL is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bL couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bls")
    public ResponseEntity<BL> updateBL(@Valid @RequestBody BL bL) throws URISyntaxException {
        log.debug("REST request to update BL : {}", bL);
        if (bL.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BL result = bLService.save(bL);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bL.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bls} : get all the bLS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bLS in body.
     */
    @GetMapping("/bls")
    public ResponseEntity<List<BL>> getAllBLS(Pageable pageable) {
        log.debug("REST request to get a page of BLS");
        Page<BL> page = bLService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bls/:id} : get the "id" bL.
     *
     * @param id the id of the bL to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bL, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bls/{id}")
    public ResponseEntity<BL> getBL(@PathVariable Long id) {
        log.debug("REST request to get BL : {}", id);
        Optional<BL> bL = bLService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bL);
    }

    /**
     * {@code DELETE  /bls/:id} : delete the "id" bL.
     *
     * @param id the id of the bL to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bls/{id}")
    public ResponseEntity<Void> deleteBL(@PathVariable Long id) {
        log.debug("REST request to delete BL : {}", id);
        bLService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
