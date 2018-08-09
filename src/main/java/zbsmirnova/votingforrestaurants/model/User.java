package zbsmirnova.votingforrestaurants.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity{


    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("voteDate DESC")
//    @JsonIgnore
    protected List<Vote> votes;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){}



    public User(Integer id, String name, String email, String password, Role role) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String name, String email, String password, Role role) {
        super(null, name);
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", email=" + email +
                ", name=" + name +
                '}';
    }

}
