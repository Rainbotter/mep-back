package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.mep.Api;
import lu.mypost.mep.model.document.mep.DockerApi;
import lu.mypost.mep.model.document.mep.RancherApi;
import lu.mypost.mep.model.response.ApiResponse;
import lu.mypost.mep.model.response.DockerApiResponse;
import lu.mypost.mep.model.response.RancherApiResponse;

import java.util.stream.Collectors;

public class ApiMapper {

    public static ApiResponse createResponse(Api api) {
        switch (api.getType()) {
            case RANCHER:
                return createRancherApiResponse((RancherApi) api);
            case DOCKER:
                return createDockerApiResponse((DockerApi) api);
            default:
                throw new UnsupportedOperationException("Api type " + api.getType() + "is currently not supported");
        }
    }

    public static DockerApiResponse createDockerApiResponse(DockerApi api) {
        DockerApiResponse result = DockerApiResponse.builder()
                .appPort(api.getAppPort())
                .dockerNode(api.getDockerNode())
                .dockerRunArgs(api.getDockerRunArgs())
                .dockerToolboxVersion(api.getDockerToolboxVersion())
                .build();

        setCommonValues(result, api);

        return result;
    }

    public static RancherApiResponse createRancherApiResponse(RancherApi api) {
        RancherApiResponse result = RancherApiResponse.builder()
                .branchName(api.getBranchName())
                .cluster(api.getCluster())
                .namespace(api.getNamespace())
                .rancherEnv(api.getRancherEnv())
                .rancherProject(api.getRancherProject())
                .rancherTemplate(api.getRancherTemplate())
                .tfsTeam(api.getTfsTeam())
                .build();

        setCommonValues(result, api);

        return result;
    }

    private static void setCommonValues(ApiResponse apiResponse, Api api) {
        apiResponse.setId(api.getId());
        apiResponse.setMaintainer(api.getMaintainer());
        apiResponse.setName(api.getName());
        apiResponse.setNewVersion(api.getNewVersion());
        apiResponse.setOldVersion(api.getOldVersion());
        apiResponse.setType(api.getType());
        apiResponse.setComment(api.getComment());
        apiResponse.setExposition(api.getExposition());
        apiResponse.setDockerImage(api.getDockerImage());
        apiResponse.setDbUpdate(api.getDbUpdate());
        apiResponse.setChange(api.getChange());
        apiResponse.setStepsets(api.getStepsets().stream().map(StepsetMapper::createResponse).collect(Collectors.toList()));
    }

}
