package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierCostumerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierCostumer.class);
        CashierCostumer cashierCostumer1 = new CashierCostumer();
        cashierCostumer1.setId(1L);
        CashierCostumer cashierCostumer2 = new CashierCostumer();
        cashierCostumer2.setId(cashierCostumer1.getId());
        assertThat(cashierCostumer1).isEqualTo(cashierCostumer2);
        cashierCostumer2.setId(2L);
        assertThat(cashierCostumer1).isNotEqualTo(cashierCostumer2);
        cashierCostumer1.setId(null);
        assertThat(cashierCostumer1).isNotEqualTo(cashierCostumer2);
    }
}
