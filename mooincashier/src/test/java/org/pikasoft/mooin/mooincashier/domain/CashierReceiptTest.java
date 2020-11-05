package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierReceiptTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierReceipt.class);
        CashierReceipt cashierReceipt1 = new CashierReceipt();
        cashierReceipt1.setId(1L);
        CashierReceipt cashierReceipt2 = new CashierReceipt();
        cashierReceipt2.setId(cashierReceipt1.getId());
        assertThat(cashierReceipt1).isEqualTo(cashierReceipt2);
        cashierReceipt2.setId(2L);
        assertThat(cashierReceipt1).isNotEqualTo(cashierReceipt2);
        cashierReceipt1.setId(null);
        assertThat(cashierReceipt1).isNotEqualTo(cashierReceipt2);
    }
}
