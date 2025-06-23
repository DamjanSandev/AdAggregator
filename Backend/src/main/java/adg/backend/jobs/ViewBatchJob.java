package adg.backend.jobs;

import adg.backend.repository.InteractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewBatchJob {

    private final InteractionRepository interactionRepository;
    private final WebClient recommenderClient;

    @Scheduled(fixedDelay = 3600_000)
    @Transactional
    public void flushViews() {
        Instant since = Instant.now().minusSeconds(3600);
        var recentViews = interactionRepository.findViewsSince(since);

        if (recentViews.isEmpty()) return;

        var batch = recentViews.stream()
                .collect(Collectors.groupingBy(v -> v.getUser().getUsername()));

        recommenderClient.post()
                .uri("/interaction/batch")
                .bodyValue(batch)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(r -> log.info("Flushed {} views", recentViews.size()))
                .subscribe();
    }
}
