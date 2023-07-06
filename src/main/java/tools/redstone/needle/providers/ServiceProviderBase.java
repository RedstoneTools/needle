package tools.redstone.needle.providers;

import tools.redstone.needle.helpers.ServiceProviderHelpers;
import tools.redstone.needle.servicecollection.IServiceCollection;

import java.util.Optional;
import java.util.function.Function;

public abstract class ServiceProviderBase<TService> implements IServiceProvider<TService> {
    private final Function<IServiceCollection, Optional<TService>> serviceSupplier;

    public ServiceProviderBase(Function<IServiceCollection, Optional<TService>> serviceSupplier) {
        this.serviceSupplier = serviceSupplier;
    }

    public ServiceProviderBase(Class<TService> serviceClass) {
        this(serviceCollection -> ServiceProviderHelpers.createService(serviceClass, serviceCollection));
    }

    protected final Optional<TService> createService(IServiceCollection serviceCollection) {
        return serviceSupplier.apply(serviceCollection);
    }
}
