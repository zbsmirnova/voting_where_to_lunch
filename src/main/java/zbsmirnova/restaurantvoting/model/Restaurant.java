package zbsmirnova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"address", "name"}, name = "restaurants_unique_idx")})
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Column(name = "address", nullable = false)
    @Size(min = 5, max = 255)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnore
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("voteDate DESC")
    @JsonBackReference(value = "rest to votes list")
    protected List<Vote> votes;

    public Restaurant(String name, String address) {
        super(null, name);
        this.address = address;
    }

    public Restaurant(int id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.address);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + getId() +
                ", name: " + name +
                ", address: " + address +
                '}';
    }

}
