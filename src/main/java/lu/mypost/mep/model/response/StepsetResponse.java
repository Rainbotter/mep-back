package lu.mypost.mep.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StepsetResponse {

    private String id;
    private String name;
    private List<StepResponse> steps;
    private int order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepsetResponse that = (StepsetResponse) o;
        return order == that.order &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, steps, order);
    }

}
