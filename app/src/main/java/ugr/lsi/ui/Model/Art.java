package ugr.lsi.ui.Model;

import java.util.ArrayList;

/**
 * Created by JotaC on 17/11/17.
 */

public class Art {


    private String  name;
    private String  description;
    private int     artId;

    private ArrayList<SectionArt> sectionArts;

    public Art(String name, String description, int artId, ArrayList<SectionArt> sectionArts) {
        this.name = name;
        this.description = description;
        this.artId = artId;
        this.sectionArts = sectionArts;
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

    public int getArtId() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public ArrayList<SectionArt> getSectionArts() {
        return sectionArts;
    }

    public void setSectionArts(ArrayList<SectionArt> sectionArts) {
        this.sectionArts = sectionArts;
    }

    public SectionArt getSectionArtAt(int position){
        return this.sectionArts.get(position);
    }


}
