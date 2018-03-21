package com.buzzmove.placesapiexample.data;

import android.support.annotation.NonNull;

import com.buzzmove.placesapiexample.data.remote.RemoteSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository implements DataSourceInterface {

    public RemoteSource mRemoteSource;

    @Inject
    public Repository(RemoteSource s) {
        mRemoteSource = s;
    }

    public void searchPlaces(@NonNull DataSourceInterface.GetPlacesCallback callback, String search) {
        mRemoteSource.searchPlaces(callback, search);
    }

}

