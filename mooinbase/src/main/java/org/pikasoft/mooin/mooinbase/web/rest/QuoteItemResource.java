package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.QuoteItem;
import org.pikasoft.mooin.mooinbase.service.QuoteItemService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.QuoteItem}.
 */
@RestController
@RequestMapping("/api")
public class QuoteItemResource {

    private final Logger log = LoggerFactory.getLogger(QuoteItemResource.class);

    private static final String ENTITY_NAME = "mooinbaseQuoteItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteItemService quoteItemService;

    public QuoteItemResource(QuoteItemService quoteItemService) {
        this.quoteItemService = quoteItemService;
    }

    /**
     * {@code POST  /quote-items} : Create a new quoteItem.
     *
     * @param quoteItem the quoteItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quoteItem, or with status {@code 400 (Bad Request)} if the quoteItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quote-items")
    public ResponseEntity<QuoteItem> createQuoteItem(@Valid @RequestBody QuoteItem quoteItem) throws URISyntaxException {
        log.debug("REST request to save QuoteItem : {}", quoteItem);
        if (quoteItem.getId() != null) {
            throw new BadRequestAlertException("A new quoteItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuoteItem result = quoteItemService.save(quoteItem);
        return ResponseEntity.created(new URI("/api/quote-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quote-items} : Updates an existing quoteItem.
     *
     * @param quoteItem the quoteItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quoteItem,
     * or with status {@code 400 (Bad Request)} if the quoteItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quoteItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quote-items")
    public ResponseEntity<QuoteItem> updateQuoteItem(@Valid @RequestBody QuoteItem quoteItem) throws URISyntaxException {
        log.debug("REST request to update QuoteItem : {}", quoteItem);
        if (quoteItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuoteItem result = quoteItemService.save(quoteItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quoteItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quote-items} : get all the quoteItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quoteItems in body.
     */
    @GetMapping("/quote-items")
    public ResponseEntity<List<QuoteItem>> getAllQuoteItems(Pageable pageable) {
        log.debug("REST request to get a page of QuoteItems");
        Page<QuoteItem> page = quoteItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quote-items/:id} : get the "id" quoteItem.
     *
     * @param id the id of the quoteItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quoteItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quote-items/{id}")
    public ResponseEntity<QuoteItem> getQuoteItem(@PathVariable Long id) {
        log.debug("REST request to get QuoteItem : {}", id);
        Optional<QuoteItem> quoteItem = quoteItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quoteItem);
    }

    /**
     * {@code DELETE  /quote-items/:id} : delete the "id" quoteItem.
     *
     * @param id the id of the quoteItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quote-items/{id}")
    public ResponseEntity<Void> deleteQuoteItem(@PathVariable Long id) {
        log.debug("REST request to delete QuoteItem : {}", id);
        quoteItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
