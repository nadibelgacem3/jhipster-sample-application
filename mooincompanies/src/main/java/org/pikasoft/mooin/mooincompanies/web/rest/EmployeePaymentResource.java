package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.EmployeePayment;
import org.pikasoft.mooin.mooincompanies.service.EmployeePaymentService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.EmployeePayment}.
 */
@RestController
@RequestMapping("/api")
public class EmployeePaymentResource {

    private final Logger log = LoggerFactory.getLogger(EmployeePaymentResource.class);

    private static final String ENTITY_NAME = "mooincompaniesEmployeePayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeePaymentService employeePaymentService;

    public EmployeePaymentResource(EmployeePaymentService employeePaymentService) {
        this.employeePaymentService = employeePaymentService;
    }

    /**
     * {@code POST  /employee-payments} : Create a new employeePayment.
     *
     * @param employeePayment the employeePayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeePayment, or with status {@code 400 (Bad Request)} if the employeePayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-payments")
    public ResponseEntity<EmployeePayment> createEmployeePayment(@Valid @RequestBody EmployeePayment employeePayment) throws URISyntaxException {
        log.debug("REST request to save EmployeePayment : {}", employeePayment);
        if (employeePayment.getId() != null) {
            throw new BadRequestAlertException("A new employeePayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeePayment result = employeePaymentService.save(employeePayment);
        return ResponseEntity.created(new URI("/api/employee-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-payments} : Updates an existing employeePayment.
     *
     * @param employeePayment the employeePayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeePayment,
     * or with status {@code 400 (Bad Request)} if the employeePayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeePayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-payments")
    public ResponseEntity<EmployeePayment> updateEmployeePayment(@Valid @RequestBody EmployeePayment employeePayment) throws URISyntaxException {
        log.debug("REST request to update EmployeePayment : {}", employeePayment);
        if (employeePayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeePayment result = employeePaymentService.save(employeePayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeePayment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-payments} : get all the employeePayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeePayments in body.
     */
    @GetMapping("/employee-payments")
    public ResponseEntity<List<EmployeePayment>> getAllEmployeePayments(Pageable pageable) {
        log.debug("REST request to get a page of EmployeePayments");
        Page<EmployeePayment> page = employeePaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-payments/:id} : get the "id" employeePayment.
     *
     * @param id the id of the employeePayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeePayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-payments/{id}")
    public ResponseEntity<EmployeePayment> getEmployeePayment(@PathVariable Long id) {
        log.debug("REST request to get EmployeePayment : {}", id);
        Optional<EmployeePayment> employeePayment = employeePaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeePayment);
    }

    /**
     * {@code DELETE  /employee-payments/:id} : delete the "id" employeePayment.
     *
     * @param id the id of the employeePayment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-payments/{id}")
    public ResponseEntity<Void> deleteEmployeePayment(@PathVariable Long id) {
        log.debug("REST request to delete EmployeePayment : {}", id);
        employeePaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
