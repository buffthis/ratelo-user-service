package com.ratelo.blog.domain.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<User> findAllByCursor(Long lastId, int pageSize, String nameFilter, UserType userTypeFilter) {
        QUser user = QUser.user;

        BooleanExpression cursorPredicate = (lastId != null) ? user.id.gt(lastId) : null;
        BooleanExpression namePredicate = (nameFilter != null && !nameFilter.isEmpty()) ? user.name.containsIgnoreCase(nameFilter) : null;
        BooleanExpression typePredicate = (userTypeFilter != null) ? user.userType.eq(userTypeFilter) : null;

        List<User> users = queryFactory.selectFrom(user)
                .where(cursorPredicate, namePredicate, typePredicate)
                .orderBy(user.id.asc())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = users.size() > pageSize;
        if (hasNext) {
            users.remove(pageSize);
        }
        return new SliceImpl<>(users, Pageable.ofSize(pageSize), hasNext);
    }
} 