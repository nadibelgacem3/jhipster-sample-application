package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class CompanyBillPaymentItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBillPaymentItem.class);
        CompanyBillPaymentItem companyBillPaymentItem1 = new CompanyBillPaymentItem();
        companyBillPaymentItem1.setId(1L);
        CompanyBillPaymentItem companyBillPaymentItem2 = new CompanyBillPaymentItem();
        companyBillPaymentItem2.setId(companyBillPaymentItem1.getId());
        assertThat(companyBillPaymentItem1).isEqualTo(companyBillPaymentItem2);
        companyBillPaymentItem2.setId(2L);
        assertThat(companyBillPaymentItem1).isNotEqualTo(companyBillPaymentItem2);
        companyBillPaymentItem1.setId(null);
        assertThat(companyBillPaymentItem1).isNotEqualTo(companyBillPaymentItem2);
    }
}
