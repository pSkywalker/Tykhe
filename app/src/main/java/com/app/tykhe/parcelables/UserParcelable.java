package com.app.tykhe.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.app.tykhe.misc.SavingRateEnum;

public class UserParcelables implements Parcelable {

    public int pk;
    public int user_id;
    public String name;
    public Integer age;
    public SavingRateEnum.savingRate savingRate;
    public double contributionAmount;
    public double interstRate;
    public Integer lengthOfInvestment;
    public Double currentSavings;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(pk);
        dest.writeInt(user_id);
        dest.writeString(name);
        dest.writeValue(age);
        dest.writeString(savingRate.name());
        dest.writeDouble(contributionAmount);
        dest.writeDouble(interstRate);
        dest.writeValue(lengthOfInvestment);
        dest.writeValue(currentSavings);
    }

    protected UserParcelables(Parcel in) {
        pk = in.readInt();
        user_id = in.readInt();
        name = in.readString();
        age = (Integer) in.readValue(Integer.class.getClassLoader());
        savingRate = SavingRateEnum.savingRate.valueOf(in.readString());
        contributionAmount = in.readDouble();
        interstRate = in.readDouble();
        lengthOfInvestment = (Integer) in.readValue(Integer.class.getClassLoader());
        currentSavings = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<UserParcelables> CREATOR = new Creator<UserParcelables>() {
        @Override
        public UserParcelables createFromParcel(Parcel in) {
            return new UserParcelables(in);
        }

        @Override
        public UserParcelables[] newArray(int size) {
            return new UserParcelables[size];
        }
    };
}
