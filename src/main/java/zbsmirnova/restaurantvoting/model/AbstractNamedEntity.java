package zbsmirnova.restaurantvoting.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), id, name);
    }
}