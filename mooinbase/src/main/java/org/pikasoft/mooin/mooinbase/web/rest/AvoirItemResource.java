package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.AvoirItem;
import org.pikasoft.mooin.mooinbase.service.AvoirItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.AvoirItem}.
 */
@RestController
@RequestMapping("/api")
public class AvoirItemResource {

    private final Logger log = LoggerFactory.getLogger(AvoirItemResource.class);

    private static final String ENTITY_NAME = "mooinbaseAvoirItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvoirItemService avoirItemService;

    public AvoirItemResource(AvoirItemService avoirItemService) {
        this.avoirItemService = avoirItemService;
    }

    /**
     * {@code POST  /avoir-items} : Create a new avoirItem.
     *
     * @param avoirItem the avoirItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avoirItem, or with status {@code 400 (Bad Request)} if the avoirItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avoir-items")
    public ResponseEntity<AvoirItem> createAvoirItem(@Valid @RequestBody AvoirItem avoirItem) throws URISyntaxException {
        log.debug("REST request to save AvoirItem : {}", avoirItem);
        if (avoirItem.getId() != null) {
            throw new BadRequestAlertException("A new avoirItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvoirItem result = avoirItemService.save(avoirItem);
        return ResponseEntity.created(new URI("/api/avoir-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avoir-items} : Updates an existing avoirItem.
     *
     * @param avoirItem the avoirItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avoirItem,
     * or with status {@code 400 (Bad Request)} if the avoirItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avoirItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avoir-items")
    public ResponseEntity<AvoirItem> updateAvoirItem(@Valid @RequestBody AvoirItem avoirItem) throws URISyntaxException {
        log.debug("REST request to update AvoirItem : {}", avoirItem);
        if (avoirItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AvoirItem result = avoirItemService.save(avoirItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avoirItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /avoir-items} : get all the avoirItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avoirItems in body.
     */
    @GetMapping("/avoir-items")
    public ResponseEntity<List<AvoirItem>> getAllAvoirItems(Pageable pageable) {
        log.debug("REST request to get a page of AvoirItems");
        Page<AvoirItem> page = avoirItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avoir-items/:id} : get the "id" avoirItem.
     *
     * @param id the id of the avoirItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avoirItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avoir-items/{id}")
    public ResponseEntity<AvoirItem> getAvoirItem(@PathVariable Long id) {
        log.debug("REST request to get AvoirItem : {}", id);
        Optional<AvoirItem> avoirItem = avoirItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avoirItem);
    }

    /**
     * {@code DELETE  /avoir-items/:id} : delete the "id" avoirItem.
     *
     * @param id the id of the avoirItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avoir-items/{id}")
    public ResponseEntity<Void> deleteAvoirItem(@PathVariable Long id) {
        log.debug("REST request to delete AvoirItem : {}", id);
        avoirItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
