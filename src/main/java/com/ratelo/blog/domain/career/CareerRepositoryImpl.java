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
    public List<Career> findAllByCondition(Boolean includeHidden, Long userId, String username) {
        QCareer career = QCareer.career;
        BooleanBuilder builder = new BooleanBuilder();
        if (includeHidden == null || !includeHidden) {
            builder.and(career.hidden.eq(false));
        }
        if (userId != null) {
            builder.and(career.user.id.eq(userId));
        }
        if (username != null) {
            builder.and(career.user.username.eq(username));
        }
        return queryFactory.selectFrom(career)
                .where(builder)
                .orderBy(career.startDate.desc())
                .fetch();
    }
} 