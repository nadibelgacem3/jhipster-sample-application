package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.CompanyModule;
import org.pikasoft.mooin.mooincompanies.repository.CompanyModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyModule}.
 */
@Service
@Transactional
public class CompanyModuleService {

    private final Logger log = LoggerFactory.getLogger(CompanyModuleService.class);

    private final CompanyModuleRepository companyModuleRepository;

    public CompanyModuleService(CompanyModuleRepository companyModuleRepository) {
        this.companyModuleRepository = companyModuleRepository;
    }

    /**
     * Save a companyModule.
     *
     * @param companyModule the entity to save.
     * @return the persisted entity.
     */
    public CompanyModule save(CompanyModule companyModule) {
        log.debug("Request to save CompanyModule : {}", companyModule);
        return companyModuleRepository.save(companyModule);
    }

    /**
     * Get all the companyModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyModule> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyModules");
        return companyModuleRepository.findAll(pageable);
    }


    /**
     * Get one companyModule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyModule> findOne(Long id) {
        log.debug("Request to get CompanyModule : {}", id);
        return companyModuleRepository.findById(id);
    }

    /**
     * Delete the companyModule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyModule : {}", id);
        companyModuleRepository.deleteById(id);
    }
}
