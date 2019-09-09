package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.mep.Mep;
import lu.mypost.mep.model.response.MepResponse;
import lu.mypost.mep.model.response.TruncatedMepResponse;

import java.util.stream.Collectors;

public class MepMapper {

    public static MepResponse createResponse(Mep mep) {

        return MepResponse.builder()
                .id(mep.getId())
                .name(mep.getName())
                .project(mep.getProject())
                .version(mep.getVersion())
                .apis(mep.getApis().stream().map(ApiMapper::createResponse).collect(Collectors.toList()))
                .creationDate(mep.getCreationDate())
                .lastModificationDate(mep.getLastModificationDate())
                .closureDate(mep.getClosureDate())
                .dueDate(mep.getDueDate())
                .templateId(mep.getTemplateId())
                .build();
    }

    public static TruncatedMepResponse createTruncatedResponse(Mep mep) {

        return TruncatedMepResponse.builder()
                .id(mep.getId())
                .name(mep.getName())
                .project(mep.getProject())
                .version(mep.getVersion())
                .creationDate(mep.getCreationDate())
                .lastModificationDate(mep.getLastModificationDate())
                .closureDate(mep.getClosureDate())
                .templateId(mep.getTemplateId())
                .build();
    }
}
