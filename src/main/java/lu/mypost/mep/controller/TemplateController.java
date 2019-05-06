package lu.mypost.mep.controller;

import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.mapper.MepTemplateMapper;
import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.model.request.CreateMepTemplateRequest;
import lu.mypost.mep.model.request.CreateStepTemplateRequest;
import lu.mypost.mep.model.request.CreateStepsetTemplateRequest;
import lu.mypost.mep.model.response.MepTemplateResponse;
import lu.mypost.mep.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    private TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping({""})
    public ResponseEntity<List<MepTemplateResponse>> getTemplates() {

        return ResponseEntity.ok(
                this.templateService.getAll()
                        .stream()
                        .map(MepTemplateMapper::createResponse).collect(Collectors.toList())
        );
    }

    @PostMapping({""})
    public ResponseEntity<MepTemplateResponse> createTemplate(@RequestBody @Valid final CreateMepTemplateRequest request) throws AlreadyExistsException {

        MepTemplate createdTemplate = this.templateService.create(request.getName());
        return ResponseEntity.created(URI.create("")).body(MepTemplateMapper.createResponse(createdTemplate));
    }

    @GetMapping({"/{templateId}"})
    public ResponseEntity<MepTemplateResponse> getTemplate(@PathVariable("templateId") String templateId) throws NotFoundException {

        return ResponseEntity.ok(
                MepTemplateMapper.createResponse(this.templateService.findById(templateId))
        );
    }

    @PostMapping({"/{templateId}/stepsets"})
    public ResponseEntity<MepTemplateResponse> createTemplateStepset(@PathVariable("templateId") String templateId,
                                                                     @RequestBody @Valid final CreateStepsetTemplateRequest request) throws NotFoundException, AlreadyExistsException {

        MepTemplate createdTemplate = this.templateService.createStepet(templateId, request.getName(), request.getOrder());
        return ResponseEntity.created(URI.create("")).body(MepTemplateMapper.createResponse(createdTemplate));
    }

    @PostMapping({"/{templateId}/stepsets/{stepsetId}/steps"})
    public ResponseEntity<MepTemplateResponse> createTemplateStep(@PathVariable("templateId") String templateId,
                                                                  @PathVariable("stepsetId") String stepsetId,
                                                                  @RequestBody @Valid final CreateStepTemplateRequest request) throws NotFoundException {

        MepTemplate createdTemplate = this.templateService.createStep(templateId, stepsetId, request.getName(), request.getOrder(), request.getStatus());

        return ResponseEntity.ok(MepTemplateMapper.createResponse(createdTemplate));
    }

}

