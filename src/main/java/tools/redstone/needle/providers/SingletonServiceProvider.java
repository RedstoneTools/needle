package tools.redstone.needle.providers;

import tools.redstone.needle.servicecollection.IServiceCollection;

import java.util.Optional;
import java.util.function.Function;

public class SingletonServiceProvider<TService> extends ServiceProviderBase<TService> {
    private Optional<Optional<TService>> serviceInstance = Optional.empty();

    public SingletonServiceProvider(Function<IServiceCollection, Optional<TService>> serviceSupplier) {
        super(serviceSupplier);
    }

    public SingletonServiceProvider(Class<TService> serviceClass) {
        super(serviceClass);
    }

    @Override
    public Optional<TService> getService(IServiceCollection serviceCollection) {
        if (serviceInstance.isEmpty()) {
            serviceInstance = Optional.of(createService(serviceCollection));
        }

        return serviceInstance.get();
    }
}
