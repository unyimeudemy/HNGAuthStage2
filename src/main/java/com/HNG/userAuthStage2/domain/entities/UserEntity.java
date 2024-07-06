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


import java.util.*;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class UserEntity implements UserDetails{

    private Set<String> permissions =  new HashSet<>();;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_organization",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "orgId")
    )
    private Set<OrganizationEntity> organizations = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return permissions.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());

        if (permissions == null) {
            permissions = new HashSet<>();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

