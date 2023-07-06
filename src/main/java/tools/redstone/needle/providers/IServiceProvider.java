package tools.redstone.needle.providers;

import tools.redstone.needle.servicecollection.IServiceCollection;

import java.util.Optional;

public interface IServiceProvider<TService> {
    Optional<TService> getService(IServiceCollection serviceCollection);
}
