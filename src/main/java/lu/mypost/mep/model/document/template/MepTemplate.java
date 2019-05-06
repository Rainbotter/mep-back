package lu.mypost.mep.model.document.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.mypost.mep.model.document.mep.Stepset;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "templates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MepTemplate {

    @Id
    private String id;
    private String name;
    private List<Stepset> stepsets = new ArrayList<>();

}
