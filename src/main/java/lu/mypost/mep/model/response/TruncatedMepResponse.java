package lu.mypost.mep.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static lu.mypost.mep.configuration.Constants.dateSerializationFormat;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TruncatedMepResponse {

    private String id;
    private String name;
    private String project;
    private String templateId;
    private String version;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateSerializationFormat)
    private Date creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateSerializationFormat)
    private Date lastModificationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateSerializationFormat)
    private Date closureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateSerializationFormat)
    private Date dueDate;

}
