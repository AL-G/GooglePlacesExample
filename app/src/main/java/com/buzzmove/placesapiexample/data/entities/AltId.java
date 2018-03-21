
package com.buzzmove.placesapiexample.data.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AltId implements Parcelable
{

    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("scope")
    @Expose
    private String scope;
    public final static Parcelable.Creator<AltId> CREATOR = new Creator<AltId>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AltId createFromParcel(Parcel in) {
            return new AltId(in);
        }

        public AltId[] newArray(int size) {
            return (new AltId[size]);
        }

    }
    ;

    protected AltId(Parcel in) {
        this.placeId = ((String) in.readValue((String.class.getClassLoader())));
        this.scope = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public AltId() {
    }

    /**
     * 
     * @param scope
     * @param placeId
     */
    public AltId(String placeId, String scope) {
        super();
        this.placeId = placeId;
        this.scope = scope;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(placeId);
        dest.writeValue(scope);
    }

    public int describeContents() {
        return  0;
    }

}
