package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.BLItem;
import org.pikasoft.mooin.mooinbase.service.BLItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.BLItem}.
 */
@RestController
@RequestMapping("/api")
public class BLItemResource {

    private final Logger log = LoggerFactory.getLogger(BLItemResource.class);

    private static final String ENTITY_NAME = "mooinbaseBlItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BLItemService bLItemService;

    public BLItemResource(BLItemService bLItemService) {
        this.bLItemService = bLItemService;
    }

    /**
     * {@code POST  /bl-items} : Create a new bLItem.
     *
     * @param bLItem the bLItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bLItem, or with status {@code 400 (Bad Request)} if the bLItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bl-items")
    public ResponseEntity<BLItem> createBLItem(@Valid @RequestBody BLItem bLItem) throws URISyntaxException {
        log.debug("REST request to save BLItem : {}", bLItem);
        if (bLItem.getId() != null) {
            throw new BadRequestAlertException("A new bLItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BLItem result = bLItemService.save(bLItem);
        return ResponseEntity.created(new URI("/api/bl-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bl-items} : Updates an existing bLItem.
     *
     * @param bLItem the bLItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bLItem,
     * or with status {@code 400 (Bad Request)} if the bLItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bLItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bl-items")
    public ResponseEntity<BLItem> updateBLItem(@Valid @RequestBody BLItem bLItem) throws URISyntaxException {
        log.debug("REST request to update BLItem : {}", bLItem);
        if (bLItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BLItem result = bLItemService.save(bLItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bLItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bl-items} : get all the bLItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bLItems in body.
     */
    @GetMapping("/bl-items")
    public ResponseEntity<List<BLItem>> getAllBLItems(Pageable pageable) {
        log.debug("REST request to get a page of BLItems");
        Page<BLItem> page = bLItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bl-items/:id} : get the "id" bLItem.
     *
     * @param id the id of the bLItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bLItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bl-items/{id}")
    public ResponseEntity<BLItem> getBLItem(@PathVariable Long id) {
        log.debug("REST request to get BLItem : {}", id);
        Optional<BLItem> bLItem = bLItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bLItem);
    }

    /**
     * {@code DELETE  /bl-items/:id} : delete the "id" bLItem.
     *
     * @param id the id of the bLItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bl-items/{id}")
    public ResponseEntity<Void> deleteBLItem(@PathVariable Long id) {
        log.debug("REST request to delete BLItem : {}", id);
        bLItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
