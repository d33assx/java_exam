package com.exam.dias.service;

import com.exam.dias.dto.AbdullayevDiasUserDto;
import com.exam.dias.entity.AbdullayevDiasUser;
import com.exam.dias.exception.AbdullayevDiasResourceNotFoundException;
import com.exam.dias.repository.AbdullayevDiasUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbdullayevDiasUserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AbdullayevDiasUserService.class);

    private final AbdullayevDiasUserRepository userRepository;

    public AbdullayevDiasUserService(AbdullayevDiasUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<AbdullayevDiasUserDto> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AbdullayevDiasUserDto getUserById(Long id) {
        logger.info("Fetching user by id: {}", id);
        AbdullayevDiasUser user = userRepository.findById(id)
                .orElseThrow(() -> new AbdullayevDiasResourceNotFoundException("User not found"));
        return convertToDto(user);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user: {}", id);
        if (!userRepository.existsById(id)) {
            throw new AbdullayevDiasResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private AbdullayevDiasUserDto convertToDto(AbdullayevDiasUser user) {
        AbdullayevDiasUserDto dto = new AbdullayevDiasUserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
