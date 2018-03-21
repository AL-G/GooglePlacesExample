package com.buzzmove.placesapiexample.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.buzzmove.placesapiexample.data.DataSourceInterface;
import com.buzzmove.placesapiexample.data.entities.SearchResult;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteSource implements DataSourceInterface {

    private Retrofit retrofit;

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    private final String API_KEY = "AIzaSyDaZ4mnBNydc5CYhOD_Hz1_emxzS3iF4GI";

    @Inject
    public RemoteSource() {
        retrofit = getRestAdapter();
    }

    @Override
    public void searchPlaces(final @NonNull GetPlacesCallback callback, String search) {

        getRestAdapter().create(PlacesApiInterface.class).searchPlaces(search, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull SearchResult result) {
                        callback.onLoaded(result.getResults());
                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }
                });

    }

    /**
     * Get Retrofit object for Api requests
     *
     * @return Retrofit
     */
    private Retrofit getRestAdapter() {

        if (retrofit == null) {


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();

        }

        return retrofit;
    }

    /**
     * Return a OkHttp client
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        // use custom OkHttp client to
        return new OkHttpClient.Builder().build();
    }

}
