package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class TVAItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TVAItem.class);
        TVAItem tVAItem1 = new TVAItem();
        tVAItem1.setId(1L);
        TVAItem tVAItem2 = new TVAItem();
        tVAItem2.setId(tVAItem1.getId());
        assertThat(tVAItem1).isEqualTo(tVAItem2);
        tVAItem2.setId(2L);
        assertThat(tVAItem1).isNotEqualTo(tVAItem2);
        tVAItem1.setId(null);
        assertThat(tVAItem1).isNotEqualTo(tVAItem2);
    }
}
