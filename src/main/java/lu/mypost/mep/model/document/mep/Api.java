package lu.mypost.mep.model.document.mep;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Api {

    protected String id;
    protected String name;
    protected List<Stepset> Stepsets;
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
