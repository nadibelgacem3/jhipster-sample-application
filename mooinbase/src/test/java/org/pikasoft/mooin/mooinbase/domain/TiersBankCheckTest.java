package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class TiersBankCheckTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TiersBankCheck.class);
        TiersBankCheck tiersBankCheck1 = new TiersBankCheck();
        tiersBankCheck1.setId(1L);
        TiersBankCheck tiersBankCheck2 = new TiersBankCheck();
        tiersBankCheck2.setId(tiersBankCheck1.getId());
        assertThat(tiersBankCheck1).isEqualTo(tiersBankCheck2);
        tiersBankCheck2.setId(2L);
        assertThat(tiersBankCheck1).isNotEqualTo(tiersBankCheck2);
        tiersBankCheck1.setId(null);
        assertThat(tiersBankCheck1).isNotEqualTo(tiersBankCheck2);
    }
}
