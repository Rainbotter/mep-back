package lu.mypost.mep.service;

import lombok.Synchronized;
import lu.mypost.mep.exception.CantBeModifiedException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.document.mep.Api;
import lu.mypost.mep.model.document.mep.DockerApi;
import lu.mypost.mep.model.document.mep.Mep;
import lu.mypost.mep.model.document.mep.RancherApi;
import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.model.enums.Status;
import lu.mypost.mep.model.enums.Type;
import lu.mypost.mep.util.FieldsUtils;
import lu.mypost.mep.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ApiService {

    private MepService mepService;
    private TemplateService templateService;

    @Autowired
    public ApiService(MepService mepService, TemplateService templateService) {
        this.mepService = mepService;
        this.templateService = templateService;
    }

    public List<Api> getAll(String mepId) throws NotFoundException {
        return this.mepService.findById(mepId).getApis();
    }

    public Api findById(String mepId, String apiId) throws NotFoundException {
        return this.findApiFromMep(this.mepService.findById(mepId), apiId);
    }

    @Synchronized
    public Api create(String mepId,
                      String apiName,
                      String maintainer,
                      Type type,
                      String newVersion,
                      String oldVersion) throws NotFoundException, CantBeModifiedException {

        Mep mep = this.mepService.findById(mepId);

        if (mep.getClosureDate() != null) {
            throw new CantBeModifiedException("The mep is closed");
        }

        Api api;

        switch (type) {
            case DOCKER:
                api = DockerApi.builder().build();
                break;
            case RANCHER:
                api = RancherApi.builder().build();
                break;
            default:
                throw new UnsupportedOperationException("The api type " + type + "is not currently supported");
        }

        String suffix = RandomStringUtils.randomAlphabetic(10);
        api.setId(StringUtils.formatNameToId(apiName + suffix));
        api.setName(apiName);
        api.setMaintainer(maintainer);
        api.setType(type);
        api.setNewVersion(newVersion);
        api.setOldVersion(oldVersion);

        MepTemplate mepTemplate = this.templateService.findById(mep.getTemplateId());
        api.setStepsets(mepTemplate.getStepsets());

        mep.getApis().add(api);
        this.mepService.save(mep);

        return api;
    }

    @Synchronized
    public void updateField(String mepId, String apiId, String fieldName, String newValue) throws NotFoundException, CantBeModifiedException {
        Mep mep = this.mepService.findById(mepId);
        Api api = this.findApiFromMep(mep, apiId);


        if (mep.getClosureDate() != null) {
            throw new CantBeModifiedException("The mep is closed");
        }

        try {
            Object effectiveNewValue;
            Class newValueType = FieldsUtils.getFieldType(api, fieldName);
            if (!Objects.equals(newValueType.getSimpleName(), "String")) {
                // This should be improved in a way that removes that switch case
                switch (newValueType.getSimpleName()) {
                    case "Type":
                        effectiveNewValue = Type.valueOf(newValue);
                        break;
                    case "Status":
                        effectiveNewValue = Status.valueOf(newValue);
                        break;
                    default:
                        throw new CantBeModifiedException("This field doesn't exists: " + newValueType.getSimpleName());
                }
            } else {
                effectiveNewValue = newValue;
            }

            FieldsUtils.updateField(api, fieldName, effectiveNewValue);
            this.mepService.save(mep);
        } catch (ClassNotFoundException | IllegalAccessException e) {
            throw new InternalError("An error occured while updating the api");
        }
    }

    public Api findApiFromMep(Mep mep, String apiId) throws NotFoundException {
        return mep.getApis()
                .stream()
                .filter(api -> apiId.equalsIgnoreCase(api.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Api with id " + apiId + " doesn't exists in mep " + mep.getId()));
    }

}
