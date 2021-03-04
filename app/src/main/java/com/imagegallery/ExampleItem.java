package com.imagegallery;

public class ExampleItem {
    private String mImgUrl, mCreator;
    private int mLikes;

    public ExampleItem(String mImgUrl, String mCreator, int mLikes) {
        this.mImgUrl = mImgUrl;
        this.mCreator = mCreator;
        this.mLikes = mLikes;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public String getmCreator() {
        return mCreator;
    }

    public int getmLikes() {
        return mLikes;
    }
}
