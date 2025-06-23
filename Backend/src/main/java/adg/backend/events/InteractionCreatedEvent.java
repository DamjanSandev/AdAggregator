package adg.backend.events;

import adg.backend.model.domain.Interaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InteractionCreatedEvent extends ApplicationEvent {
    private final Interaction interaction;

    public InteractionCreatedEvent(Interaction interaction) {
        super(interaction);
        this.interaction = interaction;
    }
}
