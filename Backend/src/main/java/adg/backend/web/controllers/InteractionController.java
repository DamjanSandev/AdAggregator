package adg.backend.web.controllers;

import adg.backend.dto.request.InteractionRequestDTO;
import adg.backend.dto.response.InteractionResponseDto;
import adg.backend.service.application.InteractionApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/findByUser")
    public ResponseEntity<List<InteractionResponseDto>> findAllByUser(@RequestParam String username) {
        return ResponseEntity.ok(interactionApplicationService.findAllByUser(username));
    }

    @GetMapping("/findByUserAndAd")
    public ResponseEntity<List<InteractionResponseDto>> findAllByUserAndAd(
            @RequestParam String username,
            @RequestParam Long adId) {
        return ResponseEntity.ok(interactionApplicationService.findByUserAndAd(username, adId));
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
