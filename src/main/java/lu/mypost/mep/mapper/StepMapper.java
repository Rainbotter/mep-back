package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.mep.Step;
import lu.mypost.mep.model.response.StepResponse;

public class StepMapper {

    public static StepResponse createResponse(Step step) {
        return StepResponse.builder()
                .id(step.getId())
                .name(step.getName())
                .status(step.getStatus())
                .order(step.getOrder())
                .build();
    }
}
