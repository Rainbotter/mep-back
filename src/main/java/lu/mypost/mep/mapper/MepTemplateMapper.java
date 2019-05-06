package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.model.response.MepTemplateResponse;

import java.util.stream.Collectors;

public class MepTemplateMapper {

    public static MepTemplateResponse createResponse(MepTemplate template) {
        return MepTemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .stepsets(template.getStepsets().stream().map(StepsetMapper::createResponse).collect(Collectors.toList()))
                .build();
    }

}
