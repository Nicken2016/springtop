package net.nicken.model;

import net.nicken.util.MealsUtil;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
})

@Entity
@NamedEntityGraph(name = User.GRAPH_WITH_MEALS, attributeNodes = {@NamedAttributeNode("meals")})
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends NamedEntity{

    public static final String GRAPH_WITH_MEALS = "User.withMeals";

    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";
    public static final String BY_EMAIL = "User.getByEmail";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 5)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boot default true")
    private boolean enabled = true;

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    private Date registered = new Date();

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @Column(name = "calories_per_day", columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay = MealsUtil.DEFAULT_CALORIES_PER_DAY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("dateTime DESC")
    protected List<Meal> meals;

    public User() {
    }

    public User(User u){
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCaloriesPerDay(), u.isEnabled(), u.getRoles());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles){
        this(id, name, email, password, MealsUtil.DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public List<Meal> getMeals(){
        return meals;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}
