package lu.mypost.mep.controller;

import lu.mypost.mep.mapper.NotificationMapper;
import lu.mypost.mep.model.response.NotificationResponse;
import lu.mypost.mep.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping({""})
    public ResponseEntity<List<NotificationResponse>> getNotifications() {

        return ResponseEntity.ok(
                this.notificationService
                        .getNotifications()
                        .stream()
                        .map(NotificationMapper::createResponse).collect(Collectors.toList())
        );
    }

}
