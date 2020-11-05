package org.pikasoft.mooin.mooinecommerce.web.rest;

import org.pikasoft.mooin.mooinecommerce.domain.OfferOrder;
import org.pikasoft.mooin.mooinecommerce.service.OfferOrderService;
import org.pikasoft.mooin.mooinecommerce.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.pikasoft.mooin.mooinecommerce.domain.OfferOrder}.
 */
@RestController
@RequestMapping("/api")
public class OfferOrderResource {

    private final Logger log = LoggerFactory.getLogger(OfferOrderResource.class);

    private static final String ENTITY_NAME = "mooinecommerceOfferOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferOrderService offerOrderService;

    public OfferOrderResource(OfferOrderService offerOrderService) {
        this.offerOrderService = offerOrderService;
    }

    /**
     * {@code POST  /offer-orders} : Create a new offerOrder.
     *
     * @param offerOrder the offerOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerOrder, or with status {@code 400 (Bad Request)} if the offerOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offer-orders")
    public ResponseEntity<OfferOrder> createOfferOrder(@Valid @RequestBody OfferOrder offerOrder) throws URISyntaxException {
        log.debug("REST request to save OfferOrder : {}", offerOrder);
        if (offerOrder.getId() != null) {
            throw new BadRequestAlertException("A new offerOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferOrder result = offerOrderService.save(offerOrder);
        return ResponseEntity.created(new URI("/api/offer-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-orders} : Updates an existing offerOrder.
     *
     * @param offerOrder the offerOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerOrder,
     * or with status {@code 400 (Bad Request)} if the offerOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offer-orders")
    public ResponseEntity<OfferOrder> updateOfferOrder(@Valid @RequestBody OfferOrder offerOrder) throws URISyntaxException {
        log.debug("REST request to update OfferOrder : {}", offerOrder);
        if (offerOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferOrder result = offerOrderService.save(offerOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offer-orders} : get all the offerOrders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerOrders in body.
     */
    @GetMapping("/offer-orders")
    public ResponseEntity<List<OfferOrder>> getAllOfferOrders(Pageable pageable) {
        log.debug("REST request to get a page of OfferOrders");
        Page<OfferOrder> page = offerOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offer-orders/:id} : get the "id" offerOrder.
     *
     * @param id the id of the offerOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-orders/{id}")
    public ResponseEntity<OfferOrder> getOfferOrder(@PathVariable Long id) {
        log.debug("REST request to get OfferOrder : {}", id);
        Optional<OfferOrder> offerOrder = offerOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerOrder);
    }

    /**
     * {@code DELETE  /offer-orders/:id} : delete the "id" offerOrder.
     *
     * @param id the id of the offerOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-orders/{id}")
    public ResponseEntity<Void> deleteOfferOrder(@PathVariable Long id) {
        log.debug("REST request to delete OfferOrder : {}", id);
        offerOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
