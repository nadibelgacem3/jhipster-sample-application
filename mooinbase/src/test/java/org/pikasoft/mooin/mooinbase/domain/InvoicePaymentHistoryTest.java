package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class InvoicePaymentHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoicePaymentHistory.class);
        InvoicePaymentHistory invoicePaymentHistory1 = new InvoicePaymentHistory();
        invoicePaymentHistory1.setId(1L);
        InvoicePaymentHistory invoicePaymentHistory2 = new InvoicePaymentHistory();
        invoicePaymentHistory2.setId(invoicePaymentHistory1.getId());
        assertThat(invoicePaymentHistory1).isEqualTo(invoicePaymentHistory2);
        invoicePaymentHistory2.setId(2L);
        assertThat(invoicePaymentHistory1).isNotEqualTo(invoicePaymentHistory2);
        invoicePaymentHistory1.setId(null);
        assertThat(invoicePaymentHistory1).isNotEqualTo(invoicePaymentHistory2);
    }
}
