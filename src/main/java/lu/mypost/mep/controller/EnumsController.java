package lu.mypost.mep.controller;

import lu.mypost.mep.model.enums.Status;
import lu.mypost.mep.service.ApiService;
import lu.mypost.mep.service.MepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
public class EnumsController {

    private MepService mepService;
    private ApiService apiService;

    @Autowired
    public EnumsController(final MepService mepService, final ApiService apiService) {
        this.mepService = mepService;
        this.apiService = apiService;
    }

    @GetMapping({"/statuses"})
    public ResponseEntity<List<Status>> getStatuses() {
        return ResponseEntity.ok(Arrays.asList(Status.values()));
    }

    @GetMapping({"/distinct/apis"})
    public ResponseEntity<Set<String>> getApis() {
        return ResponseEntity.ok(this.apiService.getDistinctApis());
    }

    @GetMapping({"/distinct/projects"})
    public ResponseEntity<Set<String>> getMeps() {
        return ResponseEntity.ok(this.mepService.getDistinctProjects());
    }

}
