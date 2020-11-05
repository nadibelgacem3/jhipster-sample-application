package org.pikasoft.mooin.mooinecommerce.service;

import org.pikasoft.mooin.mooinecommerce.domain.OfferOrder;
import org.pikasoft.mooin.mooinecommerce.repository.OfferOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OfferOrder}.
 */
@Service
@Transactional
public class OfferOrderService {

    private final Logger log = LoggerFactory.getLogger(OfferOrderService.class);

    private final OfferOrderRepository offerOrderRepository;

    public OfferOrderService(OfferOrderRepository offerOrderRepository) {
        this.offerOrderRepository = offerOrderRepository;
    }

    /**
     * Save a offerOrder.
     *
     * @param offerOrder the entity to save.
     * @return the persisted entity.
     */
    public OfferOrder save(OfferOrder offerOrder) {
        log.debug("Request to save OfferOrder : {}", offerOrder);
        return offerOrderRepository.save(offerOrder);
    }

    /**
     * Get all the offerOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferOrder> findAll(Pageable pageable) {
        log.debug("Request to get all OfferOrders");
        return offerOrderRepository.findAll(pageable);
    }


    /**
     * Get one offerOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OfferOrder> findOne(Long id) {
        log.debug("Request to get OfferOrder : {}", id);
        return offerOrderRepository.findById(id);
    }

    /**
     * Delete the offerOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferOrder : {}", id);
        offerOrderRepository.deleteById(id);
    }
}
