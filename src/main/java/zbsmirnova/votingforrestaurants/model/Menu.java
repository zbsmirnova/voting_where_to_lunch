package zbsmirnova.votingforrestaurants.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @JsonIgnoreProperties("menu")//???
    private Set<Dish> dishes;

    @Column(name = "date", columnDefinition = "date default current_date",  nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Menu(){}

    public Menu(Restaurant restaurant, LocalDate menuDate) {
        super(null);
        this.restaurant = restaurant;
        this.date = menuDate;
    }

    public Menu(int id, Restaurant restaurant, LocalDate menuDate) {
        super(id);
        this.restaurant = restaurant;
        this.date = menuDate;
    }

    public Menu(LocalDate menuDate){
        super(null);
        this.date = menuDate;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
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
        return "Menu{"  +
                "date: " + date +
//                ", restaurant" + restaurant +
                '}';

    }
}
