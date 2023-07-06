package tools.redstone.needle.servicecollection;

import tools.redstone.needle.helpers.ReflectionHelpers;

import java.util.Optional;

public interface IServiceCollection {
    <TService> Optional<TService> getService(Class<TService> serviceClass);

    default <TService> TService getRequiredService(Class<TService> serviceClass) {
        return getService(serviceClass).orElseThrow(() -> new RuntimeException("Could not get required service " + ReflectionHelpers.referenceClass(serviceClass)));
    }
}
