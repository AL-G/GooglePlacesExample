package com.buzzmove.placesapiexample.data.di;

import com.buzzmove.placesapiexample.data.Repository;
import com.buzzmove.placesapiexample.data.remote.RemoteSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    Repository provideRepository() {
        return new Repository(new RemoteSource());
    }

    @Provides
    @Singleton
    RemoteSource providRemoteDataSource() {
        return new RemoteSource();
    }
}
