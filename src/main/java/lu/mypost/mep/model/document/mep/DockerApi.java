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
public class DockerApi extends Api {

    private String appPort;
    private String dockerNode;
    private String dockerRunArgs;
    private String dockerToolboxVersion;

}
