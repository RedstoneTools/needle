package tools.redstone.needle.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public final class ReflectionHelpers {
    private ReflectionHelpers() {
    }

    public static Stream<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return getAnnotatedObjects(getFields(clazz), annotationClass);
    }

    public static Stream<Field> getFields(Class<?> clazz) {
        if (clazz == Object.class) {
            return Arrays.stream(clazz.getDeclaredFields());
        }

        return Stream.concat(Arrays.stream(clazz.getDeclaredFields()), getFields(clazz.getSuperclass()));
    }

    public static <T extends AccessibleObject> Stream<T> getAnnotatedObjects(Stream<T> objects, Class<? extends Annotation> annotationClass) {
        return objects.filter(object -> object.isAnnotationPresent(annotationClass));
    }

    public static String referenceClass(Class<?> clazz) {
        return "class \"" + clazz.getName() + "\"";
    }

    public static String referenceField(Field field) {
        return "field \"" + field.getName() + "\" of " + referenceClass(field.getDeclaringClass());
    }
}
