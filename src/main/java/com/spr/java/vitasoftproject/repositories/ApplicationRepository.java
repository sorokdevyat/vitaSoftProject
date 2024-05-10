package com.spr.java.vitasoftproject.repositories;

import com.spr.java.vitasoftproject.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long>, JpaSpecificationExecutor<Application> {
    Optional<List<Application>> findAllByAccount_Id(Long accountId);
    @Query("SELECT '*' FROM Application order by date ASC ")
    List<Application> findAllAsc();
    @Query("SELECT '*' FROM Application order by date desc ")
    List<Application> findAllDesc();
    List<Application> findAllByAccount_Username(String username);
}
