package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class CompanyBillPaymentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBillPayment.class);
        CompanyBillPayment companyBillPayment1 = new CompanyBillPayment();
        companyBillPayment1.setId(1L);
        CompanyBillPayment companyBillPayment2 = new CompanyBillPayment();
        companyBillPayment2.setId(companyBillPayment1.getId());
        assertThat(companyBillPayment1).isEqualTo(companyBillPayment2);
        companyBillPayment2.setId(2L);
        assertThat(companyBillPayment1).isNotEqualTo(companyBillPayment2);
        companyBillPayment1.setId(null);
        assertThat(companyBillPayment1).isNotEqualTo(companyBillPayment2);
    }
}
