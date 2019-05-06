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
public class MepTemplateResponse {

    private String id;
    private String name;
    private List<StepsetResponse> stepsets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MepTemplateResponse that = (MepTemplateResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(stepsets, that.stepsets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stepsets);
    }

}
