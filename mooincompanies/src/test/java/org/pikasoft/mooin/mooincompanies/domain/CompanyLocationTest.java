package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class CompanyLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLocation.class);
        CompanyLocation companyLocation1 = new CompanyLocation();
        companyLocation1.setId(1L);
        CompanyLocation companyLocation2 = new CompanyLocation();
        companyLocation2.setId(companyLocation1.getId());
        assertThat(companyLocation1).isEqualTo(companyLocation2);
        companyLocation2.setId(2L);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
        companyLocation1.setId(null);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
    }
}
