package lu.mypost.mep.service;

import lu.mypost.mep.exception.CantBeModifiedException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.document.mep.Api;
import lu.mypost.mep.model.document.mep.Mep;
import lu.mypost.mep.model.enums.Status;
import lu.mypost.mep.model.document.mep.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepService {

    private MepService mepService;
    private ApiService apiService;

    @Autowired
    public StepService(MepService mepService, ApiService apiService) {
        this.mepService = mepService;
        this.apiService = apiService;
    }

    public void updateStatus(String mepId, String apiId, String stepsetId, String stepId, Status newStatus) throws NotFoundException, CantBeModifiedException {
        Mep mep = this.mepService.findById(mepId);
        Api api = this.apiService.findApiFromMep(mep, apiId);

        if (mep.getClosureDate() != null) {
            throw new CantBeModifiedException("The mep is closed");
        }

        this.getStepFromApi(api, stepsetId, stepId).setStatus(newStatus);

        this.mepService.save(mep);
    }

    public Step getStepFromApi(Api api, String stepsetId, String stepId) throws NotFoundException {
        return api.getStepsets().stream()
                .filter(stepset -> stepset.getId().equalsIgnoreCase(stepsetId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Can't find stepSet with id " + stepsetId))
                .getSteps().stream()
                .filter(step -> step.getId().equalsIgnoreCase(stepId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Can't find step with id " + stepId));
    }

}
