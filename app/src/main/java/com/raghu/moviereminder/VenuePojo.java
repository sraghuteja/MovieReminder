package com.raghu.moviereminder;

/**
 * Venue details for a specific movie
 * Created by Raghu on 0002 02/10/2016.
 */
class VenuePojo {

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
    public String ShowTimeCode;

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


}
