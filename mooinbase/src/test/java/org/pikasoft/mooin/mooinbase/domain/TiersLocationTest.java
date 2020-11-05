package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class TiersLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiersLocation.class);
        TiersLocation tiersLocation1 = new TiersLocation();
        tiersLocation1.setId(1L);
        TiersLocation tiersLocation2 = new TiersLocation();
        tiersLocation2.setId(tiersLocation1.getId());
        assertThat(tiersLocation1).isEqualTo(tiersLocation2);
        tiersLocation2.setId(2L);
        assertThat(tiersLocation1).isNotEqualTo(tiersLocation2);
        tiersLocation1.setId(null);
        assertThat(tiersLocation1).isNotEqualTo(tiersLocation2);
    }
}
