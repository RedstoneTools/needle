package tools.redstone.needle.servicecollection;

import tools.redstone.needle.providers.IServiceProvider;

import java.util.Map;
import java.util.Optional;

public class ServiceCollection implements IServiceCollection {
    private final Map<Class<?>, IServiceProvider<?>> serviceProviders;

    ServiceCollection(Map<Class<?>, IServiceProvider<?>> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    @Override
    public <TService> Optional<TService> getService(Class<TService> serviceClass) {
        return getServiceProvider(serviceClass)
                .flatMap(provider -> provider.getService(this));
    }

    @SuppressWarnings("unchecked")
    private <TService> Optional<IServiceProvider<TService>> getServiceProvider(Class<TService> serviceClass) {
        if (!serviceProviders.containsKey(serviceClass)) {
            return Optional.empty();
        }

        return Optional.of((IServiceProvider<TService>) serviceProviders.get(serviceClass));
    }
}
