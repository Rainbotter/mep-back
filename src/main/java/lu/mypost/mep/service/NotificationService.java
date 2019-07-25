package lu.mypost.mep.service;

import lu.mypost.mep.model.document.notification.Notification;
import lu.mypost.mep.model.enums.NotificationSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private MepService mepService;

    @Autowired
    public NotificationService(MepService mepService) {
        this.mepService = mepService;
    }

    public List<Notification> getNotifications() {
        List<Notification> result = new ArrayList<>();

        result.addAll(this.getShortDueDateMepsNotifications());

        return result;
    }

    private List<Notification> getShortDueDateMepsNotifications() {
        Calendar reminderDate = Calendar.getInstance();
        reminderDate.setTime(new Date());
        reminderDate.add(Calendar.DATE, -7);

        return mepService.getAll()
                .stream()
                .filter(mep -> {
                    return mep.getDueDate() != null &&
                            mep.getClosureDate() != null &&
                            reminderDate.getTime().compareTo(mep.getDueDate()) < 0;
                })
                .map(mep -> Notification.builder()
                        .message("La mep " + mep.getName() + " approche et n'a toujours pas été complétée")
                        .subject(NotificationSubject.MEP)
                        .subjectId(mep.getId())
                        .build())
                .collect(Collectors.toList());
    }

}
