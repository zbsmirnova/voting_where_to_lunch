package zbsmirnova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"}, name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity{

    //price in cents
    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(){}

    public Dish(int price, String name, LocalDate date){
        super(null, name);
        this.price = price;
        this.date = date;
    }

    public Dish(int price, String name, Restaurant restaurant, LocalDate date){
        this(price, name, date);
        this.restaurant = restaurant;
    }

    public Dish(int id, int price, String name,  LocalDate date){
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Dish(int id, int price, String name, Restaurant restaurant, LocalDate date){
       this(id, price, name, date);
        this.restaurant = restaurant;
    }

    public Dish(Dish dish){
        this(dish.getId(), dish.getPrice(), dish.getName(), dish.getRestaurant(), dish.getDate());
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{"  +
                " name " + name +
                ", price = " + price + " cents, " +
                "date = " + date +
                '}';
    }
}
