package org.pikasoft.mooin.mooinbase.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.pikasoft.mooin.mooinbase.web.rest.TestUtil;

public class QuoteItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteItem.class);
        QuoteItem quoteItem1 = new QuoteItem();
        quoteItem1.setId(1L);
        QuoteItem quoteItem2 = new QuoteItem();
        quoteItem2.setId(quoteItem1.getId());
        assertThat(quoteItem1).isEqualTo(quoteItem2);
        quoteItem2.setId(2L);
        assertThat(quoteItem1).isNotEqualTo(quoteItem2);
        quoteItem1.setId(null);
        assertThat(quoteItem1).isNotEqualTo(quoteItem2);
    }
}
