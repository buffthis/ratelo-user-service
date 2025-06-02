package com.ratelo.blog.domain.career;

import com.ratelo.blog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long>, CareerRepositoryCustom {

    @Query("SELECT c.user FROM Career c WHERE c.company.id = :companyId")
    List<User> findUsersByCompanyId(@Param("companyId") Long companyId);
}
