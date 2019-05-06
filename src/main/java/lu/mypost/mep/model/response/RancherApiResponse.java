package lu.mypost.mep.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RancherApiResponse extends ApiResponse {

    private String namespace;
    private String branchName;
    private String tfsTeam;
    private String cluster;
    private String rancherProject;
    private String rancherEnv;
    private String rancherTemplate;

}
