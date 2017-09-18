package com.raghu.moviereminder.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * For displaying to user
 * Created by sragh_000 on 018 18/09/2017.
 */

public class VenueDetails implements Parcelable {
    public String VenueCode;
    public String BestAvailableSeats;
    public String BestBuy;
    public String Class;
    public String CutOffFlag;
    public List<String> Availability;

    public List<String> CutOffDateTime;
    public String IsAtmosEnabled;
    public double MaxPrice;

    public double MinPrice;
    public String SessionUnpaidQuota;

    public String SessionUnpaidFlag;
    public List<String> ShowDateCode;

    public List<String> ShowDateTime;
    public String EventCode;
    public List<String> SessionId;

    public List<String> ShowTime;
    public List<Integer> ShowTimeCode;

    public VenueDetails() {
        this.Availability = new ArrayList<>();
        this.CutOffDateTime = new ArrayList<>();
        this.ShowDateCode = new ArrayList<>();
        this.ShowDateTime = new ArrayList<>();
        this.SessionId = new ArrayList<>();
        this.ShowTime = new ArrayList<>();
        this.ShowTimeCode = new ArrayList<>();
    }

    public VenueDetails(VenuePojo vp) {
        this();
        this.BestAvailableSeats = vp.BestAvailableSeats;
        this.BestBuy = vp.BestBuy;
        this.Class = vp.Class;
        this.CutOffFlag = vp.CutOffFlag;

        this.Availability.add(vp.Availability);

        this.CutOffDateTime.add(vp.CutOffDateTime);

        this.IsAtmosEnabled = vp.IsAtmosEnabled;
        this.MaxPrice = vp.MaxPrice;
        this.MinPrice = vp.MinPrice;

        this.SessionUnpaidQuota = vp.SessionUnpaidQuota;
        this.SessionUnpaidFlag = vp.SessionUnpaidFlag;

        this.ShowDateCode.add(vp.ShowDateCode);
        this.ShowDateTime.add(vp.ShowDateTime);

        this.VenueCode = vp.VenueCode;
        this.EventCode = vp.EventCode;

        this.SessionId.add(vp.SessionId);

        this.ShowTime.add(vp.ShowTime);
        this.ShowTimeCode.add(vp.ShowTimeCode);
    }

    public void addVenueDetails(VenuePojo vp) {
        this.Availability.add(vp.Availability);
        this.CutOffDateTime.add(vp.CutOffDateTime);
        this.ShowDateCode.add(vp.ShowDateCode);
        this.ShowDateTime.add(vp.ShowDateTime);
        this.SessionId.add(vp.SessionId);
        this.ShowTime.add(vp.ShowTime);
        this.ShowTimeCode.add(vp.ShowTimeCode);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.VenueCode);
        dest.writeString(this.BestAvailableSeats);
        dest.writeString(this.BestBuy);
        dest.writeString(this.Class);
        dest.writeString(this.CutOffFlag);
        dest.writeStringList(this.Availability);
        dest.writeStringList(this.CutOffDateTime);
        dest.writeString(this.IsAtmosEnabled);
        dest.writeDouble(this.MaxPrice);
        dest.writeDouble(this.MinPrice);
        dest.writeString(this.SessionUnpaidQuota);
        dest.writeString(this.SessionUnpaidFlag);
        dest.writeStringList(this.ShowDateCode);
        dest.writeStringList(this.ShowDateTime);
        dest.writeString(this.EventCode);
        dest.writeStringList(this.SessionId);
        dest.writeStringList(this.ShowTime);
        dest.writeList(this.ShowTimeCode);
    }

    protected VenueDetails(Parcel in) {
        this.VenueCode = in.readString();
        this.BestAvailableSeats = in.readString();
        this.BestBuy = in.readString();
        this.Class = in.readString();
        this.CutOffFlag = in.readString();
        this.Availability = in.createStringArrayList();
        this.CutOffDateTime = in.createStringArrayList();
        this.IsAtmosEnabled = in.readString();
        this.MaxPrice = in.readDouble();
        this.MinPrice = in.readDouble();
        this.SessionUnpaidQuota = in.readString();
        this.SessionUnpaidFlag = in.readString();
        this.ShowDateCode = in.createStringArrayList();
        this.ShowDateTime = in.createStringArrayList();
        this.EventCode = in.readString();
        this.SessionId = in.createStringArrayList();
        this.ShowTime = in.createStringArrayList();
        this.ShowTimeCode = new ArrayList<Integer>();
        in.readList(this.ShowTimeCode, Integer.class.getClassLoader());
    }

    public static final Creator<VenueDetails> CREATOR = new Creator<VenueDetails>() {
        @Override
        public VenueDetails createFromParcel(Parcel source) {
            return new VenueDetails(source);
        }

        @Override
        public VenueDetails[] newArray(int size) {
            return new VenueDetails[size];
        }
    };
}
