/*
 * author: Garrett
 * date: 2/10/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import java.util.HashMap;
import java.util.Objects;

/**
 * Object to represent the User's search preferences to be used
 * when accessing the GitHub jobs api.
 */
public class SearchPreferences {
    private String mLang;
    private String mLoc;
    private Boolean mIsFullTime;

    public SearchPreferences (String lang, String loc, boolean time) {
        mLang = lang;
        mLoc = loc;
        mIsFullTime = time;
    }

    public String getLang () {
        return mLang;
    }

    public void setLang (String lang) {
        mLang = lang;
    }

    public String getLoc () {
        return mLoc;
    }

    public void setLoc (String loc) {
        mLoc = loc;
    }

    public boolean isFullTime () {
        return mIsFullTime;
    }

    public void setFullTime (boolean fullTime) {
        mIsFullTime = fullTime;
    }

    /**
     * Turns the SearchPreferences object into the proper QueryMap
     * needed for the API.
     * @return qMap a HashMap containing the parameters to add to the query string
     */
    public HashMap<String, String> toQueryMap() {
        HashMap<String, String> qMap = new HashMap<>();

        qMap.put("description", mLang);
        qMap.put("location", mLoc);
        qMap.put("full_time", mIsFullTime.toString());

        return qMap;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPreferences that = (SearchPreferences) o;
        return mIsFullTime == that.mIsFullTime &&
                Objects.equals(mLang, that.mLang) &&
                Objects.equals(mLoc, that.mLoc);
    }

    @Override
    public int hashCode () {
        return Objects.hash(mLang, mLoc, mIsFullTime);
    }
}
