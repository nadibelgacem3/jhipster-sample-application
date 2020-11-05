package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class CompanyModuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyModule.class);
        CompanyModule companyModule1 = new CompanyModule();
        companyModule1.setId(1L);
        CompanyModule companyModule2 = new CompanyModule();
        companyModule2.setId(companyModule1.getId());
        assertThat(companyModule1).isEqualTo(companyModule2);
        companyModule2.setId(2L);
        assertThat(companyModule1).isNotEqualTo(companyModule2);
        companyModule1.setId(null);
        assertThat(companyModule1).isNotEqualTo(companyModule2);
    }
}
