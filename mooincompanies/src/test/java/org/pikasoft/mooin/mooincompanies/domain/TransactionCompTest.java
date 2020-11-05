package org.pikasoft.mooin.mooincompanies.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooincompanies.web.rest.TestUtil;

public class TransactionCompTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionComp.class);
        TransactionComp transactionComp1 = new TransactionComp();
        transactionComp1.setId(1L);
        TransactionComp transactionComp2 = new TransactionComp();
        transactionComp2.setId(transactionComp1.getId());
        assertThat(transactionComp1).isEqualTo(transactionComp2);
        transactionComp2.setId(2L);
        assertThat(transactionComp1).isNotEqualTo(transactionComp2);
        transactionComp1.setId(null);
        assertThat(transactionComp1).isNotEqualTo(transactionComp2);
    }
}
