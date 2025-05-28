package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByCompanyId(Long companyId) {
        return careerRepository.findUsersByCompanyId(companyId);
    }
}
