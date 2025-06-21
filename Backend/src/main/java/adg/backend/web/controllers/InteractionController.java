package adg.backend.web.controllers;

import adg.backend.dto.request.InteractionRequestDTO;
import adg.backend.dto.response.InteractionResponseDto;
import adg.backend.model.enumerations.InteractionType;
import adg.backend.service.application.InteractionApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {
    private final InteractionApplicationService interactionApplicationService;

    public InteractionController(InteractionApplicationService interactionApplicationService) {
        this.interactionApplicationService = interactionApplicationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteractionResponseDto> findById(@PathVariable Long id) {
        return interactionApplicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByUserAndInteraction")
    public ResponseEntity<List<InteractionResponseDto>> findAllByUserAndInteraction(@RequestParam String username, @RequestParam InteractionType interactionType) {
        return ResponseEntity.ok(interactionApplicationService.findAllByUserAndInteraction(username, interactionType));
    }

    @GetMapping("/findByUserAndAdAndInteraction")
    public ResponseEntity<InteractionResponseDto> findAllByUserAndAdAndInteraction(
            @RequestParam String username,
            @RequestParam Long adId,
            @RequestParam InteractionType interactionType) {
        return interactionApplicationService.findByUserAndAdAndInteraction(username, adId, interactionType).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<InteractionResponseDto> save(@RequestBody InteractionRequestDTO interaction) {
        return interactionApplicationService.save(interaction)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InteractionResponseDto> delete(@PathVariable Long id) {
        return interactionApplicationService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
