package zbsmirnova.votingforrestaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity{

    //price in cents
    @Column(name = "price", nullable = false)
    @NotNull
    private int price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("meals") //??????
    private Menu menu;

    public Dish(){}

    public Dish(int price, String name){
        super(null, name);
        this.price = price;

    }
    public Dish(int id, int price, String name){
        super(id, name);
        this.price = price;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{"  +
                " name" + name +
                ", price = " + price + " cents" +
                '}';
    }
}
