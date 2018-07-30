package zbsmirnova.votingforrestaurants.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @JsonIgnoreProperties("menu")//???
    private Set<Dish> dishes;

    @Column(name = "menu_date", columnDefinition = "date default current_date",  nullable = false)
    @NotNull
    private Date menu_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Menu(){}

    public Menu(Restaurant restaurant, Date menuDate) {
        super(null);
        this.restaurant = restaurant;
        this.menu_date = menuDate;
    }

    public Menu(int id, Restaurant restaurant, Date menuDate) {
        super(id);
        this.restaurant = restaurant;
        this.menu_date = menuDate;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Date getMenuDate() {
        return menu_date;
    }

    public void setMenuDate(Date menuDate) {
        this.menu_date = menuDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
