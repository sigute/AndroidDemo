package com.github.sigute.demo.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Customer implements Parcelable {
    @SerializedName("id")
    private int id;

    @NonNull
    @SerializedName("customerFirstName")
    private String firstName;

    @NonNull
    @SerializedName("customerLastName")
    private String lastName;

    public Customer(int id, @NonNull String firstName, @NonNull String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    //parcelable implementation
    private Customer(Parcel in) {
        this.id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
