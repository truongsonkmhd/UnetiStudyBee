package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.security.MyUserDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceDetail {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .getByUsernameAndIsDeletedWithRoles(username, false)
                .map(MyUserDetail::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }
}
