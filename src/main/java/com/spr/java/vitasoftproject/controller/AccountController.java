package com.spr.java.vitasoftproject.controller;

import com.spr.java.vitasoftproject.dto.AccountDto;
import com.spr.java.vitasoftproject.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vitasoft/accounts")
@Tag(name = "Account",description = "Account Controller")
public class AccountController {

    private final AccountService accountService;
    @PostMapping("/createAccount")
    public AccountDto createAccount(@RequestBody AccountDto accountDto){
        return accountService.createUser(accountDto);
    }
    @GetMapping("/getAllUsers")
    public List<AccountDto> getAllUsers(){
        return accountService.getAllAccounts();
    }
    @PostMapping("/assignOperatorRights")
    public AccountDto assignOperatorRights(@RequestParam Long accountId){
        return accountService.assignOperatorRightOfUser(accountId);
    }

}
