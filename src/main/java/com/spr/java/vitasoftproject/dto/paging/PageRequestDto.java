package com.spr.java.vitasoftproject.dto.paging;

import com.spr.java.vitasoftproject.dto.filter.ApplicationFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto<T> {
    private int page;
    private int size;
    private ApplicationFilter data;
}