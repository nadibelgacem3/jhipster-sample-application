package org.pikasoft.mooin.mooincashier.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincashier.web.rest.TestUtil;

public class CashierReceiptItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashierReceiptItem.class);
        CashierReceiptItem cashierReceiptItem1 = new CashierReceiptItem();
        cashierReceiptItem1.setId(1L);
        CashierReceiptItem cashierReceiptItem2 = new CashierReceiptItem();
        cashierReceiptItem2.setId(cashierReceiptItem1.getId());
        assertThat(cashierReceiptItem1).isEqualTo(cashierReceiptItem2);
        cashierReceiptItem2.setId(2L);
        assertThat(cashierReceiptItem1).isNotEqualTo(cashierReceiptItem2);
        cashierReceiptItem1.setId(null);
        assertThat(cashierReceiptItem1).isNotEqualTo(cashierReceiptItem2);
    }
}
