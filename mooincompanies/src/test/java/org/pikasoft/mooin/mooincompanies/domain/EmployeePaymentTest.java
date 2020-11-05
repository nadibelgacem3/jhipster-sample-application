package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class EmployeePaymentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePayment.class);
        EmployeePayment employeePayment1 = new EmployeePayment();
        employeePayment1.setId(1L);
        EmployeePayment employeePayment2 = new EmployeePayment();
        employeePayment2.setId(employeePayment1.getId());
        assertThat(employeePayment1).isEqualTo(employeePayment2);
        employeePayment2.setId(2L);
        assertThat(employeePayment1).isNotEqualTo(employeePayment2);
        employeePayment1.setId(null);
        assertThat(employeePayment1).isNotEqualTo(employeePayment2);
    }
}
