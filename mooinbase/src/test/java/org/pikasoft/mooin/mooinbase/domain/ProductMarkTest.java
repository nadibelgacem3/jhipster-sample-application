package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class ProductMarkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMark.class);
        ProductMark productMark1 = new ProductMark();
        productMark1.setId(1L);
        ProductMark productMark2 = new ProductMark();
        productMark2.setId(productMark1.getId());
        assertThat(productMark1).isEqualTo(productMark2);
        productMark2.setId(2L);
        assertThat(productMark1).isNotEqualTo(productMark2);
        productMark1.setId(null);
        assertThat(productMark1).isNotEqualTo(productMark2);
    }
}
