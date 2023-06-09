package com.edatwhite.smkd.entity;

import com.edatwhite.smkd.entity.smkdocument.RelationalDocument;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String fio;

    @Column(name = "login")
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "work",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "division_id"))
    private Set<Division> divisions = new HashSet<>();

    // "favorites"
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    private Set<RelationalDocument> favorites = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites_template",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "template_id"))
    private Set<Templates> favoritesTemplate = new HashSet<>();

    public Users() {
    }

    public Users(String fio, String username, String password) {
        this.fio = fio;
        this.username = username;
        this.password = password;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(Set<Division> divisions) {
        this.divisions = divisions;
    }

    public Set<RelationalDocument> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<RelationalDocument> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(RelationalDocument favorite) {
        this.favorites.add(favorite);
    }

    public void deleteFavorite(RelationalDocument favorite) {
        this.favorites.remove(favorite);
    }

    public Set<Templates> getFavoritesTemplate() {
        return favoritesTemplate;
    }

    public void setFavoritesTemplate(Set<Templates> favoritesTemplate) {
        this.favoritesTemplate = favoritesTemplate;
    }

    public void addFavoriteTemplate(Templates templates) {
        this.favoritesTemplate.add(templates);
    }

    public void deleteFavoriteTemplate(Templates templates) {
        this.favoritesTemplate.remove(templates);
    }
}
