package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.CompanyLocation;
import org.pikasoft.mooin.mooincompanies.repository.CompanyLocationRepository;
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
 * Service Implementation for managing {@link CompanyLocation}.
 */
@Service
@Transactional
public class CompanyLocationService {

    private final Logger log = LoggerFactory.getLogger(CompanyLocationService.class);

    private final CompanyLocationRepository companyLocationRepository;

    public CompanyLocationService(CompanyLocationRepository companyLocationRepository) {
        this.companyLocationRepository = companyLocationRepository;
    }

    /**
     * Save a companyLocation.
     *
     * @param companyLocation the entity to save.
     * @return the persisted entity.
     */
    public CompanyLocation save(CompanyLocation companyLocation) {
        log.debug("Request to save CompanyLocation : {}", companyLocation);
        return companyLocationRepository.save(companyLocation);
    }

    /**
     * Get all the companyLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyLocation> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyLocations");
        return companyLocationRepository.findAll(pageable);
    }



    /**
     *  Get all the companyLocations where Company is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CompanyLocation> findAllWhereCompanyIsNull() {
        log.debug("Request to get all companyLocations where Company is null");
        return StreamSupport
            .stream(companyLocationRepository.findAll().spliterator(), false)
            .filter(companyLocation -> companyLocation.getCompany() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one companyLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyLocation> findOne(Long id) {
        log.debug("Request to get CompanyLocation : {}", id);
        return companyLocationRepository.findById(id);
    }

    /**
     * Delete the companyLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyLocation : {}", id);
        companyLocationRepository.deleteById(id);
    }
}
