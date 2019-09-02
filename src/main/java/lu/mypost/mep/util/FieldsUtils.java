package lu.mypost.mep.util;

import lu.mypost.mep.exception.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static lu.mypost.mep.configuration.Constants.dateSerializationFormat;

public class FieldsUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat(dateSerializationFormat);

    public static void updateField(Object object, String fieldName, Object newValue) throws ClassNotFoundException, NotFoundException, IllegalAccessException, ParseException {
        Field fieldToModify;

        List<Field> fields = Arrays.asList(ArrayUtils.addAll(
                Class.forName(object.getClass().getCanonicalName()).getDeclaredFields(),
                Class.forName(object.getClass().getSuperclass().getCanonicalName()).getDeclaredFields())
        );

        fieldToModify = fields.stream()
                .filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Field " + fieldName + " doesn't exists in type Mep"));


        if (Objects.equals(fieldToModify.getType().getSimpleName(), "Date")) {
            newValue = sdf.parse(newValue.toString());
        }

        FieldUtils.writeField(fieldToModify, object, newValue, true);
    }

    public static Class getFieldType(Object object, String fieldName) throws ClassNotFoundException, NotFoundException {
        Field fieldToModify;

        List<Field> fields = Arrays.asList(ArrayUtils.addAll(
                Class.forName(object.getClass().getCanonicalName()).getDeclaredFields(),
                Class.forName(object.getClass().getSuperclass().getCanonicalName()).getDeclaredFields())
        );

        fieldToModify = fields.stream()
                .filter(field -> field.getName().equalsIgnoreCase(fieldName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Field " + fieldName + " doesn't exists in type Mep"));

        return fieldToModify.getType();
    }

}
