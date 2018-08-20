package zbsmirnova.votingforrestaurants.to;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class MenuTo extends BaseTo implements Serializable {
    @NotNull
    private LocalDate date;

    public MenuTo(){}

    public MenuTo(int id,  LocalDate date) {
        super(id);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
