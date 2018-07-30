package zbsmirnova.votingforrestaurants.model;


import javax.persistence.*;
import java.util.Set;

//@NamedQueries({
//        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant m WHERE m.id=:id AND m.user.id=:userId"),
//        @NamedQuery(name = Restaurant.GET, query = "SELECT m FROM Restaurant m WHERE m.id=:id AND m.user.id=:userId"),
//        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT m FROM Restaurant m WHERE m.user.id=:userId ORDER BY m.dateTime"),
//})

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name")})
public class Restaurant extends AbstractNamedEntity{
//    public static final String DELETE = "Dish.delete";
//    public static final String GET = "Dish.get";
//    public static final String ALL_SORTED = "Dish.getAllSorted";
//    public static final String GET_BETWEEN = "Dish.getBetween";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

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

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
