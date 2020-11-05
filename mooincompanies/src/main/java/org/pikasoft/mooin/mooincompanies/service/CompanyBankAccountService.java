package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.CompanyBankAccount;
import org.pikasoft.mooin.mooincompanies.repository.CompanyBankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link CompanyBankAccount}.
 */
@Service
@Transactional
public class CompanyBankAccountService {

    private final Logger log = LoggerFactory.getLogger(CompanyBankAccountService.class);

    private final CompanyBankAccountRepository companyBankAccountRepository;

    public CompanyBankAccountService(CompanyBankAccountRepository companyBankAccountRepository) {
        this.companyBankAccountRepository = companyBankAccountRepository;
    }

    /**
     * Save a companyBankAccount.
     *
     * @param companyBankAccount the entity to save.
     * @return the persisted entity.
     */
    public CompanyBankAccount save(CompanyBankAccount companyBankAccount) {
        log.debug("Request to save CompanyBankAccount : {}", companyBankAccount);
        return companyBankAccountRepository.save(companyBankAccount);
    }

    /**
     * Get all the companyBankAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyBankAccount> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyBankAccounts");
        return companyBankAccountRepository.findAll(pageable);
    }



    /**
     *  Get all the companyBankAccounts where Company is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CompanyBankAccount> findAllWhereCompanyIsNull() {
        log.debug("Request to get all companyBankAccounts where Company is null");
        return StreamSupport
            .stream(companyBankAccountRepository.findAll().spliterator(), false)
            .filter(companyBankAccount -> companyBankAccount.getCompany() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one companyBankAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyBankAccount> findOne(Long id) {
        log.debug("Request to get CompanyBankAccount : {}", id);
        return companyBankAccountRepository.findById(id);
    }

    /**
     * Delete the companyBankAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyBankAccount : {}", id);
        companyBankAccountRepository.deleteById(id);
    }
}
