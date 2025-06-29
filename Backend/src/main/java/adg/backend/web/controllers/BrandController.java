package adg.backend.web.controllers;

import adg.backend.dto.response.BrandResponseDto;
import adg.backend.service.application.BrandApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandApplicationService brandApplicationService;

    public BrandController(BrandApplicationService brandApplicationService) {
        this.brandApplicationService = brandApplicationService;
    }

    @GetMapping()
    public ResponseEntity<List<BrandResponseDto>> findALl() {
        return ResponseEntity.ok(brandApplicationService.findAll());
    }

    @GetMapping("/by-name")
    public ResponseEntity<BrandResponseDto> findByName(@RequestParam String name) {
        return brandApplicationService.findByName(name).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
