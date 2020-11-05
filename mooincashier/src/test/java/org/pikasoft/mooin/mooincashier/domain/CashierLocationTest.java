package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierLocation.class);
        CashierLocation cashierLocation1 = new CashierLocation();
        cashierLocation1.setId(1L);
        CashierLocation cashierLocation2 = new CashierLocation();
        cashierLocation2.setId(cashierLocation1.getId());
        assertThat(cashierLocation1).isEqualTo(cashierLocation2);
        cashierLocation2.setId(2L);
        assertThat(cashierLocation1).isNotEqualTo(cashierLocation2);
        cashierLocation1.setId(null);
        assertThat(cashierLocation1).isNotEqualTo(cashierLocation2);
    }
}
