package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierProduct.class);
        CashierProduct cashierProduct1 = new CashierProduct();
        cashierProduct1.setId(1L);
        CashierProduct cashierProduct2 = new CashierProduct();
        cashierProduct2.setId(cashierProduct1.getId());
        assertThat(cashierProduct1).isEqualTo(cashierProduct2);
        cashierProduct2.setId(2L);
        assertThat(cashierProduct1).isNotEqualTo(cashierProduct2);
        cashierProduct1.setId(null);
        assertThat(cashierProduct1).isNotEqualTo(cashierProduct2);
    }
}
