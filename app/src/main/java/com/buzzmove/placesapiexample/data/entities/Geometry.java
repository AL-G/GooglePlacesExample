
package com.buzzmove.placesapiexample.data.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry implements Parcelable
{

    @SerializedName("location")
    @Expose
    private Location location;
    public final static Parcelable.Creator<Geometry> CREATOR = new Creator<Geometry>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Geometry createFromParcel(Parcel in) {
            return new Geometry(in);
        }

        public Geometry[] newArray(int size) {
            return (new Geometry[size]);
        }

    }
    ;

    protected Geometry(Parcel in) {
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geometry() {
    }

    /**
     * 
     * @param location
     */
    public Geometry(Location location) {
        super();
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(location);
    }

    public int describeContents() {
        return  0;
    }

}
