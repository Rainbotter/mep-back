package lu.mypost.mep.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMepRequest {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Size(min = 2)
    private String project;

    @NotNull
    @Size(min = 2)
    private String version;

    @NotNull
    @Size(min = 2)
    private String templateId;

}
