package com.ratelo.blog.domain.career;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CareerRepositoryImpl implements CareerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CareerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Career> findAllWithHiddenOption(Boolean includeHidden) {
        QCareer career = QCareer.career;
        BooleanBuilder builder = new BooleanBuilder();
        if (includeHidden == null || !includeHidden) {
            builder.and(career.isHidden.eq(false));
        }
        return queryFactory.selectFrom(career)
                .where(builder)
                .fetch();
    }
} 