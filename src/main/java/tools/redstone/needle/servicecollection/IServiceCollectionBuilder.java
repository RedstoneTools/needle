package tools.redstone.needle.servicecollection;

import tools.redstone.needle.ServiceLifetime;

import java.util.Optional;
import java.util.function.Function;

public interface IServiceCollectionBuilder {
    <TServiceInterface, TServiceImplementation> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Class<TServiceImplementation> serviceImplementationClass, ServiceLifetime serviceLifetime);
    <TServiceInterface> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Function<IServiceCollection, Optional<TServiceInterface>> serviceSupplier, ServiceLifetime serviceLifetime);
    IServiceCollection build();

    private ServiceLifetime getDefaultServiceLifetime() {
        return ServiceLifetime.TRANSIENT;
    }

//    default <TServiceInterface> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, TServiceInterface service) {
//        return addService(serviceInterfaceClass, ServiceLifetime.SINGLETON, () -> service);
//    }
//
//    default <TServiceInterface, TServiceImplementation> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Class<TServiceImplementation> serviceImplementationClass) {
//        return addService(serviceInterfaceClass, serviceImplementationClass, getDefaultServiceLifetime());
//    }
//
//    default <TService> IServiceCollectionBuilder addService(Class<TService> serviceClass, ServiceLifetime serviceLifetime) {
//        return addService(serviceClass, serviceClass, serviceLifetime);
//    }
//
//    default <TService> IServiceCollectionBuilder addService(Class<TService> serviceClass) {
//        return addService(serviceClass, getDefaultServiceLifetime());
//    }
//
//    default <TServiceInterface, TServiceImplementation> IServiceCollectionBuilder addService(Class<TServiceInterface> serviceInterfaceClass, Class<TServiceImplementation> serviceImplementationClass, Function<IServiceCollection, TService> serviceSupplier) {
//        return addService(serviceInterfaceClass, serviceImplementationClass, getDefaultServiceLifetime(), serviceSupplier);
//    }
//
//    default <TService> IServiceCollectionBuilder addService(Class<TService> serviceClass, ServiceLifetime serviceLifetime, Function<IServiceCollection, TService> serviceSupplier) {
//        return addService(serviceClass, serviceClass, serviceLifetime, serviceSupplier);
//    }
//
//    default <TService> IServiceCollectionBuilder addService(Class<TService> serviceClass, Function<IServiceCollection, TService> serviceSupplier) {
//        return addService(serviceClass, getDefaultServiceLifetime(), serviceSupplier);
//    }
//
//    default <TService> IServiceCollectionBuilder addService(Class<TService> serviceClass, TService service) {
//        return addService(serviceClass, serviceClass, service);
//    }
}
