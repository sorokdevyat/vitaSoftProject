package com.spr.java.vitasoftproject.dto.paging;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResponseDto<T> {
    private int page;
    private int total;
    private int size; // размер стр
    private List<T> responsePage;
}
