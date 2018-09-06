package zbsmirnova.votingforrestaurants.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"address", "name"}, name = "restaurants_unique_idx")})
public class Restaurant extends AbstractNamedEntity{

    @NotBlank
    @Column(name = "address", nullable = false)
    @Size(min = 5, max = 255)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")
    protected List<Vote> votes;

    public Restaurant(){}

    public Restaurant(String name, String address){
        super(null, name);
        this.address = address;
    }

    public Restaurant(int id, String name, String address){
        super(id, name);
        this.address = address;
    }

    public List<Dish> getDishes() { return dishes; }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{"  +
                "id=" + getId() +
                ", name: " + name +
                ", address: " + address +
                '}';
    }

}
