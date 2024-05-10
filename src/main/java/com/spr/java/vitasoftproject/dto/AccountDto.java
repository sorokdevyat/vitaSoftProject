package com.spr.java.vitasoftproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spr.java.vitasoftproject.common.enums.AccountRole;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String username;
    private String password;
    private Set<AccountRole> accountRole;
}
