package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "name", nullable = false, length = 30)
    private String firstName;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email")
    @Size(min = 2, message = "Enter at least 2 characters")
    private String email;

    @Column
    @Size(min = 2, message = "Enter at least 2 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
//    @ManyToMany
/*    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )*/
    private List<Role> roles;

    public User() {}

    public User(Long id, String firstName, String lastName, String email, String password, List<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getRolesInStrings() {
        StringBuilder sb = new StringBuilder();
        for (Role role : roles) {
            sb.append(role.getName() + " ");
        }

        return sb.toString();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User that = (User) o;
//        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
//                && Objects.equals(email, that.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(firstName, lastName, email);
//    }
}
