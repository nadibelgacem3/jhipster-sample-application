package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.CompanyLocation;
import org.pikasoft.mooin.mooincompanies.service.CompanyLocationService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.CompanyLocation}.
 */
@RestController
@RequestMapping("/api")
public class CompanyLocationResource {

    private final Logger log = LoggerFactory.getLogger(CompanyLocationResource.class);

    private static final String ENTITY_NAME = "mooincompaniesCompanyLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyLocationService companyLocationService;

    public CompanyLocationResource(CompanyLocationService companyLocationService) {
        this.companyLocationService = companyLocationService;
    }

    /**
     * {@code POST  /company-locations} : Create a new companyLocation.
     *
     * @param companyLocation the companyLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyLocation, or with status {@code 400 (Bad Request)} if the companyLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-locations")
    public ResponseEntity<CompanyLocation> createCompanyLocation(@RequestBody CompanyLocation companyLocation) throws URISyntaxException {
        log.debug("REST request to save CompanyLocation : {}", companyLocation);
        if (companyLocation.getId() != null) {
            throw new BadRequestAlertException("A new companyLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyLocation result = companyLocationService.save(companyLocation);
        return ResponseEntity.created(new URI("/api/company-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-locations} : Updates an existing companyLocation.
     *
     * @param companyLocation the companyLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLocation,
     * or with status {@code 400 (Bad Request)} if the companyLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-locations")
    public ResponseEntity<CompanyLocation> updateCompanyLocation(@RequestBody CompanyLocation companyLocation) throws URISyntaxException {
        log.debug("REST request to update CompanyLocation : {}", companyLocation);
        if (companyLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyLocation result = companyLocationService.save(companyLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-locations} : get all the companyLocations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyLocations in body.
     */
    @GetMapping("/company-locations")
    public ResponseEntity<List<CompanyLocation>> getAllCompanyLocations(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("company-is-null".equals(filter)) {
            log.debug("REST request to get all CompanyLocations where company is null");
            return new ResponseEntity<>(companyLocationService.findAllWhereCompanyIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CompanyLocations");
        Page<CompanyLocation> page = companyLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-locations/:id} : get the "id" companyLocation.
     *
     * @param id the id of the companyLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-locations/{id}")
    public ResponseEntity<CompanyLocation> getCompanyLocation(@PathVariable Long id) {
        log.debug("REST request to get CompanyLocation : {}", id);
        Optional<CompanyLocation> companyLocation = companyLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyLocation);
    }

    /**
     * {@code DELETE  /company-locations/:id} : delete the "id" companyLocation.
     *
     * @param id the id of the companyLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-locations/{id}")
    public ResponseEntity<Void> deleteCompanyLocation(@PathVariable Long id) {
        log.debug("REST request to delete CompanyLocation : {}", id);
        companyLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
