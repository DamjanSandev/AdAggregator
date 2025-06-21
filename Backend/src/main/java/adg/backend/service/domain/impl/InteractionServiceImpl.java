package adg.backend.service.domain.impl;

import adg.backend.model.domain.Ad;
import adg.backend.model.domain.Interaction;
import adg.backend.model.domain.User;
import adg.backend.repository.InteractionRepository;
import adg.backend.service.domain.InteractionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionServiceImpl implements InteractionService {
    private final InteractionRepository interactionRepository;

    public InteractionServiceImpl(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    @Override
    public Optional<Interaction> findById(Long id) {
        return interactionRepository.findById(id);
    }

    @Override
    public List<Interaction> findAllByUser(User user) {
        return interactionRepository.findAllByUser(user);
    }

    @Override
    public List<Interaction> findByUserAndAd(User user, Ad ad) {
        return interactionRepository.findAllByUserAndAd(user, ad);
    }

    @Override
    public Optional<Interaction> save(Interaction interaction) {
        if (interaction.getUser() == null || interaction.getAd() == null) {
            return Optional.empty();
        }
        return Optional.of(interactionRepository.save(interaction));
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
