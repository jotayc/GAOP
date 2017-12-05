package ugr.lsi.ui.Model;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.opencv.core.Point;

import java.util.ArrayList;

/**
 * Created by JotaC on 17/11/17.
 */

public class SectionArt {
    private int sectID;
    private String comment;
    private PercPoint perCenterPoint;
    private FrameLayout.LayoutParams layoutParams;
    private boolean isSound;


    public boolean isSound() {
        return isSound;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }

    public SectionArt(int sectID, String comment, PercPoint perCenterPoint) {
        this.sectID  = sectID;
        this.comment = comment;
        this.perCenterPoint = perCenterPoint;
        this.layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT);

        this.isSound = false;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PercPoint getPerCenterPoint() {
        return perCenterPoint;
    }

    public void setPerCenterPoint(PercPoint perCenterPoint) {
        this.perCenterPoint = perCenterPoint;
    }

    public FrameLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public int getSectID() {
        return sectID;
    }

    public void setSectID(int sectID) {
        this.sectID = sectID;
    }
}
