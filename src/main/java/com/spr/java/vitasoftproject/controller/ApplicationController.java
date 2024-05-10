package com.spr.java.vitasoftproject.controller;

import com.spr.java.vitasoftproject.dto.ApplicationDto;
import com.spr.java.vitasoftproject.dto.paging.PageRequestDto;
import com.spr.java.vitasoftproject.dto.paging.PageResponseDto;
import com.spr.java.vitasoftproject.model.Application;
import com.spr.java.vitasoftproject.service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/vitasoft/applications")
@Tag(name = "Application",description = "Application Controller")
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/createApplication")
    public ApplicationDto createApplication(@RequestBody ApplicationDto applicationDto){
        return applicationService.createApplication(applicationDto);
    }
    @PostMapping("/sendApplication")
    public ApplicationDto sendApplication(@RequestParam Long applicationId){
        return applicationService.sendApplication(applicationId);
    }
    @PostMapping("/getAllAplicationUser")
    public PageResponseDto<Application> getAllUs(@RequestBody PageRequestDto pageRequestDto){
        return applicationService.getAllApplicationUser(pageRequestDto);
    }
    @PostMapping("/getAllAplicationOperator")
    public PageResponseDto<Application> getAllOp(@RequestBody PageRequestDto pageRequestDto){
        return applicationService.getAllApplicationOperator(pageRequestDto);
    }
    @PostMapping("/acceptAppl")
    public ApplicationDto acceptApplication(@RequestParam Long applicationId){
        return applicationService.acceptApplication(applicationId);
    }
    @PostMapping("/rejectAppl")
    public ApplicationDto rejectApplication(@RequestParam Long applicationId){
        return applicationService.rejectApplication(applicationId);
    }
}
