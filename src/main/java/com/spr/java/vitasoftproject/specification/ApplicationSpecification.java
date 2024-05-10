package com.spr.java.vitasoftproject.specification;

import com.spr.java.vitasoftproject.common.enums.SortingEnum;
import com.spr.java.vitasoftproject.dto.filter.ApplicationFilter;
import com.spr.java.vitasoftproject.model.Account;
import com.spr.java.vitasoftproject.model.Application;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSpecification {
    public static Specification<Application> getSpec(ApplicationFilter applicationFilter){
        return ((root, query, criteriaBuilder) -> {
            Join<Application, Account> accountJoin = root.join("account");
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(applicationFilter.getUsername())){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(accountJoin.get("username")),
                        String.format("%%%s%%", applicationFilter.getUsername().toLowerCase())));
            }
            if (!ObjectUtils.isEmpty(applicationFilter.getDateSort())){
                Path<Object> orderByDate = root.get("date");
                Order order;
                if (applicationFilter.getDateSort() == SortingEnum.ASC){
                    order = criteriaBuilder.asc(orderByDate);
                } else {
                    order = criteriaBuilder.desc(orderByDate);
                }
                query.orderBy(order);
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
