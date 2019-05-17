package lu.mypost.mep.model.document.mep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "meps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mep {

    @Id
    private String id;
    private String name;
    private String project;
    private String version;
    private Date dueDate;
    private Date lastModificationDate;
    private Date creationDate;
    private Date closureDate;
    private List<Api> apis;
    private String templateId;

}
