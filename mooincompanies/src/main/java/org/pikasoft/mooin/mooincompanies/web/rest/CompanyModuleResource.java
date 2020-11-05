package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.CompanyModule;
import org.pikasoft.mooin.mooincompanies.service.CompanyModuleService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.CompanyModule}.
 */
@RestController
@RequestMapping("/api")
public class CompanyModuleResource {

    private final Logger log = LoggerFactory.getLogger(CompanyModuleResource.class);

    private static final String ENTITY_NAME = "mooincompaniesCompanyModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyModuleService companyModuleService;

    public CompanyModuleResource(CompanyModuleService companyModuleService) {
        this.companyModuleService = companyModuleService;
    }

    /**
     * {@code POST  /company-modules} : Create a new companyModule.
     *
     * @param companyModule the companyModule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyModule, or with status {@code 400 (Bad Request)} if the companyModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-modules")
    public ResponseEntity<CompanyModule> createCompanyModule(@Valid @RequestBody CompanyModule companyModule) throws URISyntaxException {
        log.debug("REST request to save CompanyModule : {}", companyModule);
        if (companyModule.getId() != null) {
            throw new BadRequestAlertException("A new companyModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyModule result = companyModuleService.save(companyModule);
        return ResponseEntity.created(new URI("/api/company-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-modules} : Updates an existing companyModule.
     *
     * @param companyModule the companyModule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyModule,
     * or with status {@code 400 (Bad Request)} if the companyModule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyModule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-modules")
    public ResponseEntity<CompanyModule> updateCompanyModule(@Valid @RequestBody CompanyModule companyModule) throws URISyntaxException {
        log.debug("REST request to update CompanyModule : {}", companyModule);
        if (companyModule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyModule result = companyModuleService.save(companyModule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyModule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-modules} : get all the companyModules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyModules in body.
     */
    @GetMapping("/company-modules")
    public ResponseEntity<List<CompanyModule>> getAllCompanyModules(Pageable pageable) {
        log.debug("REST request to get a page of CompanyModules");
        Page<CompanyModule> page = companyModuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-modules/:id} : get the "id" companyModule.
     *
     * @param id the id of the companyModule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyModule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-modules/{id}")
    public ResponseEntity<CompanyModule> getCompanyModule(@PathVariable Long id) {
        log.debug("REST request to get CompanyModule : {}", id);
        Optional<CompanyModule> companyModule = companyModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyModule);
    }

    /**
     * {@code DELETE  /company-modules/:id} : delete the "id" companyModule.
     *
     * @param id the id of the companyModule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-modules/{id}")
    public ResponseEntity<Void> deleteCompanyModule(@PathVariable Long id) {
        log.debug("REST request to delete CompanyModule : {}", id);
        companyModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
