package tools.redstone.needle.helpers;

import tools.redstone.needle.Inject;
import tools.redstone.needle.servicecollection.IServiceCollection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public final class ServiceProviderHelpers {
    private ServiceProviderHelpers() {
    }

    public static <TService> Optional<TService> createService(Class<TService> serviceClass, IServiceCollection serviceCollection) {
        return configureService(createService(serviceClass), serviceCollection);
    }

    private static <TService> Optional<TService> configureService(TService service, IServiceCollection serviceCollection) {
        var fieldsToInject = ReflectionHelpers.getAnnotatedFields(service.getClass(), Inject.class).toList();

        for (var fieldToInject : fieldsToInject) {
            // TODO: Refactor this, too much code
            try {
                fieldToInject.setAccessible(true);
            } catch (Throwable e) {
                throw new RuntimeException("Cannot set accessibility of " + ReflectionHelpers.referenceField(fieldToInject), e);
            }

            var valueToInject = serviceCollection.getService(getTypeToInject(fieldToInject));
            try {
                if (isOptional(fieldToInject)) {
                    fieldToInject.set(service, valueToInject);
                } else {
                    if (valueToInject.isEmpty()) {
                        return Optional.empty();
                    }

                    fieldToInject.set(service, valueToInject.get());
                }
            } catch (Throwable e) {
                throw new RuntimeException("Cannot set value of " + ReflectionHelpers.referenceField(fieldToInject), e);
            }
        }

        return Optional.of(service);
    }

    private static Class<?> getTypeToInject(Field fieldToInject) {
        if (!isOptional(fieldToInject)) {
            return fieldToInject.getType();
        }

        return (Class<?>) ((ParameterizedType) fieldToInject.getGenericType()).getActualTypeArguments()[0];  // TODO: Put this in reflection helpers
    }

    private static boolean isOptional(Field field) {
        return field.getType() == Optional.class;
    }

    private static <TService> TService createService(Class<TService> serviceClass) {
        try {
            return serviceClass.getConstructor().newInstance();  // TODO: Use a constructor annotated with @Inject (make sure there's at most 1) if present
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No parameterless constructor found for " + ReflectionHelpers.referenceClass(serviceClass), e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
