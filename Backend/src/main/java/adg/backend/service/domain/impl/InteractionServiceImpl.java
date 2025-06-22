package adg.backend.service.domain.impl;

import adg.backend.events.InteractionCreatedEvent;
import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;
import adg.backend.model.enumerations.InteractionType;
import adg.backend.repository.InteractionRepository;
import adg.backend.service.domain.InteractionService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionServiceImpl implements InteractionService {
    private final InteractionRepository interactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public InteractionServiceImpl(InteractionRepository interactionRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.interactionRepository = interactionRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<Interaction> findById(Long id) {
        return interactionRepository.findById(id);
    }

    @Override
    public List<Interaction> findAllByUserAndInteraction(User user, InteractionType interactionType) {
        return interactionRepository.findAllByUserAndInteraction(user, interactionType);
    }

    @Override
    public Optional<Interaction> findByUserAndAdAndInteraction(User user, Ad ad, InteractionType interactionType) {
        return interactionRepository.findByUserAndAdAndInteraction(user, ad, interactionType);
    }

    @Override
    @Transactional
    public Optional<Interaction> save(Interaction interaction) {
        if (interaction.getUser() == null || interaction.getAd() == null) {
            return Optional.empty();
        }
        Interaction saved = interactionRepository.save(interaction);
        applicationEventPublisher.publishEvent(new InteractionCreatedEvent(saved));
        return Optional.of(saved);
    }


    @Override
    public Optional<Interaction> deleteById(Long id) {
        return interactionRepository.findById(id).map(interaction ->
        {
            interactionRepository.delete(interaction);
            return interaction;
        });
    }
}
