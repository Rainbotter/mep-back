package lu.mypost.mep.model.document.mep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stepset {

    private String id;
    private String name;
    private List<Step> steps;
    private int order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stepset stepset = (Stepset) o;
        return order == stepset.order &&
                Objects.equals(id, stepset.id) &&
                Objects.equals(name, stepset.name) &&
                Objects.equals(steps, stepset.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, steps, order);
    }

}
