package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class BLTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BL.class);
        BL bL1 = new BL();
        bL1.setId(1L);
        BL bL2 = new BL();
        bL2.setId(bL1.getId());
        assertThat(bL1).isEqualTo(bL2);
        bL2.setId(2L);
        assertThat(bL1).isNotEqualTo(bL2);
        bL1.setId(null);
        assertThat(bL1).isNotEqualTo(bL2);
    }
}
