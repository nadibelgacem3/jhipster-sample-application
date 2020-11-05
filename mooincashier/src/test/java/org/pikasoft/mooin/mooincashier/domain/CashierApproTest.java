package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierApproTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierAppro.class);
        CashierAppro cashierAppro1 = new CashierAppro();
        cashierAppro1.setId(1L);
        CashierAppro cashierAppro2 = new CashierAppro();
        cashierAppro2.setId(cashierAppro1.getId());
        assertThat(cashierAppro1).isEqualTo(cashierAppro2);
        cashierAppro2.setId(2L);
        assertThat(cashierAppro1).isNotEqualTo(cashierAppro2);
        cashierAppro1.setId(null);
        assertThat(cashierAppro1).isNotEqualTo(cashierAppro2);
    }
}
