package com.buzzmove.placesapiexample.data.di;

import com.buzzmove.placesapiexample.data.Repository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    Repository provideRepository();
}


