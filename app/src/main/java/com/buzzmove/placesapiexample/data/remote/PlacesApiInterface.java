package com.buzzmove.placesapiexample.data.remote;

import com.buzzmove.placesapiexample.data.entities.SearchResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Api interface for Places Api
 *
 * A Text Search request is an HTTP URL of the following form:
 * https://maps.googleapis.com/maps/api/place/textsearch/json?query=123+main+street&key=YOUR_API_KEY
 */
public interface PlacesApiInterface {

    // Get the next {quantity} of launches
    @GET("textsearch/json")
    Single<SearchResult> searchPlaces(@Query("query") String search, @Query("key") String key);

}
