package lu.mypost.mep.controller;

import lu.mypost.mep.model.document.mep.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    @GetMapping({""})
    public ResponseEntity<List<Status>> getStatuses() {

        return ResponseEntity.ok(Arrays.asList(Status.values()));
    }
}
