package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierApproItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierApproItem.class);
        CashierApproItem cashierApproItem1 = new CashierApproItem();
        cashierApproItem1.setId(1L);
        CashierApproItem cashierApproItem2 = new CashierApproItem();
        cashierApproItem2.setId(cashierApproItem1.getId());
        assertThat(cashierApproItem1).isEqualTo(cashierApproItem2);
        cashierApproItem2.setId(2L);
        assertThat(cashierApproItem1).isNotEqualTo(cashierApproItem2);
        cashierApproItem1.setId(null);
        assertThat(cashierApproItem1).isNotEqualTo(cashierApproItem2);
    }
}
