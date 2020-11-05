package org.pikasoft.mooin.mooinbase.web.rest;

import org.pikasoft.mooin.mooinbase.domain.Movement;
import org.pikasoft.mooin.mooinbase.service.MovementService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooinbase.domain.Movement}.
 */
@RestController
@RequestMapping("/api")
public class MovementResource {

    private final Logger log = LoggerFactory.getLogger(MovementResource.class);

    private static final String ENTITY_NAME = "mooinbaseMovement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovementService movementService;

    public MovementResource(MovementService movementService) {
        this.movementService = movementService;
    }

    /**
     * {@code POST  /movements} : Create a new movement.
     *
     * @param movement the movement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movement, or with status {@code 400 (Bad Request)} if the movement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movements")
    public ResponseEntity<Movement> createMovement(@Valid @RequestBody Movement movement) throws URISyntaxException {
        log.debug("REST request to save Movement : {}", movement);
        if (movement.getId() != null) {
            throw new BadRequestAlertException("A new movement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movement result = movementService.save(movement);
        return ResponseEntity.created(new URI("/api/movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movements} : Updates an existing movement.
     *
     * @param movement the movement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movement,
     * or with status {@code 400 (Bad Request)} if the movement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movements")
    public ResponseEntity<Movement> updateMovement(@Valid @RequestBody Movement movement) throws URISyntaxException {
        log.debug("REST request to update Movement : {}", movement);
        if (movement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Movement result = movementService.save(movement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movements} : get all the movements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movements in body.
     */
    @GetMapping("/movements")
    public ResponseEntity<List<Movement>> getAllMovements(Pageable pageable) {
        log.debug("REST request to get a page of Movements");
        Page<Movement> page = movementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /movements/:id} : get the "id" movement.
     *
     * @param id the id of the movement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movements/{id}")
    public ResponseEntity<Movement> getMovement(@PathVariable Long id) {
        log.debug("REST request to get Movement : {}", id);
        Optional<Movement> movement = movementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(movement);
    }

    /**
     * {@code DELETE  /movements/:id} : delete the "id" movement.
     *
     * @param id the id of the movement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movements/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        log.debug("REST request to delete Movement : {}", id);
        movementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
