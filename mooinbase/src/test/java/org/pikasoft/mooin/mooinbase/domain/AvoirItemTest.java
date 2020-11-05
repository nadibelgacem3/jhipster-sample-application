package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class AvoirItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvoirItem.class);
        AvoirItem avoirItem1 = new AvoirItem();
        avoirItem1.setId(1L);
        AvoirItem avoirItem2 = new AvoirItem();
        avoirItem2.setId(avoirItem1.getId());
        assertThat(avoirItem1).isEqualTo(avoirItem2);
        avoirItem2.setId(2L);
        assertThat(avoirItem1).isNotEqualTo(avoirItem2);
        avoirItem1.setId(null);
        assertThat(avoirItem1).isNotEqualTo(avoirItem2);
    }
}
