package org.pikasoft.mooin.mooinecommerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinecommerce.web.rest.TestUtil;

public class OfferOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferOrder.class);
        OfferOrder offerOrder1 = new OfferOrder();
        offerOrder1.setId(1L);
        OfferOrder offerOrder2 = new OfferOrder();
        offerOrder2.setId(offerOrder1.getId());
        assertThat(offerOrder1).isEqualTo(offerOrder2);
        offerOrder2.setId(2L);
        assertThat(offerOrder1).isNotEqualTo(offerOrder2);
        offerOrder1.setId(null);
        assertThat(offerOrder1).isNotEqualTo(offerOrder2);
    }
}
