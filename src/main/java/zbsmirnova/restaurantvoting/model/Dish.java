package zbsmirnova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"}, name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity {

    //price in cents
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(int price, String name, LocalDate date) {
        super(null, name);
        this.price = price;
        this.date = date;
    }

    public Dish(int price, String name, Restaurant restaurant, LocalDate date) {
        this(price, name, date);
        this.restaurant = restaurant;
    }

    public Dish(int id, int price, String name, @NotNull LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Dish(int id, int price, String name, Restaurant restaurant, LocalDate date) {
        this(id, price, name, date);
        this.restaurant = restaurant;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getPrice(), dish.getName(), dish.getRestaurant(), dish.getDate());
    }

    @Override
    public String toString() {
        return "Dish{" +
                " name " + name +
                ", price = " + price + " cents, " +
                "date = " + date +
                '}';
    }
}
