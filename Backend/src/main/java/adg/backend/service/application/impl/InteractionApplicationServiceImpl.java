package adg.backend.service.application.impl;

import adg.backend.dto.request.InteractionRequestDTO;
import adg.backend.dto.response.InteractionResponseDto;
import adg.backend.model.domain.Ad;
import adg.backend.model.domain.User;
import adg.backend.model.exceptions.AdNotFoundException;
import adg.backend.model.exceptions.UserNotFoundException;
import adg.backend.service.application.InteractionApplicationService;
import adg.backend.service.domain.AdService;
import adg.backend.service.domain.InteractionService;
import adg.backend.service.domain.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InteractionApplicationServiceImpl implements InteractionApplicationService {
    private final InteractionService interactionService;
    private final UserService userService;
    private final AdService adService;

    public InteractionApplicationServiceImpl(InteractionService interactionService, UserService userService, AdService adService) {
        this.interactionService = interactionService;
        this.userService = userService;
        this.adService = adService;
    }

    @Override
    public Optional<InteractionResponseDto> findById(Long id) {
        return interactionService.findById(id).map(InteractionResponseDto::from);
    }

    @Override
    public List<InteractionResponseDto> findAllByUser(String userName) {
        User user = userService.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
        return interactionService.findAllByUser(user).stream().map(InteractionResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public List<InteractionResponseDto> findByUserAndAd(String userName, Long adId) {
        User user = userService.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(userName));
        Ad ad = adService.findById(adId)
                .orElseThrow(() -> new AdNotFoundException(adId));
        return interactionService.findByUserAndAd(user, ad).stream()
                .map(InteractionResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public Optional<InteractionResponseDto> save(InteractionRequestDTO interaction) {
        User user = userService.findByUsername(interaction.userUsername())
                .orElseThrow(() -> new UserNotFoundException(interaction.userUsername()));
        Ad ad = adService.findById(interaction.adId())
                .orElseThrow(() -> new AdNotFoundException(interaction.adId()));

        return interactionService.save(interaction.toInteraction(user, ad)).map(InteractionResponseDto::from);
    }

    @Override
    public Optional<InteractionResponseDto> deleteById(Long id) {
        return interactionService.deleteById(id).map(InteractionResponseDto::from);
    }
}
