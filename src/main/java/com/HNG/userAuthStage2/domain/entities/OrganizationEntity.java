package com.HNG.userAuthStage2.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
public class OrganizationEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "orgId", updatable = false, nullable = false)
    private String orgId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "organizations")
    private Set<UserEntity> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return Objects.equals(orgId, that.orgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId);
    }

    @Override
    public String toString() {
        return "OrganizationEntity{" +
                "orgId='" + orgId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}