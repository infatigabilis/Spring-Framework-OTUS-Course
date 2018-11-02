package ru.otus.springframework.hw31.security.role;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.otus.springframework.hw31.domain.User;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;
import ru.otus.springframework.hw31.repository.UserRepository;
import ru.otus.springframework.hw31.security.SecurityConfig;

import java.util.Collections;

@Component
public class JpaAuthenticationManager implements AuthenticationProvider {
    private final UserRepository userRepository;

    public JpaAuthenticationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @HystrixCommand(fallbackMethod = "authenticateFallback")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return userRepository.findByNickname(authentication.getPrincipal().toString())
                .map(user -> {
                    if (isPasswordCorrect(authentication.getCredentials().toString(), user)) {
                        return new UsernamePasswordAuthenticationToken(
                                user.getNickname(),
                                user.getPassword(),
                                Collections.singletonList((GrantedAuthority) () -> "ROLE_" + user.getRole().toString())
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


    private Authentication authenticateFallback(Authentication authentication, Throwable cause) {
        throw new ServiceUnavailableException("Authentication DB", cause);
    }
}
