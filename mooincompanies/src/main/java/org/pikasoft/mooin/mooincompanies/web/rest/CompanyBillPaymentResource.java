package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBillPayment;
import org.pikasoft.mooin.mooincompanies.service.CompanyBillPaymentService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.CompanyBillPayment}.
 */
@RestController
@RequestMapping("/api")
public class CompanyBillPaymentResource {

    private final Logger log = LoggerFactory.getLogger(CompanyBillPaymentResource.class);

    private static final String ENTITY_NAME = "mooincompaniesCompanyBillPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyBillPaymentService companyBillPaymentService;

    public CompanyBillPaymentResource(CompanyBillPaymentService companyBillPaymentService) {
        this.companyBillPaymentService = companyBillPaymentService;
    }

    /**
     * {@code POST  /company-bill-payments} : Create a new companyBillPayment.
     *
     * @param companyBillPayment the companyBillPayment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyBillPayment, or with status {@code 400 (Bad Request)} if the companyBillPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-bill-payments")
    public ResponseEntity<CompanyBillPayment> createCompanyBillPayment(@Valid @RequestBody CompanyBillPayment companyBillPayment) throws URISyntaxException {
        log.debug("REST request to save CompanyBillPayment : {}", companyBillPayment);
        if (companyBillPayment.getId() != null) {
            throw new BadRequestAlertException("A new companyBillPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyBillPayment result = companyBillPaymentService.save(companyBillPayment);
        return ResponseEntity.created(new URI("/api/company-bill-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-bill-payments} : Updates an existing companyBillPayment.
     *
     * @param companyBillPayment the companyBillPayment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyBillPayment,
     * or with status {@code 400 (Bad Request)} if the companyBillPayment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyBillPayment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-bill-payments")
    public ResponseEntity<CompanyBillPayment> updateCompanyBillPayment(@Valid @RequestBody CompanyBillPayment companyBillPayment) throws URISyntaxException {
        log.debug("REST request to update CompanyBillPayment : {}", companyBillPayment);
        if (companyBillPayment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyBillPayment result = companyBillPaymentService.save(companyBillPayment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyBillPayment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-bill-payments} : get all the companyBillPayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyBillPayments in body.
     */
    @GetMapping("/company-bill-payments")
    public ResponseEntity<List<CompanyBillPayment>> getAllCompanyBillPayments(Pageable pageable) {
        log.debug("REST request to get a page of CompanyBillPayments");
        Page<CompanyBillPayment> page = companyBillPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-bill-payments/:id} : get the "id" companyBillPayment.
     *
     * @param id the id of the companyBillPayment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyBillPayment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-bill-payments/{id}")
    public ResponseEntity<CompanyBillPayment> getCompanyBillPayment(@PathVariable Long id) {
        log.debug("REST request to get CompanyBillPayment : {}", id);
        Optional<CompanyBillPayment> companyBillPayment = companyBillPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyBillPayment);
    }

    /**
     * {@code DELETE  /company-bill-payments/:id} : delete the "id" companyBillPayment.
     *
     * @param id the id of the companyBillPayment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-bill-payments/{id}")
    public ResponseEntity<Void> deleteCompanyBillPayment(@PathVariable Long id) {
        log.debug("REST request to delete CompanyBillPayment : {}", id);
        companyBillPaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
