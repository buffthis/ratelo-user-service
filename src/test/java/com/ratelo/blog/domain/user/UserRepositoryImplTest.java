package com.ratelo.blog.domain.user;

import com.ratelo.blog.config.QuerydslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class UserRepositoryImplTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("cursor based user search: basic and filter")
    void findAllByCursor_basic_and_filter() {
        // given
        User user1 = User.builder().username("alice").name("Alice").userType(UserType.MEMBER).build();
        User user2 = User.builder().username("bob").name("Bob").userType(UserType.ADMIN).build();
        User user3 = User.builder().username("carol").name("Carol").userType(UserType.MEMBER).build();
        userRepository.saveAll(List.of(user1, user2, user3));

        // when: first page (lastId is null)
        Slice<User> slice1 = userRepository.findAllByCursor(null, 2, null, null);
        // then
        assertThat(slice1.getContent()).hasSize(2);
        assertThat(slice1.hasNext()).isTrue();
        Long lastId = slice1.getContent().get(1).getId();

        // when: second page (lastId is used)
        Slice<User> slice2 = userRepository.findAllByCursor(lastId, 2, null, null);
        assertThat(slice2.getContent()).hasSize(1);
        assertThat(slice2.hasNext()).isFalse();

        // when: name filter
        Slice<User> filtered = userRepository.findAllByCursor(null, 10, "Bob", null);
        assertThat(filtered.getContent()).hasSize(1);
        assertThat(filtered.getContent().get(0).getUsername()).isEqualTo("bob");

        // when: type filter
        Slice<User> typeFiltered = userRepository.findAllByCursor(null, 10, null, UserType.ADMIN);
        assertThat(typeFiltered.getContent()).hasSize(1);
        assertThat(typeFiltered.getContent().get(0).getUsername()).isEqualTo("bob");
    }
}