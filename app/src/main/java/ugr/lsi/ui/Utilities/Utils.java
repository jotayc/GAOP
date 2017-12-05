package ugr.lsi.ui.Utilities;

import org.opencv.core.Point;

/**
 * Created by JotaC on 17/11/17.
 *
 * Clase que engloba métodos auxiliares
 */

public class Utils {

    static public double calcPercentage(double num, int perc){

        return (num*perc)/100;
    }

    static public Point getCenter(Point pTl, double pHeight, double pWidth){

        return new Point(pTl.x + (pWidth / 2), pTl.y + (pHeight / 2));
    }

}
