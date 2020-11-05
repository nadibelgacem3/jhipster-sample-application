package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class BLItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BLItem.class);
        BLItem bLItem1 = new BLItem();
        bLItem1.setId(1L);
        BLItem bLItem2 = new BLItem();
        bLItem2.setId(bLItem1.getId());
        assertThat(bLItem1).isEqualTo(bLItem2);
        bLItem2.setId(2L);
        assertThat(bLItem1).isNotEqualTo(bLItem2);
        bLItem1.setId(null);
        assertThat(bLItem1).isNotEqualTo(bLItem2);
    }
}
