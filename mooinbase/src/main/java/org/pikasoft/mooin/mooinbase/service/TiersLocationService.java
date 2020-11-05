package org.pikasoft.mooin.mooinbase.service;

import org.pikasoft.mooin.mooinbase.domain.TiersLocation;
import org.pikasoft.mooin.mooinbase.repository.TiersLocationRepository;
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
 * Service Implementation for managing {@link TiersLocation}.
 */
@Service
@Transactional
public class TiersLocationService {

    private final Logger log = LoggerFactory.getLogger(TiersLocationService.class);

    private final TiersLocationRepository tiersLocationRepository;

    public TiersLocationService(TiersLocationRepository tiersLocationRepository) {
        this.tiersLocationRepository = tiersLocationRepository;
    }

    /**
     * Save a tiersLocation.
     *
     * @param tiersLocation the entity to save.
     * @return the persisted entity.
     */
    public TiersLocation save(TiersLocation tiersLocation) {
        log.debug("Request to save TiersLocation : {}", tiersLocation);
        return tiersLocationRepository.save(tiersLocation);
    }

    /**
     * Get all the tiersLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TiersLocation> findAll(Pageable pageable) {
        log.debug("Request to get all TiersLocations");
        return tiersLocationRepository.findAll(pageable);
    }



    /**
     *  Get all the tiersLocations where Tiers is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TiersLocation> findAllWhereTiersIsNull() {
        log.debug("Request to get all tiersLocations where Tiers is null");
        return StreamSupport
            .stream(tiersLocationRepository.findAll().spliterator(), false)
            .filter(tiersLocation -> tiersLocation.getTiers() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one tiersLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TiersLocation> findOne(Long id) {
        log.debug("Request to get TiersLocation : {}", id);
        return tiersLocationRepository.findById(id);
    }

    /**
     * Delete the tiersLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TiersLocation : {}", id);
        tiersLocationRepository.deleteById(id);
    }
}
