package ugr.lsi.ui.Model;

import java.util.ArrayList;

/**
 * Created by JotaC on 18/11/17.
 */

public class Exposition {

    private int expoID;
    private String name;
    private String description;
    private String date;
    private ArrayList<Art> artList;
    private int imageId;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Exposition(int expoID, String name, String description, String date, int imageId, ArrayList<Art> artList) {
        this.expoID = expoID;
        this.name = name;
        this.description = description;
        this.artList = artList;
        this.imageId = imageId;


    }


    public int getExpoID() {
        return expoID;
    }

    public void setExpoID(int expoID) {
        this.expoID = expoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Art> getArtList() {
        return artList;
    }

    public void setArtList(ArrayList<Art> artList) {
        this.artList = artList;
    }
}
