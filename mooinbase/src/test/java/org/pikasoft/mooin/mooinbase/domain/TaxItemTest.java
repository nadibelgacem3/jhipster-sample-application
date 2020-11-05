package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class TaxItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxItem.class);
        TaxItem taxItem1 = new TaxItem();
        taxItem1.setId(1L);
        TaxItem taxItem2 = new TaxItem();
        taxItem2.setId(taxItem1.getId());
        assertThat(taxItem1).isEqualTo(taxItem2);
        taxItem2.setId(2L);
        assertThat(taxItem1).isNotEqualTo(taxItem2);
        taxItem1.setId(null);
        assertThat(taxItem1).isNotEqualTo(taxItem2);
    }
}
