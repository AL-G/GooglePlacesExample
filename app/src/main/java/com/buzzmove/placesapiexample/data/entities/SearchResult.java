
package com.buzzmove.placesapiexample.data.entities;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResult implements Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = new ArrayList();
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList();
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error_message")
    @Expose
    private String error_message;

    public final static Parcelable.Creator<SearchResult> CREATOR = new Creator<SearchResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        public SearchResult[] newArray(int size) {
            return (new SearchResult[size]);
        }

    }
    ;

    protected SearchResult(Parcel in) {
        in.readList(this.htmlAttributions, (java.lang.Object.class.getClassLoader()));
        in.readList(this.results, (com.buzzmove.placesapiexample.data.entities.Result.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.error_message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public SearchResult() {
    }

    /**
     * 
     * @param results
     * @param status
     * @param error_message
     * @param htmlAttributions
     */
    public SearchResult(List<Object> htmlAttributions, List<Result> results, String status, String error_message) {
        super();
        this.htmlAttributions = htmlAttributions;
        this.results = results;
        this.status = status;
        this.error_message = error_message;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(htmlAttributions);
        dest.writeList(results);
        dest.writeValue(status);
        dest.writeValue(error_message);
    }

    public int describeContents() {
        return  0;
    }

}
