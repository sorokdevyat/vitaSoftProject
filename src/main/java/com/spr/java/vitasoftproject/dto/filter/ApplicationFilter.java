package com.spr.java.vitasoftproject.dto.filter;

import com.spr.java.vitasoftproject.common.enums.SortingEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFilter {
    private String username;
    private SortingEnum dateSort; //ASC or DESC
}
