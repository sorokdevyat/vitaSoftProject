package com.spr.java.vitasoftproject.mapping;

import com.spr.java.vitasoftproject.dto.AccountDto;
import com.spr.java.vitasoftproject.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto fromAccountToDto(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .accountRole(account.getAccountRole())
                .build();
    }
    public Account fromDtoToAccount(AccountDto accountDto){
        return Account.builder()
                .id(accountDto.getId())
                .username(accountDto.getUsername())
                .password(accountDto.getPassword())
                .accountRole(accountDto.getAccountRole())
                .build();
    }
}
