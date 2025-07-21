package com.fightingkorea.platform.domain.trainer.controller;

import com.fightingkorea.platform.domain.trainer.dto.SpecialtyResponse;
import com.fightingkorea.platform.domain.trainer.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @PostMapping
    public SpecialtyResponse createSpecialty(String specialtyName){
        return specialtyService.createSpecialty(specialtyName);
    }

    @GetMapping
    public List<SpecialtyResponse> getAllSpecialty(){
        return specialtyService.getAllSpecialty();
    }

    @DeleteMapping
    public void deleteSpecialty(Long specialtyId){
        specialtyService.deleteSpecialty(specialtyId);
    }

}