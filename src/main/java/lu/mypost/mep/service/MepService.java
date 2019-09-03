package lu.mypost.mep.service;

import lombok.Synchronized;
import lu.mypost.mep.exception.AlreadyExistsException;
import lu.mypost.mep.exception.CantBeModifiedException;
import lu.mypost.mep.exception.NotFoundException;
import lu.mypost.mep.model.document.mep.Mep;
import lu.mypost.mep.repositoriy.MepRepository;
import lu.mypost.mep.util.FieldsUtils;
import lu.mypost.mep.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MepService {

    private MepRepository mepRepository;
    private TemplateService templateService;

    @Autowired
    public MepService(MepRepository mepRepository, TemplateService templateService) {
        this.mepRepository = mepRepository;
        this.templateService = templateService;
    }

    public List<Mep> getAll() {
        List<Mep> result = this.mepRepository.findAll();

        result.forEach(mep -> {
            if (mep.getApis() == null) {
                mep.setApis(new ArrayList<>());
            }
        });

        return result;
    }

    public Mep findById(String id) throws NotFoundException {
        Mep result = mepRepository.findById(id).orElseThrow(() -> new NotFoundException("Mep not found"));

        if (result.getApis() == null) {
            result.setApis(new ArrayList<>());
        }

        return result;
    }

    public Mep create(String name, String project, String templateId) throws AlreadyExistsException, NotFoundException {
        String suffix = RandomStringUtils.randomAlphabetic(10);
        String formattedName = StringUtils.formatNameToId(name + suffix);

        templateService.findById(templateId);

        if (this.mepRepository.findById(formattedName).isPresent()) {
            throw new AlreadyExistsException("Mep with name " + name + " already exists");
        }

        Mep result = Mep.builder()
                .id(formattedName)
                .name(name)
                .project(project)
                .templateId(templateId)
                .creationDate(new Date())
                .apis(new ArrayList<>())
                .build();

        this.save(result);

        return result;
    }

    @Synchronized
    public void updateField(String mepId, String fieldName, String newValue) throws NotFoundException, CantBeModifiedException {

        Mep mep = this.findById(mepId);

        if (mep.getClosureDate() != null) {
            throw new CantBeModifiedException("The mep is closed");
        }

        List<String> unmodifiableFields = Arrays.asList("id", "creationDate", "closeDate", "templateId");

        if (unmodifiableFields.stream().anyMatch(field -> field.equalsIgnoreCase(fieldName))) {
            throw new CantBeModifiedException("The field " + fieldName + " can't be modified in a mep");
        }


        try {
            FieldsUtils.updateField(mep, fieldName, newValue);
            this.save(mep);
        } catch (ClassNotFoundException | IllegalAccessException | ParseException e) {
            throw new InternalError("An error occured while updating the mep");
        }

    }

    public void closeMep(String mepId) throws NotFoundException {
        Mep mep = this.findById(mepId);

        Date closureDate = new Date();

        mep.setClosureDate(closureDate);

        this.save(mep, closureDate);
    }

    public void openMep(String mepId) throws NotFoundException {
        Mep mep = this.findById(mepId);

        mep.setClosureDate(null);

        this.save(mep);
    }

    private void save(Mep mep, Date date) {
        mep.setLastModificationDate(date);
        this.mepRepository.save(mep);
    }

    public void save(Mep mep) {
        mep.setLastModificationDate(new Date());
        this.mepRepository.save(mep);
    }

}
