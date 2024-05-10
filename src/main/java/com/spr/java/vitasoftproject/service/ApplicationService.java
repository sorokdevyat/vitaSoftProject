package com.spr.java.vitasoftproject.service;

import com.spr.java.vitasoftproject.dto.ApplicationDto;
import com.spr.java.vitasoftproject.dto.filter.ApplicationFilter;
import com.spr.java.vitasoftproject.dto.paging.PageRequestDto;
import com.spr.java.vitasoftproject.dto.paging.PageResponseDto;
import com.spr.java.vitasoftproject.model.Application;

import java.util.List;

public interface ApplicationService {
    ApplicationDto createApplication(ApplicationDto applicationDto);
    ApplicationDto sendApplication(Long applicationId);
    PageResponseDto<Application> getAllApplicationUser(PageRequestDto pageRequestDto);
    PageResponseDto<Application> getAllApplicationOperator(PageRequestDto pageRequestDto);
    ApplicationDto acceptApplication(Long applicationId);
    ApplicationDto rejectApplication(Long id);

}
