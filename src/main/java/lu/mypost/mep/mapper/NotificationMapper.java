package lu.mypost.mep.mapper;

import lu.mypost.mep.model.document.notification.Notification;
import lu.mypost.mep.model.document.notification.NotificationSubjectParent;
import lu.mypost.mep.model.response.NotificationResponse;
import lu.mypost.mep.model.response.NotificationSubjectParentResponse;

public class NotificationMapper {

    public static NotificationResponse createResponse(Notification notification) {
        return NotificationResponse.builder()
                .message(notification.getMessage())
                .subject(notification.getSubject())
                .subjectId(notification.getSubjectId())
                .parent(createParentResponse(notification.getParent()))
                .build();
    }

    private static NotificationSubjectParentResponse createParentResponse(NotificationSubjectParent parent) {

        if (parent == null) {
            return null;
        }

        return NotificationSubjectParentResponse.builder()
                .id(parent.getId())
                .subject(parent.getSubject())
                .parent(createParentResponse(parent.getParent()))
                .build();

    }
}
