package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.TiersBankCheck;
import org.pikasoft.mooin.mooinbase.service.TiersBankCheckService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.TiersBankCheck}.
 */
@RestController
@RequestMapping("/api")
public class TiersBankCheckResource {

    private final Logger log = LoggerFactory.getLogger(TiersBankCheckResource.class);

    private static final String ENTITY_NAME = "mooinbaseTiersBankCheck";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiersBankCheckService tiersBankCheckService;

    public TiersBankCheckResource(TiersBankCheckService tiersBankCheckService) {
        this.tiersBankCheckService = tiersBankCheckService;
    }

    /**
     * {@code POST  /tiers-bank-checks} : Create a new tiersBankCheck.
     *
     * @param tiersBankCheck the tiersBankCheck to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiersBankCheck, or with status {@code 400 (Bad Request)} if the tiersBankCheck has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tiers-bank-checks")
    public ResponseEntity<TiersBankCheck> createTiersBankCheck(@Valid @RequestBody TiersBankCheck tiersBankCheck) throws URISyntaxException {
        log.debug("REST request to save TiersBankCheck : {}", tiersBankCheck);
        if (tiersBankCheck.getId() != null) {
            throw new BadRequestAlertException("A new tiersBankCheck cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiersBankCheck result = tiersBankCheckService.save(tiersBankCheck);
        return ResponseEntity.created(new URI("/api/tiers-bank-checks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tiers-bank-checks} : Updates an existing tiersBankCheck.
     *
     * @param tiersBankCheck the tiersBankCheck to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiersBankCheck,
     * or with status {@code 400 (Bad Request)} if the tiersBankCheck is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiersBankCheck couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tiers-bank-checks")
    public ResponseEntity<TiersBankCheck> updateTiersBankCheck(@Valid @RequestBody TiersBankCheck tiersBankCheck) throws URISyntaxException {
        log.debug("REST request to update TiersBankCheck : {}", tiersBankCheck);
        if (tiersBankCheck.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiersBankCheck result = tiersBankCheckService.save(tiersBankCheck);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tiersBankCheck.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tiers-bank-checks} : get all the tiersBankChecks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiersBankChecks in body.
     */
    @GetMapping("/tiers-bank-checks")
    public ResponseEntity<List<TiersBankCheck>> getAllTiersBankChecks(Pageable pageable) {
        log.debug("REST request to get a page of TiersBankChecks");
        Page<TiersBankCheck> page = tiersBankCheckService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tiers-bank-checks/:id} : get the "id" tiersBankCheck.
     *
     * @param id the id of the tiersBankCheck to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiersBankCheck, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tiers-bank-checks/{id}")
    public ResponseEntity<TiersBankCheck> getTiersBankCheck(@PathVariable Long id) {
        log.debug("REST request to get TiersBankCheck : {}", id);
        Optional<TiersBankCheck> tiersBankCheck = tiersBankCheckService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiersBankCheck);
    }

    /**
     * {@code DELETE  /tiers-bank-checks/:id} : delete the "id" tiersBankCheck.
     *
     * @param id the id of the tiersBankCheck to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tiers-bank-checks/{id}")
    public ResponseEntity<Void> deleteTiersBankCheck(@PathVariable Long id) {
        log.debug("REST request to delete TiersBankCheck : {}", id);
        tiersBankCheckService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
