package com.java.hotelmanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * Represents a user role in the system, used for access control and permissions. This entity is
 * mapped to the "Role" table in the database. It implements {@link GrantedAuthority} to integrate
 * with Spring Security for authorization purposes. The role is defined using an enumeration to
 * represent different types of roles like user, admin, cleaner, and restaurant.
 */
@Data
@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {
    /**
     * Enum representing different user roles in the system.
     *
     * <p>This enum defines the various user roles that can be assigned for access control and
     * permissions, including ROLE_USER, ROLE_ADMIN, ROLE_CLEANER, and ROLE_RESTAURANT.
     */
    public enum RoleEnum {
//        @JsonProperty("ROLE_USER")
        ROLE_USER,
//        @JsonProperty("ROLE_ADMIN")
        ROLE_ADMIN,
//        @JsonProperty("ROLE_CLEANER")
        ROLE_CLEANER,
//        @JsonProperty("ROLE_RESTAURANT")
        ROLE_RESTAURANT;

//        @JsonCreator
//        public static RoleEnum fromValue(String value) {
//            return RoleEnum.valueOf(value.toUpperCase());
//        }
//        @JsonValue
//        @Override
//        public String toString() {
//            return name();
//        }

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private RoleEnum name;

    public Role() {}

//    @JsonCreator
    public Role(RoleEnum name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
