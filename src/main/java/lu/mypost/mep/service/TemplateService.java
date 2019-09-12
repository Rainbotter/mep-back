package lu.mypost.mep.service;

import lombok.Synchronized;
import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.document.mep.Step;
import lu.mypost.mep.model.document.mep.Stepset;
import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.model.enums.Status;
import lu.mypost.mep.repositoriy.TemplateRepository;
import lu.mypost.mep.util.CustomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TemplateService {

    private TemplateRepository repository;

    @Autowired
    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }

    public List<MepTemplate> getAll() {
        return this.repository.findAll();
    }

    public MepTemplate findById(String id) throws NotFoundException {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("Template not found"));
    }

    public Stepset findStepSetById(MepTemplate template, String stepSetId) throws NotFoundException {
        return template.getStepsets().stream()
                .filter(stepset -> stepset.getId().equalsIgnoreCase(stepSetId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Step set with id=" + stepSetId + " can't be found in template with id= " + template.getId()));
    }

    public Step findStepById(MepTemplate template, String stepSetId, String stepId) throws NotFoundException {
        return this.findStepSetById(template, stepSetId).getSteps().stream()
                .filter(step -> step.getId().equalsIgnoreCase(stepId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Step with id=" + stepId + " can't be found in template with id= " + template.getId()));
    }

    public MepTemplate create(String name) throws AlreadyExistsException {
        String formattedName = CustomStringUtils.formatNameToId(name);

        if (this.repository.findById(formattedName).isPresent()) {
            throw new AlreadyExistsException("Template with name " + name + " already exists");
        }

        MepTemplate result = MepTemplate.builder()
                .id(formattedName)
                .name(name)
                .stepsets(new ArrayList<>())
                .build();

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public MepTemplate createStepset(String templateId, String name, int order) throws NotFoundException, AlreadyExistsException {
        MepTemplate result = this.findById(templateId);

        if (result.getStepsets().stream().anyMatch(stepset -> stepset.getOrder() == order)) {
            throw new AlreadyExistsException("Step set with order " + order + " already exists");
        }

        if (result.getStepsets().stream().anyMatch(stepset -> stepset.getId().equalsIgnoreCase(CustomStringUtils.formatNameToId(name)))) {
            throw new AlreadyExistsException("Step set with name " + name + " already exists");
        }

        result.getStepsets().add(
                Stepset.builder()
                        .id(CustomStringUtils.formatNameToId(name))
                        .name(name)
                        .order(order)
                        .steps(new ArrayList<>())
                        .build()
        );

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public MepTemplate createStep(String templateId, String stepSetId, String name, int order, Status status) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        Stepset matchedStepset = result.getStepsets().stream()
                .filter(stepset -> stepset.getId().equalsIgnoreCase(stepSetId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Step set with id=" + stepSetId + " can't be found in template with id= " + templateId));

        matchedStepset.getSteps().add(
                Step.builder()
                        .id(CustomStringUtils.formatNameToId(name))
                        .name(name)
                        .order(order)
                        .status(status)
                        .build()
        );

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public void deleteTemplate(String templateId) throws NotFoundException {
        MepTemplate result = this.findById(templateId);
        this.repository.delete(result);
    }

    @Synchronized
    public MepTemplate deleteStepSet(String templateId, String stepSetId) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        result.setStepsets(result.getStepsets().stream().filter(stepset -> !Objects.equals(stepset.getId(), stepSetId)).collect(Collectors.toList()));

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public MepTemplate deleteStep(String templateId, String stepSetId, String stepId) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        Stepset matchedStepset = this.findStepSetById(result, stepSetId);

        matchedStepset.setSteps(matchedStepset.getSteps().stream().filter(step -> !Objects.equals(step.getId(), stepId)).collect(Collectors.toList()));

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public MepTemplate renameStepSet(String templateId, String stepSetId, String newName) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        Stepset matchedStepset = this.findStepSetById(result, stepSetId);
        matchedStepset.setName(newName);

        this.repository.save(result);

        return result;
    }

    @Synchronized
    public MepTemplate renameStep(String templateId, String stepSetId, String stepId, String newName) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        Step matchedStep = this.findStepById(result, stepSetId, stepId);
        matchedStep.setName(newName);

        this.repository.save(result);

        return result;
    }

}
