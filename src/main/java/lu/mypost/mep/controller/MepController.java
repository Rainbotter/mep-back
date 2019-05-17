package lu.mypost.mep.controller;

import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.CantBeModifiedException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.mapper.ApiMapper;
import lu.mypost.mep.mapper.MepMapper;
import lu.mypost.mep.model.document.mep.Api;
import lu.mypost.mep.model.document.mep.Mep;
import lu.mypost.mep.model.request.CreateApiRequest;
import lu.mypost.mep.model.request.CreateMepRequest;
import lu.mypost.mep.model.request.UpdateApiFieldRequest;
import lu.mypost.mep.model.request.UpdateMepRequest;
import lu.mypost.mep.model.request.UpdateStepStatusRequest;
import lu.mypost.mep.model.response.ApiResponse;
import lu.mypost.mep.model.response.MepResponse;
import lu.mypost.mep.model.response.TruncatedMepResponse;
import lu.mypost.mep.service.ApiService;
import lu.mypost.mep.service.MepService;
import lu.mypost.mep.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meps")
public class MepController {

    private MepService mepService;
    private ApiService apiService;
    private StepService stepService;

    @Autowired
    public MepController(MepService mepService, ApiService apiService, StepService stepService) {
        this.mepService = mepService;
        this.apiService = apiService;
        this.stepService = stepService;
    }

    @GetMapping({""})
    public ResponseEntity<List<TruncatedMepResponse>> getMeps() {

        return ResponseEntity.ok(
                this.mepService.getAll()
                        .stream()
                        .map(MepMapper::createTruncatedResponse).collect(Collectors.toList())
        );
    }

    @PostMapping({""})
    public ResponseEntity<TruncatedMepResponse> createMep(@RequestBody @Valid final CreateMepRequest request) throws AlreadyExistsException, NotFoundException {

        Mep createdMep = this.mepService.create(request.getName(), request.getProject(), request.getVersion(), request.getTemplateId());

        return ResponseEntity.created(URI.create("")).body(MepMapper.createTruncatedResponse(createdMep));
    }

    @GetMapping({"/{mepId}"})
    public ResponseEntity<MepResponse> getMep(@PathVariable("mepId") String mepId) throws NotFoundException {

        return ResponseEntity.ok(MepMapper.createResponse(this.mepService.findById(mepId)));
    }

    @PutMapping({"/{mepId}/{fieldName}"})
    public ResponseEntity<ApiResponse> updateField(@PathVariable("mepId") String mepId,
                                                   @PathVariable("fieldName") String fieldName,
                                                   @RequestBody @Valid final UpdateMepRequest request) throws NotFoundException, CantBeModifiedException {

        this.mepService.updateField(mepId, fieldName, request.getNewValue());

        return ResponseEntity.ok().build();

    }

    @PutMapping({"/{mepId}/close"})
    public ResponseEntity<ApiResponse> closeMep(@PathVariable("mepId") String mepId) throws NotFoundException {

        this.mepService.closeMep(mepId);

        return ResponseEntity.ok().build();

    }

    @PutMapping({"/{mepId}/open"})
    public ResponseEntity<ApiResponse> openMep(@PathVariable("mepId") String mepId) throws NotFoundException {

        this.mepService.openMep(mepId);

        return ResponseEntity.ok().build();

    }

    @GetMapping({"/{mepId}/apis"})
    public ResponseEntity<List<ApiResponse>> getApis(@PathVariable("mepId") String mepId) throws NotFoundException {

        return ResponseEntity.ok(
                this.apiService.getAll(mepId)
                        .stream()
                        .map(ApiMapper::createResponse).collect(Collectors.toList())
        );
    }

    @PostMapping({"/{mepId}/apis"})
    public ResponseEntity<ApiResponse> createApi(@PathVariable("mepId") String mepId,
                                                 @RequestBody @Valid final CreateApiRequest request) throws NotFoundException, CantBeModifiedException {

        Api createdApi = this.apiService.create(mepId,
                request.getName(),
                request.getMaintainer(),
                request.getType(),
                request.getNewVersion(),
                request.getOldVersion());
        return ResponseEntity.created(URI.create("")).body(ApiMapper.createResponse(createdApi));
    }

    @GetMapping({"/{mepId}/apis/{apiId}"})
    public ResponseEntity<ApiResponse> getApi(@PathVariable("mepId") String mepId,
                                              @PathVariable("apiId") String apidId) throws NotFoundException {

        return ResponseEntity.ok(ApiMapper.createResponse(this.apiService.findById(mepId, apidId)));
    }

    @PutMapping({"/{mepId}/apis/{apiId}/{fieldName}"})
    public ResponseEntity<ApiResponse> updateField(@PathVariable("mepId") String mepId,
                                                   @PathVariable("apiId") String apidId,
                                                   @PathVariable("fieldName") String fieldName,
                                                   @RequestBody @Valid final UpdateApiFieldRequest request) throws NotFoundException, CantBeModifiedException {

        this.apiService.updateField(mepId, apidId, fieldName, request.getNewValue());

        return ResponseEntity.ok().build();
    }

    @PutMapping({"/{mepId}/apis/{apiId}/stepsets/{stepsetId}/steps/{stepId}"})
    public ResponseEntity<ApiResponse> updateStep(@PathVariable("mepId") String mepId,
                                                   @PathVariable("apiId") String apidId,
                                                   @PathVariable("stepsetId") String stepsetId,
                                                   @PathVariable("stepId") String stepId,
                                                   @RequestBody @Valid final UpdateStepStatusRequest request) throws NotFoundException, CantBeModifiedException {

        this.stepService.updateStatus(mepId, apidId, stepsetId, stepId, request.getNewStatus());

        return ResponseEntity.ok().build();
    }

}
