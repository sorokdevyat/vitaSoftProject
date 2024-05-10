package com.spr.java.vitasoftproject.service.jpa;

import com.spr.java.vitasoftproject.common.enums.AccountRole;
import com.spr.java.vitasoftproject.common.enums.ApplicationStatus;
import com.spr.java.vitasoftproject.common.exceptions.NoAccesToMethodException;
import com.spr.java.vitasoftproject.dto.AccountDto;
import com.spr.java.vitasoftproject.dto.ApplicationDto;
import com.spr.java.vitasoftproject.dto.filter.ApplicationFilter;
import com.spr.java.vitasoftproject.dto.paging.PageRequestDto;
import com.spr.java.vitasoftproject.dto.paging.PageResponseDto;
import com.spr.java.vitasoftproject.mapping.AccountMapper;
import com.spr.java.vitasoftproject.mapping.ApplicationMapper;
import com.spr.java.vitasoftproject.model.Application;
import com.spr.java.vitasoftproject.repositories.AccountRepository;
import com.spr.java.vitasoftproject.repositories.ApplicationRepository;
import com.spr.java.vitasoftproject.service.ApplicationService;
import com.spr.java.vitasoftproject.specification.ApplicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ApplicationServiceJpa implements ApplicationService {
    private final AccountRepository accountRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final AccountMapper accountMapper;

    @Override
    public ApplicationDto createApplication(ApplicationDto applicationDto) {
        AccountDto accountDto = getCurrentAccount();
        if (!accountDto.getAccountRole().contains(AccountRole.USER)){
            throw new NoAccesToMethodException("To create a application you need USER role");
        }

        applicationDto.setStatus(ApplicationStatus.DRAFT);
        applicationDto.setDate(OffsetDateTime.now());

        Application application = applicationMapper.fromDtoToApplication(applicationDto);

        application.setAccount(accountRepository.findById(accountDto.getId())
                .orElseThrow(()-> new NoSuchElementException(String.format("Account with id = %d not found", accountDto.getId()))));
        Application save = applicationRepository.save(application);
        return applicationMapper.fromApplicationToDto(save);
    }
    private static String messageToSend(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            builder.append(c);
            if (c != ' ') {
                builder.append('-');
            }
        }
        return builder.toString();
    }

    @Override
    public ApplicationDto sendApplication(Long applicationId){
        AccountDto accountDto = getCurrentAccount();
        if (accountDto.getAccountRole().contains(AccountRole.OPERATOR) & !accountDto.getAccountRole().contains(AccountRole.USER)){
            throw new RuntimeException(String.format("Operator with id = %d can't send application",accountDto.getId()));
        }
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Application with id : %d not found", applicationId)));
        if (!(application.getAccount().getId()).equals(accountDto.getId())){
            throw new RuntimeException(String.format("Account with id = %d can't send application of another user",accountDto.getId()));
        }
        application.setMessage(messageToSend(application.getMessage()));
        application.setStatus(ApplicationStatus.SENT);
        applicationRepository.save(application);
        return applicationMapper.fromApplicationToDto(application);
    }

    @Override
    public PageResponseDto<Application> getAllApplicationUser(PageRequestDto pageRequestDto) {
        AccountDto accountDto = getCurrentAccount();

        pageRequestDto.getData().setUsername(null); // Т.к юзер не может искать по нейму

        List<Application> pageOfApplication = applicationRepository.findAll(ApplicationSpecification.getSpec((ApplicationFilter) pageRequestDto.getData()),
                        PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize())).getContent()
                .stream().filter(a -> a.getAccount().getId().equals(accountDto.getId())).toList();

        return PageResponseDto.<Application>builder()
                .page(pageRequestDto.getPage())
                .total(pageOfApplication.size())
                .responsePage(pageOfApplication)
                .build();
    }

    @Override
    public PageResponseDto<Application> getAllApplicationOperator(PageRequestDto pageRequestDto) {
        AccountDto accountDto = getCurrentAccount();
        if (!accountDto.getAccountRole().contains(AccountRole.OPERATOR)){
            throw new NoAccesToMethodException(String.format("Account with username : %s haven't operator rights",accountDto.getUsername()));
        }
        List<Application> pageOfApplication = applicationRepository
                .findAll(ApplicationSpecification.getSpec(pageRequestDto.getData()), PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize())).getContent()
                .stream().filter(a -> a.getStatus().equals(ApplicationStatus.SENT)).toList();
        return PageResponseDto.<Application>builder()
                .page(pageRequestDto.getPage())
                .total(pageOfApplication.size())
                .responsePage(pageOfApplication)
                .build();
    }
    @Override
    public ApplicationDto acceptApplication(Long applicationId) {
        AccountDto accountDto = getCurrentAccount();
        if (!accountDto.getAccountRole().contains(AccountRole.OPERATOR)){
            throw new NoAccesToMethodException(String.format("Account with username : %s haven't operator rights",accountDto.getUsername()));
        }
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Application with id : %d not found", applicationId)));
        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationMapper.fromApplicationToDto(application);
    }

    @Override
    public ApplicationDto rejectApplication(Long applicationId) {
        AccountDto accountDto = getCurrentAccount();

        if (!accountDto.getAccountRole().contains(AccountRole.OPERATOR)){
            throw new NoAccesToMethodException(String.format("Account with username : %s haven't operator rights",accountDto.getUsername()));
        }
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Application with id : %d not found", applicationId)));
        application.setStatus(ApplicationStatus.REJECTED);
        return applicationMapper.fromApplicationToDto(application);
    }
    private AccountDto getCurrentAccount(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return accountMapper.fromAccountToDto(accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account %s not found", username))));
    }
}
