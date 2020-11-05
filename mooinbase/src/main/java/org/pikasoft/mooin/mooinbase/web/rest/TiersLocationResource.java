package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.TiersLocation;
import org.pikasoft.mooin.mooinbase.service.TiersLocationService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.TiersLocation}.
 */
@RestController
@RequestMapping("/api")
public class TiersLocationResource {

    private final Logger log = LoggerFactory.getLogger(TiersLocationResource.class);

    private static final String ENTITY_NAME = "mooinbaseTiersLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiersLocationService tiersLocationService;

    public TiersLocationResource(TiersLocationService tiersLocationService) {
        this.tiersLocationService = tiersLocationService;
    }

    /**
     * {@code POST  /tiers-locations} : Create a new tiersLocation.
     *
     * @param tiersLocation the tiersLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiersLocation, or with status {@code 400 (Bad Request)} if the tiersLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tiers-locations")
    public ResponseEntity<TiersLocation> createTiersLocation(@RequestBody TiersLocation tiersLocation) throws URISyntaxException {
        log.debug("REST request to save TiersLocation : {}", tiersLocation);
        if (tiersLocation.getId() != null) {
            throw new BadRequestAlertException("A new tiersLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiersLocation result = tiersLocationService.save(tiersLocation);
        return ResponseEntity.created(new URI("/api/tiers-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tiers-locations} : Updates an existing tiersLocation.
     *
     * @param tiersLocation the tiersLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiersLocation,
     * or with status {@code 400 (Bad Request)} if the tiersLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiersLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tiers-locations")
    public ResponseEntity<TiersLocation> updateTiersLocation(@RequestBody TiersLocation tiersLocation) throws URISyntaxException {
        log.debug("REST request to update TiersLocation : {}", tiersLocation);
        if (tiersLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiersLocation result = tiersLocationService.save(tiersLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tiersLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tiers-locations} : get all the tiersLocations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiersLocations in body.
     */
    @GetMapping("/tiers-locations")
    public ResponseEntity<List<TiersLocation>> getAllTiersLocations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("tiers-is-null".equals(filter)) {
            log.debug("REST request to get all TiersLocations where tiers is null");
            return new ResponseEntity<>(tiersLocationService.findAllWhereTiersIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TiersLocations");
        Page<TiersLocation> page = tiersLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tiers-locations/:id} : get the "id" tiersLocation.
     *
     * @param id the id of the tiersLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiersLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tiers-locations/{id}")
    public ResponseEntity<TiersLocation> getTiersLocation(@PathVariable Long id) {
        log.debug("REST request to get TiersLocation : {}", id);
        Optional<TiersLocation> tiersLocation = tiersLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiersLocation);
    }

    /**
     * {@code DELETE  /tiers-locations/:id} : delete the "id" tiersLocation.
     *
     * @param id the id of the tiersLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tiers-locations/{id}")
    public ResponseEntity<Void> deleteTiersLocation(@PathVariable Long id) {
        log.debug("REST request to delete TiersLocation : {}", id);
        tiersLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
