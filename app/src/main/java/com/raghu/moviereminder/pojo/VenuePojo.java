package com.raghu.moviereminder.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Venue details for a specific movie
 * Created by Raghu on 0002 02/10/2016.
 */
public class VenuePojo implements Parcelable {

    public String Availability;
    public String BestAvailableSeats;
    public String BestBuy;
    public String Class;
    public String CutOffDateTime;
    public String CutOffFlag;

    public String IsAtmosEnabled;
    public double MaxPrice;
    public double MinPrice;

    public String SessionUnpaidQuota;
    public String SessionUnpaidFlag;

    public String ShowDateCode;
    public String ShowDateTime;

    public String VenueCode;
    public String EventCode;
    public String SessionId;

    public String ShowTime;
    public Integer ShowTimeCode;



    @Override
    public String toString() {
        return "VenuePojo{" +
                "Availability='" + Availability + '\'' +
                ", BestAvailableSeats='" + BestAvailableSeats + '\'' +
                ", BestBuy='" + BestBuy + '\'' +
                ", Class='" + Class + '\'' +
                ", CutOffDateTime='" + CutOffDateTime + '\'' +
                ", CutOffFlag='" + CutOffFlag + '\'' +
                ", IsAtmosEnabled='" + IsAtmosEnabled + '\'' +
                ", MaxPrice=" + MaxPrice +
                ", MinPrice=" + MinPrice +
                ", SessionUnpaidQuota='" + SessionUnpaidQuota + '\'' +
                //", SessionUnpaidFlag='" + SessionUnpaidFlag + '\'' +
                ", ShowDateCode='" + ShowDateCode + '\'' +
                ", ShowDateTime='" + ShowDateTime + '\'' +
                ", VenueCode='" + VenueCode + '\'' +
                ", EventCode='" + EventCode + '\'' +
                ", SessionId='" + SessionId + '\'' +
                ", ShowTime='" + ShowTime + '\'' +
                ", ShowTimeCode='" + ShowTimeCode + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Availability);
        dest.writeString(this.BestAvailableSeats);
        dest.writeString(this.BestBuy);
        dest.writeString(this.Class);
        dest.writeString(this.CutOffDateTime);
        dest.writeString(this.CutOffFlag);
        dest.writeString(this.IsAtmosEnabled);
        dest.writeDouble(this.MaxPrice);
        dest.writeDouble(this.MinPrice);
        dest.writeString(this.SessionUnpaidQuota);
        dest.writeString(this.SessionUnpaidFlag);
        dest.writeString(this.ShowDateCode);
        dest.writeString(this.ShowDateTime);
        dest.writeString(this.VenueCode);
        dest.writeString(this.EventCode);
        dest.writeString(this.SessionId);
        dest.writeString(this.ShowTime);
        dest.writeInt(this.ShowTimeCode);
    }

    public VenuePojo() {
    }

    protected VenuePojo(Parcel in) {
        this.Availability = in.readString();
        this.BestAvailableSeats = in.readString();
        this.BestBuy = in.readString();
        this.Class = in.readString();
        this.CutOffDateTime = in.readString();
        this.CutOffFlag = in.readString();
        this.IsAtmosEnabled = in.readString();
        this.MaxPrice = in.readDouble();
        this.MinPrice = in.readDouble();
        this.SessionUnpaidQuota = in.readString();
        this.SessionUnpaidFlag = in.readString();
        this.ShowDateCode = in.readString();
        this.ShowDateTime = in.readString();
        this.VenueCode = in.readString();
        this.EventCode = in.readString();
        this.SessionId = in.readString();
        this.ShowTime = in.readString();
        this.ShowTimeCode = in.readInt();
    }

    public static final Parcelable.Creator<VenuePojo> CREATOR = new Parcelable.Creator<VenuePojo>() {
        @Override
        public VenuePojo createFromParcel(Parcel source) {
            return new VenuePojo(source);
        }

        @Override
        public VenuePojo[] newArray(int size) {
            return new VenuePojo[size];
        }
    };
}
