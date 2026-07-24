package com.amex.benefit_activation_engine.integration.feed;

import com.amex.benefit_activation_engine.model.Transaction;
import com.amex.benefit_activation_engine.service.IngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Demo delivery mechanism: a scheduled job that "drips" a randomized purchase
 * into the ingestion pipeline every few seconds, simulating a live stream of
 * card activity without any external infrastructure.
 *
 * <p>Active only when {@code feed.type=local-scheduled}. Interchange with
 * {@link PubSubFeed} by flipping that one flag.</p>
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "feed", name = "type", havingValue = "local-scheduled")
@RequiredArgsConstructor
public class LocalScheduledFeed implements TransactionFeed {

    private final IngestionService ingestionService;
    private final SamplePurchaseGenerator generator;

    @Override
    public String name() {
        return "local-scheduled";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * Emits one simulated purchase per tick. Interval and initial delay are
     * configurable via {@code feed.local.interval-ms} / {@code feed.local.initial-delay-ms}.
     */
    @Scheduled(
            fixedDelayString = "${feed.local.interval-ms:5000}",
            initialDelayString = "${feed.local.initial-delay-ms:5000}")
    public void emit() {
        Transaction transaction = ingestionService.ingest(generator.next());
        log.info("[local-scheduled feed] dripped transaction {} — {} {} at {} ({})",
                transaction.getId(), transaction.getAmount(), transaction.getCurrency(),
                transaction.getMerchantName(), transaction.getCardProduct());
    }
}
