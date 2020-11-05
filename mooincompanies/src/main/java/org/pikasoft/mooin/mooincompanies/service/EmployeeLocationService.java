package org.pikasoft.mooin.mooincompanies.service;

import org.pikasoft.mooin.mooincompanies.domain.EmployeeLocation;
import org.pikasoft.mooin.mooincompanies.repository.EmployeeLocationRepository;
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
 * Service Implementation for managing {@link EmployeeLocation}.
 */
@Service
@Transactional
public class EmployeeLocationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeLocationService.class);

    private final EmployeeLocationRepository employeeLocationRepository;

    public EmployeeLocationService(EmployeeLocationRepository employeeLocationRepository) {
        this.employeeLocationRepository = employeeLocationRepository;
    }

    /**
     * Save a employeeLocation.
     *
     * @param employeeLocation the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLocation save(EmployeeLocation employeeLocation) {
        log.debug("Request to save EmployeeLocation : {}", employeeLocation);
        return employeeLocationRepository.save(employeeLocation);
    }

    /**
     * Get all the employeeLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLocation> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeLocations");
        return employeeLocationRepository.findAll(pageable);
    }



    /**
     *  Get all the employeeLocations where Employee is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<EmployeeLocation> findAllWhereEmployeeIsNull() {
        log.debug("Request to get all employeeLocations where Employee is null");
        return StreamSupport
            .stream(employeeLocationRepository.findAll().spliterator(), false)
            .filter(employeeLocation -> employeeLocation.getEmployee() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one employeeLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeLocation> findOne(Long id) {
        log.debug("Request to get EmployeeLocation : {}", id);
        return employeeLocationRepository.findById(id);
    }

    /**
     * Delete the employeeLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeLocation : {}", id);
        employeeLocationRepository.deleteById(id);
    }
}
