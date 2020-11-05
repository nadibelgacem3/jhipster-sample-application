package org.pikasoft.mooin.mooincompanies.web.rest;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBankAccount;
import org.pikasoft.mooin.mooincompanies.service.CompanyBankAccountService;
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
 * REST controller for managing {@link org.pikasoft.mooin.mooincompanies.domain.CompanyBankAccount}.
 */
@RestController
@RequestMapping("/api")
public class CompanyBankAccountResource {

    private final Logger log = LoggerFactory.getLogger(CompanyBankAccountResource.class);

    private static final String ENTITY_NAME = "mooincompaniesCompanyBankAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyBankAccountService companyBankAccountService;

    public CompanyBankAccountResource(CompanyBankAccountService companyBankAccountService) {
        this.companyBankAccountService = companyBankAccountService;
    }

    /**
     * {@code POST  /company-bank-accounts} : Create a new companyBankAccount.
     *
     * @param companyBankAccount the companyBankAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyBankAccount, or with status {@code 400 (Bad Request)} if the companyBankAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-bank-accounts")
    public ResponseEntity<CompanyBankAccount> createCompanyBankAccount(@RequestBody CompanyBankAccount companyBankAccount) throws URISyntaxException {
        log.debug("REST request to save CompanyBankAccount : {}", companyBankAccount);
        if (companyBankAccount.getId() != null) {
            throw new BadRequestAlertException("A new companyBankAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyBankAccount result = companyBankAccountService.save(companyBankAccount);
        return ResponseEntity.created(new URI("/api/company-bank-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-bank-accounts} : Updates an existing companyBankAccount.
     *
     * @param companyBankAccount the companyBankAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyBankAccount,
     * or with status {@code 400 (Bad Request)} if the companyBankAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyBankAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-bank-accounts")
    public ResponseEntity<CompanyBankAccount> updateCompanyBankAccount(@RequestBody CompanyBankAccount companyBankAccount) throws URISyntaxException {
        log.debug("REST request to update CompanyBankAccount : {}", companyBankAccount);
        if (companyBankAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyBankAccount result = companyBankAccountService.save(companyBankAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyBankAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-bank-accounts} : get all the companyBankAccounts.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyBankAccounts in body.
     */
    @GetMapping("/company-bank-accounts")
    public ResponseEntity<List<CompanyBankAccount>> getAllCompanyBankAccounts(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("company-is-null".equals(filter)) {
            log.debug("REST request to get all CompanyBankAccounts where company is null");
            return new ResponseEntity<>(companyBankAccountService.findAllWhereCompanyIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CompanyBankAccounts");
        Page<CompanyBankAccount> page = companyBankAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-bank-accounts/:id} : get the "id" companyBankAccount.
     *
     * @param id the id of the companyBankAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyBankAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-bank-accounts/{id}")
    public ResponseEntity<CompanyBankAccount> getCompanyBankAccount(@PathVariable Long id) {
        log.debug("REST request to get CompanyBankAccount : {}", id);
        Optional<CompanyBankAccount> companyBankAccount = companyBankAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyBankAccount);
    }

    /**
     * {@code DELETE  /company-bank-accounts/:id} : delete the "id" companyBankAccount.
     *
     * @param id the id of the companyBankAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-bank-accounts/{id}")
    public ResponseEntity<Void> deleteCompanyBankAccount(@PathVariable Long id) {
        log.debug("REST request to delete CompanyBankAccount : {}", id);
        companyBankAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
