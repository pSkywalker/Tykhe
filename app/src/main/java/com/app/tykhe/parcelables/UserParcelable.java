package com.app.tykhe.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;

public class UserParcelable implements Parcelable {

    public int pk;
    public int user_id;
    public String name;
    public Integer age;
    public SavingRateEnum.savingRate savingRate;
    public double contributionAmount;
    public double interstRate;
    public Integer lengthOfInvestment;
    public Double currentSavings;


    public UserParcelable(User entityUser) {
        // Map properties from entityReminder to this object
        this.savingRate = entityUser.savingRate;
        this.contributionAmount = entityUser.contributionAmount;
        this.interstRate = entityUser.interstRate;
        this.lengthOfInvestment = entityUser.lengthOfInvestment;
        this.currentSavings = entityUser.currentSavings;
    }
    public User getUserEntityFromParcelable(){
        User user = new User();

        user.savingRate = this.savingRate;
        user.contributionAmount = this.contributionAmount;
        user.interstRate = this.interstRate;
        user.lengthOfInvestment = this.lengthOfInvestment;
        user.currentSavings = this.currentSavings;

        return user;
    }


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


    protected UserParcelable(Parcel in) {
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

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };
}
