package adg.backend.web.controllers;

import adg.backend.dto.response.ModelResponseDto;
import adg.backend.service.application.ModelApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelController {
    private final ModelApplicationService modelApplicationService;

    public ModelController(ModelApplicationService modelApplicationService) {
        this.modelApplicationService = modelApplicationService;
    }

    @GetMapping("/by-brand")
    public ResponseEntity<List<ModelResponseDto>> findByBrand(@RequestParam String brand) {
        return ResponseEntity.ok(modelApplicationService.findByBrand(brand));
    }
}
