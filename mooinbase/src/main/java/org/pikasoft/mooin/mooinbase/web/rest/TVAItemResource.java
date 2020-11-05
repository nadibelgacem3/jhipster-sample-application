package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.TVAItem;
import org.pikasoft.mooin.mooinbase.service.TVAItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.TVAItem}.
 */
@RestController
@RequestMapping("/api")
public class TVAItemResource {

    private final Logger log = LoggerFactory.getLogger(TVAItemResource.class);

    private static final String ENTITY_NAME = "mooinbaseTvaItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TVAItemService tVAItemService;

    public TVAItemResource(TVAItemService tVAItemService) {
        this.tVAItemService = tVAItemService;
    }

    /**
     * {@code POST  /tva-items} : Create a new tVAItem.
     *
     * @param tVAItem the tVAItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tVAItem, or with status {@code 400 (Bad Request)} if the tVAItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tva-items")
    public ResponseEntity<TVAItem> createTVAItem(@Valid @RequestBody TVAItem tVAItem) throws URISyntaxException {
        log.debug("REST request to save TVAItem : {}", tVAItem);
        if (tVAItem.getId() != null) {
            throw new BadRequestAlertException("A new tVAItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TVAItem result = tVAItemService.save(tVAItem);
        return ResponseEntity.created(new URI("/api/tva-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tva-items} : Updates an existing tVAItem.
     *
     * @param tVAItem the tVAItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tVAItem,
     * or with status {@code 400 (Bad Request)} if the tVAItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tVAItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tva-items")
    public ResponseEntity<TVAItem> updateTVAItem(@Valid @RequestBody TVAItem tVAItem) throws URISyntaxException {
        log.debug("REST request to update TVAItem : {}", tVAItem);
        if (tVAItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TVAItem result = tVAItemService.save(tVAItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tVAItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tva-items} : get all the tVAItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tVAItems in body.
     */
    @GetMapping("/tva-items")
    public ResponseEntity<List<TVAItem>> getAllTVAItems(Pageable pageable) {
        log.debug("REST request to get a page of TVAItems");
        Page<TVAItem> page = tVAItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tva-items/:id} : get the "id" tVAItem.
     *
     * @param id the id of the tVAItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tVAItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tva-items/{id}")
    public ResponseEntity<TVAItem> getTVAItem(@PathVariable Long id) {
        log.debug("REST request to get TVAItem : {}", id);
        Optional<TVAItem> tVAItem = tVAItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tVAItem);
    }

    /**
     * {@code DELETE  /tva-items/:id} : delete the "id" tVAItem.
     *
     * @param id the id of the tVAItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tva-items/{id}")
    public ResponseEntity<Void> deleteTVAItem(@PathVariable Long id) {
        log.debug("REST request to delete TVAItem : {}", id);
        tVAItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
