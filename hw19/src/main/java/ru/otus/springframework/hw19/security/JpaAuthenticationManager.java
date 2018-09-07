package ru.otus.springframework.hw19.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw19.domain.User;
import ru.otus.springframework.hw19.repository.UserRepository;

import java.util.Collections;

@Component
public class JpaAuthenticationManager implements AuthenticationProvider {
    private final UserRepository userRepository;

    public JpaAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return userRepository.findByNickname(authentication.getPrincipal().toString())
                .map(user -> {
                    if (isPasswordCorrect(authentication.getCredentials().toString(), user)) {
                        return new UsernamePasswordAuthenticationToken(
                                user.getNickname(),
                                user.getPassword(),
                                Collections.singletonList((GrantedAuthority) () -> user.getRole().toString())
                        );
                    } else {
                        return null;
                    }
                })
                .orElse(null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


    private boolean isPasswordCorrect(String testPassword, User user) {
        return SecurityConfig.passwordEncoder.matches(testPassword, user.getPassword());
    }
}
