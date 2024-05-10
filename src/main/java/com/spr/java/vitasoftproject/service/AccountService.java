package com.spr.java.vitasoftproject.service;

import com.spr.java.vitasoftproject.dto.AccountDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    AccountDto createUser(AccountDto accountDto);
    List<AccountDto> getAllAccounts();
    AccountDto assignOperatorRightOfUser(Long accountId);
}
