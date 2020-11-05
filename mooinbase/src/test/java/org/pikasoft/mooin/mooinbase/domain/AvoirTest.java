package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class AvoirTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avoir.class);
        Avoir avoir1 = new Avoir();
        avoir1.setId(1L);
        Avoir avoir2 = new Avoir();
        avoir2.setId(avoir1.getId());
        assertThat(avoir1).isEqualTo(avoir2);
        avoir2.setId(2L);
        assertThat(avoir1).isNotEqualTo(avoir2);
        avoir1.setId(null);
        assertThat(avoir1).isNotEqualTo(avoir2);
    }
}
