package adg.backend.web.controllers;

import adg.backend.dto.request.AdRequestDto;
import adg.backend.dto.response.AdResponseDto;
import adg.backend.dto.search.SearchAdDto;
import adg.backend.model.enumerations.ads.*;
import adg.backend.service.application.AdApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")

public class AdController {
    private final AdApplicationService adService;

    public AdController(AdApplicationService adService) {
        this.adService = adService;
    }

    @GetMapping
    public ResponseEntity<List<AdResponseDto>> findAll(@RequestParam(required = false) String brand,
                                                       @RequestParam(required = false) String model,
                                                       @RequestParam(required = false) Integer fromYear,
                                                       @RequestParam(required = false) Integer toYear,
                                                       @RequestParam(required = false) FuelType fuelType,
                                                       @RequestParam(required = false) TransmissionType transmission,
                                                       @RequestParam(required = false) BodyType bodyType,
                                                       @RequestParam(required = false) String color,
                                                       @RequestParam(required = false) RegistrationType registrationType,
                                                       @RequestParam(required = false) EmissionType emissionType,
                                                       @RequestParam(required = false) Integer fromKilometers,
                                                       @RequestParam(required = false) Integer toKilometers,
                                                       @RequestParam(required = false) String enginePower) {
        SearchAdDto searchAdDto = new SearchAdDto(brand, model, fromYear, toYear, fuelType, transmission, bodyType, color, registrationType, emissionType, fromKilometers, toKilometers, enginePower);
        return ResponseEntity.ok(this.adService.findAll(searchAdDto));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AdResponseDto>> findAll(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) TransmissionType transmission,
            @RequestParam(required = false) BodyType bodyType,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) RegistrationType registrationType,
            @RequestParam(required = false) EmissionType emissionType,
            @RequestParam(required = false) Integer fromKilometers,
            @RequestParam(required = false) Integer toKilometers,
            @RequestParam(required = false) String enginePower,
            @RequestParam Pageable pageable
    ) {
        SearchAdDto searchAdDto = new SearchAdDto(brand, model, fromYear, toYear, fuelType, transmission, bodyType, color, registrationType, emissionType, fromKilometers, toKilometers, enginePower);
        return ResponseEntity.ok(this.adService.findAll(searchAdDto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdResponseDto> findById(@PathVariable Long id) {
        return this.adService
                .findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<AdResponseDto> save(@RequestBody AdRequestDto ad) {
        return this.adService.save(ad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdResponseDto> update(@PathVariable Long id, @RequestBody AdRequestDto ad) {
        return this.adService.update(id, ad)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AdResponseDto> delete(@PathVariable Long id) {
        return this.adService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
