package com.buzzmove.placesapiexample.data;

import android.support.annotation.NonNull;


import com.buzzmove.placesapiexample.data.entities.Result;

import java.util.List;

public interface DataSourceInterface {

    interface GetPlacesCallback {

        void onLoaded(List<Result> results);

        void onDataNotAvailable();

    }

    void searchPlaces(@NonNull GetPlacesCallback callback, String search);

}
