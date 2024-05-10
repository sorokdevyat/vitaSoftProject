package com.spr.java.vitasoftproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spr.java.vitasoftproject.common.enums.ApplicationStatus;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String message;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long accountId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ApplicationStatus status;
}
