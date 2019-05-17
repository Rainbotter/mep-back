package lu.mypost.mep.model.document.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lu.mypost.mep.model.enums.NotificationSubject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private NotificationSubject subject;
    private NotificationSubjectParent parent;
    private String subjectId;
    private String message;

}
