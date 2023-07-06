package tools.redstone.needle.servicecollection;

import tools.redstone.needle.ServiceLifetime;
import tools.redstone.needle.helpers.ReflectionHelpers;
import tools.redstone.needle.providers.IServiceProvider;
import tools.redstone.needle.providers.SingletonServiceProvider;
import tools.redstone.needle.providers.TransientServiceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ServiceCollectionBuilder implements IServiceCollectionBuilder {
    private final Map<Class<?>, IServiceProvider<?>> serviceProviders = new HashMap<>();

    @Override
    public <TServiceInterface, TServiceImplementation> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Class<TServiceImplementation> serviceImplementationClass, ServiceLifetime serviceLifetime) {
        return addServiceProvider(serviceInterfaceClass, createServiceProvider(serviceImplementationClass, serviceLifetime));
    }

    @Override
    public <TServiceInterface> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Function<IServiceCollection, Optional<TServiceInterface>> serviceSupplier, ServiceLifetime serviceLifetime) {
        return addServiceProvider(serviceInterfaceClass, createServiceProvider(serviceSupplier, serviceLifetime));
    }

    @Override
    public IServiceCollection build() {
        return new ServiceCollection(serviceProviders);
    }

    private <TServiceInterface> IServiceCollectionBuilder addServiceProvider(Class<TServiceInterface> serviceInterfaceClass, IServiceProvider<?> serviceProvider) {
        if (serviceProviders.containsKey(serviceInterfaceClass)) {
            throw new RuntimeException("Cannot register " + ReflectionHelpers.referenceClass(serviceInterfaceClass) + " twice");
        }

        serviceProviders.put(serviceInterfaceClass, serviceProvider);

        return this;
    }

    private <TService> IServiceProvider<TService> createServiceProvider(Class<TService> serviceClass, ServiceLifetime serviceLifetime) {
        return switch (serviceLifetime) {
            case TRANSIENT -> new TransientServiceProvider<>(serviceClass);
            case SINGLETON -> new SingletonServiceProvider<>(serviceClass);
        };
    }

    private <TService> IServiceProvider<TService> createServiceProvider(Function<IServiceCollection, Optional<TService>> serviceSupplier, ServiceLifetime serviceLifetime) {
        return switch (serviceLifetime) {
            case TRANSIENT -> new TransientServiceProvider<>(serviceSupplier);
            case SINGLETON -> new SingletonServiceProvider<>(serviceSupplier);
        };
    }
}
