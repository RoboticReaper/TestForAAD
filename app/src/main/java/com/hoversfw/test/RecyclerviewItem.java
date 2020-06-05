package com.hoversfw.test;

public class RecyclerviewItem {
    private int imageResource;
    private String title;
    private String description;

    public RecyclerviewItem(int mimageResource, String mtitle, String mdescription){
        imageResource=mimageResource;
        title=mtitle;
        description=mdescription;
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}
