package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierReceiptPayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierReceiptPay.class);
        CashierReceiptPay cashierReceiptPay1 = new CashierReceiptPay();
        cashierReceiptPay1.setId(1L);
        CashierReceiptPay cashierReceiptPay2 = new CashierReceiptPay();
        cashierReceiptPay2.setId(cashierReceiptPay1.getId());
        assertThat(cashierReceiptPay1).isEqualTo(cashierReceiptPay2);
        cashierReceiptPay2.setId(2L);
        assertThat(cashierReceiptPay1).isNotEqualTo(cashierReceiptPay2);
        cashierReceiptPay1.setId(null);
        assertThat(cashierReceiptPay1).isNotEqualTo(cashierReceiptPay2);
    }
}
