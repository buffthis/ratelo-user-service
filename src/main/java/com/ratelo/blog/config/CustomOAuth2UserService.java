package com.ratelo.blog.config;

import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.domain.user.UserType;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. Google에서 사용자 정보 가져오기
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 2. Provider 정보 추출
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "google"

        // 3. Google 사용자 정보 추출
        String providerId = oauth2User.getAttribute("sub");  // Google의 고유 user ID
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");

        // 4. DB에서 사용자 찾기 또는 생성
        User user = userRepository.findByProviderAndProviderId(registrationId, providerId)
            .orElseGet(() -> createNewUser(registrationId, providerId, email, name, picture));

        // 5. OAuth2 attributes 설정 (세션용, DB에는 저장 안 됨)
        user.setAttributes(oauth2User.getAttributes());

        // 6. Spring Security가 사용할 User 객체 반환
        // OAuth2User 인터페이스와 UserDetails 인터페이스를 모두 구현한 User를 반환
        return user;
    }

    private User createNewUser(String provider, String providerId, String email, String name, String picture) {
        // 새 사용자 생성
        User newUser = User.builder()
            .username(generateUniqueUsername(email))  // 이메일 기반 unique username 생성
            .name(name)
            .provider(provider)
            .providerId(providerId)
            .password(null)  // OAuth 사용자는 비밀번호 없음
            .userType(UserType.MEMBER)
            .disabled(false)
            .masked(false)
            .build();

        return userRepository.save(newUser);
    }

    private String generateUniqueUsername(String email) {
        // 이메일에서 @ 앞부분 추출
        String baseUsername = email.split("@")[0];

        // 이미 존재하는 username이면 숫자 추가
        String username = baseUsername;
        int counter = 1;

        while (userRepository.findUserByUsername(username).isPresent()) {
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }
}
