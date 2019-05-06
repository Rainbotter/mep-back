package lu.mypost.mep.model.response;

import lombok.Getter;
import lombok.Setter;
import lu.mypost.mep.model.document.mep.Type;

import java.util.List;

@Getter
@Setter
public abstract class ApiResponse {

    protected String id;
    protected String name;
    protected List<StepsetResponse> Stepsets;
    protected String maintainer;
    protected String oldVersion;
    protected String newVersion;
    protected String exposition;
    protected String dockerImage;
    protected String dbUpdate;
    protected String comment;
    protected String change;
    protected Type type;


}
