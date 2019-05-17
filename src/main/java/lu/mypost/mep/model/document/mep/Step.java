package lu.mypost.mep.model.document.mep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.mypost.mep.model.enums.Status;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Step {

    private String id;
    private String name;
    private Status status;
    private int order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return order == step.order &&
                Objects.equals(id, step.id) &&
                Objects.equals(name, step.name) &&
                status == step.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, order);
    }

}
