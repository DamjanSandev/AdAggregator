package adg.backend.listeners;

import adg.backend.events.InteractionCreatedEvent;
import adg.backend.model.domain.Interaction;
import adg.backend.model.enumerations.InteractionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InteractionListener {

    private static final Set<InteractionType> STRONG_EVENTS =
            EnumSet.of(InteractionType.CLICK, InteractionType.FAV);

    private final WebClient recommenderClient;

    @Async
    @EventListener
    public void on(InteractionCreatedEvent evt) {
        Interaction i = evt.getInteraction();
        if (!STRONG_EVENTS.contains(i.getInteraction_type())) return;

        var payload = Map.of(
                "user",  i.getUser().getUsername(),
                "ad_id", i.getAd().getId(),
                "interaction_type", i.getInteraction_type().name(),
                "strength", i.getStrength()
        );

        recommenderClient.post()
                .uri("/interaction")
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(r -> log.debug("RT rec sent for {}", payload))
                .doOnError(e -> log.error("Failed to RT-send interaction", e))
                .subscribe();
    }
}
