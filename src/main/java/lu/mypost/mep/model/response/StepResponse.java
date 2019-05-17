package lu.mypost.mep.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.mypost.mep.model.enums.Status;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StepResponse {

    private String id;
    private String name;
    private Status status;
    private int order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepResponse that = (StepResponse) o;
        return order == that.order &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, order);
    }

}
