package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.EmployeeLocation;
import org.pikasoft.mooin.mooincompanies.service.EmployeeLocationService;
import org.pikasoft.mooin.mooincompanies.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.EmployeeLocation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLocationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLocationResource.class);

    private static final String ENTITY_NAME = "mooincompaniesEmployeeLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeLocationService employeeLocationService;

    public EmployeeLocationResource(EmployeeLocationService employeeLocationService) {
        this.employeeLocationService = employeeLocationService;
    }

    /**
     * {@code POST  /employee-locations} : Create a new employeeLocation.
     *
     * @param employeeLocation the employeeLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeLocation, or with status {@code 400 (Bad Request)} if the employeeLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-locations")
    public ResponseEntity<EmployeeLocation> createEmployeeLocation(@RequestBody EmployeeLocation employeeLocation) throws URISyntaxException {
        log.debug("REST request to save EmployeeLocation : {}", employeeLocation);
        if (employeeLocation.getId() != null) {
            throw new BadRequestAlertException("A new employeeLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLocation result = employeeLocationService.save(employeeLocation);
        return ResponseEntity.created(new URI("/api/employee-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-locations} : Updates an existing employeeLocation.
     *
     * @param employeeLocation the employeeLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLocation,
     * or with status {@code 400 (Bad Request)} if the employeeLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-locations")
    public ResponseEntity<EmployeeLocation> updateEmployeeLocation(@RequestBody EmployeeLocation employeeLocation) throws URISyntaxException {
        log.debug("REST request to update EmployeeLocation : {}", employeeLocation);
        if (employeeLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeLocation result = employeeLocationService.save(employeeLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-locations} : get all the employeeLocations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeLocations in body.
     */
    @GetMapping("/employee-locations")
    public ResponseEntity<List<EmployeeLocation>> getAllEmployeeLocations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("employee-is-null".equals(filter)) {
            log.debug("REST request to get all EmployeeLocations where employee is null");
            return new ResponseEntity<>(employeeLocationService.findAllWhereEmployeeIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of EmployeeLocations");
        Page<EmployeeLocation> page = employeeLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-locations/:id} : get the "id" employeeLocation.
     *
     * @param id the id of the employeeLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-locations/{id}")
    public ResponseEntity<EmployeeLocation> getEmployeeLocation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLocation : {}", id);
        Optional<EmployeeLocation> employeeLocation = employeeLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeLocation);
    }

    /**
     * {@code DELETE  /employee-locations/:id} : delete the "id" employeeLocation.
     *
     * @param id the id of the employeeLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-locations/{id}")
    public ResponseEntity<Void> deleteEmployeeLocation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLocation : {}", id);
        employeeLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
