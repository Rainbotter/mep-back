package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.mep.Stepset;
import lu.mypost.mep.model.response.StepsetResponse;

import java.util.stream.Collectors;

public class StepsetMapper {

    public static StepsetResponse createResponse(Stepset stepset) {
        return StepsetResponse.builder()
                .id(stepset.getId())
                .name(stepset.getName())
                .order(stepset.getOrder())
                .steps(stepset.getSteps().stream().map(StepMapper::createResponse).collect(Collectors.toList()))
                .build();
    }
}
