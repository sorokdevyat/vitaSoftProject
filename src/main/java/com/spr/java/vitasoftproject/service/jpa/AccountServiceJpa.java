package com.spr.java.vitasoftproject.service.jpa;

import com.spr.java.vitasoftproject.common.enums.AccountRole;
import com.spr.java.vitasoftproject.dto.AccountDto;
import com.spr.java.vitasoftproject.mapping.AccountMapper;
import com.spr.java.vitasoftproject.model.Account;
import com.spr.java.vitasoftproject.repositories.AccountRepository;
import com.spr.java.vitasoftproject.service.AccountService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountServiceJpa implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createUser(AccountDto accountDto) {
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account save = accountRepository.save(accountMapper.fromDtoToAccount(accountDto));
        return accountMapper.fromAccountToDto(save);
    }
    private void checkAdminRights() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account %s not found", username)));
        if (!account.getAccountRole().contains(AccountRole.ADMIN)){
            throw new NoSuchElementException(String.format("User with the username %s is denied access",username));
        }
    }
    @Override
    public List<AccountDto> getAllAccounts() {
        checkAdminRights();
        return accountRepository.findAll().stream().map(accountMapper::fromAccountToDto).toList();
    }
    @Override
    public AccountDto assignOperatorRightOfUser(Long accountId) {
        checkAdminRights();
        Account accountToUpdate = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format("Account %d not found", accountId)));
        if (accountToUpdate.getAccountRole().contains(AccountRole.OPERATOR)){
            throw new RuntimeException(String.format("Account with username : %s already have operator rights",accountToUpdate.getUsername()));
        }
        accountToUpdate.getAccountRole().add(AccountRole.OPERATOR);
        return accountMapper.fromAccountToDto(accountRepository.save(accountToUpdate));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format("Account %s not fount",username)));
        return new User(username,account.getPassword(), Collections.emptyList());
    }

    @PostConstruct
    public void initAdmin() {
        Set<AccountRole> set = new HashSet<>();
        set.add(AccountRole.ADMIN);
        set.add(AccountRole.OPERATOR);
        set.add(AccountRole.USER);

        accountRepository.findByUsername("root")
                .orElseGet(() -> accountRepository.save(Account.builder()
                        .username("root")
                        .password(passwordEncoder.encode("root"))
                        .accountRole(set)
                        .build()));
    }
}
