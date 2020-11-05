package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class CompanyBankAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBankAccount.class);
        CompanyBankAccount companyBankAccount1 = new CompanyBankAccount();
        companyBankAccount1.setId(1L);
        CompanyBankAccount companyBankAccount2 = new CompanyBankAccount();
        companyBankAccount2.setId(companyBankAccount1.getId());
        assertThat(companyBankAccount1).isEqualTo(companyBankAccount2);
        companyBankAccount2.setId(2L);
        assertThat(companyBankAccount1).isNotEqualTo(companyBankAccount2);
        companyBankAccount1.setId(null);
        assertThat(companyBankAccount1).isNotEqualTo(companyBankAccount2);
    }
}
