package adg.backend.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommenderSchedulerJob {

    private final WebClient recommenderClient;

    @Scheduled(cron = "0 0 3 * * *")
    public void refreshCosineIndex() {
        recommenderClient.post()
                .uri("/refresh")
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(r -> log.info("Recommender index refreshed"))
                .doOnError(e -> log.error("Failed to refresh recommender index", e))
                .subscribe();
    }
}
