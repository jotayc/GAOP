package ugr.lsi.ui.Model;

import java.util.ArrayList;

/**
 * Created by JotaC on 18/11/17.
 */

public class Place {

    private int placeID;
    private String name;
    private String description;
    private String city;
    private ArrayList<Exposition> artList;
    private int imageId;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Place(int placeID, String name, String description, String city, int imageId, ArrayList<Exposition> artList) {
        this.placeID = placeID;
        this.name = name;
        this.description = description;
        this.artList = artList;
        this.imageId = imageId;
        this.city = city;


    }


    public int getPlaceId() {
        return placeID;
    }

    public void setPlaceId(int placeID) {
        this.placeID = placeID;
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

    public ArrayList<Exposition> getExpositionList() {
        return artList;
    }

    public void setExpositionList(ArrayList<Exposition> artList) {
        this.artList = artList;
    }
}
