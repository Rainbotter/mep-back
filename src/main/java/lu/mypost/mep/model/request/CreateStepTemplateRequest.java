package lu.mypost.mep.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.mypost.mep.model.document.mep.Status;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStepTemplateRequest {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    private Status status;

    @NotNull
    @Min(0)
    private int order;

}
