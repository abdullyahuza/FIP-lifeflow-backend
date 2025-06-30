package com.lifeflow.api.donation.controllers;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifeflow.api.common.exceptions.ResourceNotFoundException;
import com.lifeflow.api.common.responses.ApiErrorResponse;
import com.lifeflow.api.donation.dtos.DonationCenterRequestDto;
import com.lifeflow.api.donation.dtos.DonationCenterResponseDto;
import com.lifeflow.api.donation.dtos.DonationCenterSummaryDto;
import com.lifeflow.api.donation.entities.DonationCenterEntity;
import com.lifeflow.api.donation.mappers.DonationCenterMapper;
import com.lifeflow.api.donation.repositories.DonationCenterRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/donation-centers")
@RequiredArgsConstructor
public class DonationCenterController {

    private final DonationCenterRepository donationCenterRepository;
    private final DonationCenterMapper mapper;

    // Create a donation center
    @PostMapping
    public ResponseEntity<?> create(
        @Valid @RequestBody DonationCenterRequestDto requestDto,
        HttpServletRequest request
    ) {
        if (donationCenterRepository.existsByNameIgnoreCase(requestDto.getName())) {
            ApiErrorResponse error = ApiErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.CONFLICT.value())
                    .error("Conflict")
                    .message("A donation center with the name '" + requestDto.getName() + "' already exists.")
                    .path(request.getRequestURI())
                    .build();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        DonationCenterEntity entity = mapper.toEntity(requestDto);
        DonationCenterEntity saved = donationCenterRepository.save(entity);

        return ResponseEntity.created(URI.create("/api/donation-centers/" + saved.getId()))
                .body(mapper.toResponseDto(saved));
    }



    // Get all donation centers
    @GetMapping
    public ResponseEntity<List<DonationCenterSummaryDto>> getAll() {
        List<DonationCenterSummaryDto> centers = donationCenterRepository.findAll()
                .stream()
                .map(mapper::toSummaryDto)
                .toList();
        return ResponseEntity.ok(centers);
    }

    // Get full details of one center
    @GetMapping("/{id}")
    public ResponseEntity<DonationCenterResponseDto> getById(@PathVariable Long id) {
        DonationCenterEntity entity = donationCenterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Donation center with ID " + id + " not found"));
        
        DonationCenterResponseDto dto = mapper.toResponseDto(entity);
        return ResponseEntity.ok(dto);
    }


    // Update a center
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody DonationCenterRequestDto dto,
            HttpServletRequest request
    ) {
        return donationCenterRepository.findById(id)
            .map(existing -> {
                // Check for name uniqueness excluding the current record
                boolean nameExists = donationCenterRepository
                    .findByName(dto.getName())
                    .filter(center -> !center.getId().equals(id))
                    .isPresent();

                if (nameExists) {
                    ApiErrorResponse error = ApiErrorResponse.builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.CONFLICT.value())
                    .error("Conflict")
                    .message("A donation center with the name '" + dto.getName() + "' already exists.")
                    .path(request.getRequestURI())
                    .build();
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
                }

                DonationCenterEntity updated = mapper.toEntity(dto);
                updated.setId(existing.getId());
                updated.setCreatedAt(existing.getCreatedAt());
                DonationCenterEntity saved = donationCenterRepository.save(updated);
                return ResponseEntity.ok(mapper.toResponseDto(saved));
            })
            .orElseThrow(() -> new ResourceNotFoundException("Donation center with ID " + id + " not found"));
    }


    // Delete a center
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!donationCenterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        donationCenterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Filter by city and/or state
    @GetMapping("/search")
    public ResponseEntity<List<DonationCenterSummaryDto>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state
    ) {
        List<DonationCenterEntity> results;
        if (city != null && state != null) {
            results = donationCenterRepository.findByCityIgnoreCaseAndStateIgnoreCase(city, state);
        } else if (city != null) {
            results = donationCenterRepository.findByCityIgnoreCase(city);
        } else if (state != null) {
            results = donationCenterRepository.findByStateIgnoreCase(state);
        } else {
            results = donationCenterRepository.findAll();
        }

        List<DonationCenterSummaryDto> summaries = results.stream()
                .map(mapper::toSummaryDto)
                .toList();

        return ResponseEntity.ok(summaries);
    }
}
