package lu.mypost.mep.util;

import lu.mypost.mep.exception.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldsUtils {

    public static void updateField(Object object, String fieldName, String newValue) throws ClassNotFoundException, NotFoundException, IllegalAccessException {
        Field fieldToModify;

        List<Field> fields = Arrays.asList(ArrayUtils.addAll(
                Class.forName(object.getClass().getCanonicalName()).getDeclaredFields(),
                Class.forName(object.getClass().getSuperclass().getCanonicalName()).getDeclaredFields())
        );

        fieldToModify = fields.stream()
                .filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Field " + fieldName + " doesn't exists in type Mep"));

        FieldUtils.writeField(fieldToModify, object, newValue, true);
    }
}
