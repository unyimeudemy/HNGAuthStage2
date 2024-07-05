package com.HNG.userAuthStage2.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.*;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class UserEntity implements UserDetails{

    private Set<String> permissions;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userId", updatable = false, nullable = false)
    private String userId;

    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    @Column(name = "firstName")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    @Column(name = "lastName")
    private String lastName;

    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
