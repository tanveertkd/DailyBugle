package com.example.baseplate.dailybugle;

public class pojo {
    private String mHeadline;
    private String mHead;
    private String mSection;
    private String mDate;
    private String mURL;
    public pojo(String headlineID, String headID, String sectionID, String dateID, String urlID) {
        mHeadline = headlineID;
        mHead = headID;
        //mAuthor = authorID;
        mSection = sectionID;
        mDate = dateID;
        mURL = urlID;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getHead() {
        return mHead;
    }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getURL() {
        return mURL;
    }
}
