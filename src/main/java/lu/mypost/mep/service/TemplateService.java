package lu.mypost.mep.service;

import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.document.mep.Status;
import lu.mypost.mep.model.document.mep.Step;
import lu.mypost.mep.model.document.mep.Stepset;
import lu.mypost.mep.model.document.template.MepTemplate;
import lu.mypost.mep.repositoriy.TemplateRepository;
import lu.mypost.mep.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public MepTemplate create(String name) throws AlreadyExistsException {
        String formattedName = StringUtils.formatNameToId(name);

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

    public MepTemplate createStepet(String templateId, String name, int order) throws NotFoundException, AlreadyExistsException {
        MepTemplate result = this.findById(templateId);

        if (result.getStepsets().stream().anyMatch(stepset -> stepset.getOrder() == order)) {
            throw new AlreadyExistsException("Step set with order " + order + " already exists");
        }

        if (result.getStepsets().stream().anyMatch(stepset -> stepset.getId().equalsIgnoreCase(StringUtils.formatNameToId(name)))) {
            throw new AlreadyExistsException("Step set with name " + name + " already exists");
        }

        result.getStepsets().add(
                Stepset.builder()
                        .id(StringUtils.formatNameToId(name))
                        .name(name)
                        .order(order)
                        .steps(new ArrayList<>())
                        .build()
        );

        this.repository.save(result);

        return result;
    }

    public MepTemplate createStep(String templateId, String stepsetId, String name, int order, Status status) throws NotFoundException {
        MepTemplate result = this.findById(templateId);

        Stepset matchedStepset = result.getStepsets().stream()
                .filter(stepset -> stepset.getId().equalsIgnoreCase(stepsetId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Step set with id=" + stepsetId + " can't be found in template with id= " + templateId));

        matchedStepset.getSteps().add(
                Step.builder()
                        .id(StringUtils.formatNameToId(name))
                        .name(name)
                        .order(order)
                        .status(status)
                        .build()
        );

        this.repository.save(result);

        return result;
    }

}
