package com.spr.java.vitasoftproject.mapping;

import com.spr.java.vitasoftproject.dto.ApplicationDto;
import com.spr.java.vitasoftproject.model.Application;
import com.spr.java.vitasoftproject.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ApplicationMapper {
    public Application fromDtoToApplication(ApplicationDto applicationDto){
        Application application = Application.builder()
                .id(applicationDto.getId())
                .date(applicationDto.getDate())
                .message(applicationDto.getMessage())
                .status(applicationDto.getStatus())
                .build();
        if (!ObjectUtils.isEmpty(applicationDto.getAccountId())){
            application.setAccount(Account.builder().id(applicationDto.getId()).build());
        }
        return application;
    }
    public ApplicationDto fromApplicationToDto(Application application){
        ApplicationDto applicationDto = ApplicationDto.builder()
                .id(application.getId())
                .date(application.getDate())
                .message(application.getMessage())
                .status(application.getStatus())
                .build();
        if (!ObjectUtils.isEmpty(application.getAccount())){
            applicationDto.setAccountId(application.getAccount().getId());
        }
        return applicationDto;
    }
}
