package tools.redstone.needle.providers;

import tools.redstone.needle.servicecollection.IServiceCollection;

import java.util.Optional;
import java.util.function.Function;

public class TransientServiceProvider<TService> extends ServiceProviderBase<TService> {
    public TransientServiceProvider(Function<IServiceCollection, Optional<TService>> serviceSupplier) {
        super(serviceSupplier);
    }

    public TransientServiceProvider(Class<TService> serviceClass) {
        super(serviceClass);
    }

    @Override
    public Optional<TService> getService(IServiceCollection serviceCollection) {
        return createService(serviceCollection);
    }
}
