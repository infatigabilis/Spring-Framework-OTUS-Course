package ru.otus.springframework.hw21.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.springframework.hw21.security.SecurityConfig;
import ru.otus.springframework.hw21.security.UserRole;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1)
    private Long id;

    @Column(nullable = false)
    @NotBlank @Size(max = 255)
    private String nickname;

    @Size(max = 255)
    private String password;

    private UserRole role;

    public User(@NotBlank @Size(max = 255) String nickname, @Size(max = 255) String password, UserRole role) {
        this.nickname = nickname;
        this.password = SecurityConfig.passwordEncoder.encode(password);
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = SecurityConfig.passwordEncoder.encode(password);
    }
}
