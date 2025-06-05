package com.ratelo.blog.domain.user;

import org.springframework.data.domain.Slice;

public interface UserRepositoryCustom {
    /**
     * cursor based pagination with filter
     * @param lastId last id of the user (null for the first page)
     * @param pageSize number of users to fetch per page
     * @param nameFilter name filter (optional)
     * @param userTypeFilter user type filter (optional)
     * @return Slice<User>
     */
    Slice<User> findAllByCursor(Long lastId, int pageSize, String nameFilter, UserType userTypeFilter);
} 