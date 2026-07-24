package com.amex.benefit_activation_engine.integration.feed;

import com.amex.benefit_activation_engine.dto.CreateTransactionRequest;
import com.amex.benefit_activation_engine.model.Transaction;
import com.amex.benefit_activation_engine.service.IngestionService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for the local feed drip: {@code emit()} must pull a purchase from
 * the generator and push it through the ingestion pipeline. Uses a recording
 * fake so no Spring context or database is needed.
 */
class LocalScheduledFeedTest {

    /** Fake IngestionService that records the ingested request. */
    private static class RecordingIngestionService extends IngestionService {
        private CreateTransactionRequest captured;

        RecordingIngestionService() {
            super(null, null);
        }

        @Override
        public Transaction ingest(CreateTransactionRequest request) {
            this.captured = request;
            return Transaction.builder()
                    .id(1L)
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .merchantName(request.getMerchantName())
                    .cardProduct(request.getCardProduct())
                    .build();
        }
    }

    @Test
    void emitIngestsAGeneratedPurchase() {
        RecordingIngestionService ingestion = new RecordingIngestionService();
        LocalScheduledFeed feed = new LocalScheduledFeed(ingestion, new SamplePurchaseGenerator());

        feed.emit();

        assertThat(ingestion.captured).isNotNull();
        assertThat(ingestion.captured.getAmount()).isNotNull();
        assertThat(ingestion.captured.getCardProduct()).isIn("PLATINUM", "GOLD", "GREEN");
        assertThat(feed.name()).isEqualTo("local-scheduled");
        assertThat(feed.isActive()).isTrue();
    }
}
