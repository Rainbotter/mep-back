package lu.mypost.mep.model.document.mep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RancherApi extends Api {

    private String namespace;
    private String branchName;
    private String tfsTeam;
    private String cluster;
    private String rancherProject;
    private String rancherEnv;
    private String rancherTemplate;

}
