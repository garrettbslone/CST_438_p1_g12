/*
 * author: Garrett
 * date: 2/10/2021
 * project: Project1
 * description:
 */
package com.group12.project1;

import java.util.Objects;

/**
 * Object to represent the User's search preferences to be used
 * when accessing the GitHub jobs api.
 */
public class SearchPreferences {
    private String mLang;
    private String mLoc;
    private boolean mIsFullTime;

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
