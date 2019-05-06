package lu.mypost.mep.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStepsetTemplateRequest {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Min(1)
    private int order;

}
