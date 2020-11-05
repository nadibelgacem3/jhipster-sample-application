package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class TVATest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TVA.class);
        TVA tVA1 = new TVA();
        tVA1.setId(1L);
        TVA tVA2 = new TVA();
        tVA2.setId(tVA1.getId());
        assertThat(tVA1).isEqualTo(tVA2);
        tVA2.setId(2L);
        assertThat(tVA1).isNotEqualTo(tVA2);
        tVA1.setId(null);
        assertThat(tVA1).isNotEqualTo(tVA2);
    }
}
