package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cashier.class);
        Cashier cashier1 = new Cashier();
        cashier1.setId(1L);
        Cashier cashier2 = new Cashier();
        cashier2.setId(cashier1.getId());
        assertThat(cashier1).isEqualTo(cashier2);
        cashier2.setId(2L);
        assertThat(cashier1).isNotEqualTo(cashier2);
        cashier1.setId(null);
        assertThat(cashier1).isNotEqualTo(cashier2);
    }
}
