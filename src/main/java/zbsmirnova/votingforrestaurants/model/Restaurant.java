package zbsmirnova.votingforrestaurants.model;


import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name")})
public class Restaurant extends AbstractNamedEntity{


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")

    protected List<Vote> votes;

    public Restaurant(){}

    public Restaurant(String name){
        super(null, name);
    }

    public Restaurant(int id, String name){
        super(id, name);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{"  +
                "id=" + id +
                ", name: " + this.getName() +
                '}';
    }

}
