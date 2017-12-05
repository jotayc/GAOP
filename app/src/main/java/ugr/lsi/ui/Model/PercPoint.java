package ugr.lsi.ui.Model;

import ugr.lsi.ui.Utilities.Utils;
import org.opencv.core.Point;

/**
 * Created by JotaC on 17/11/17.
 */

public class PercPoint {

    private int perX;
    private int perY;

    public PercPoint(int perX, int perY) {
        this.perX = perX;
        this.perY = perY;
    }

    public int getPerX() {
        return perX;
    }

    public void setPerX(int perX) {
        this.perX = perX;
    }

    public int getPerY() {
        return perY;
    }

    public void setPerY(int perY) {
        this.perY = perY;
    }

    /**
     * Funcion que transforma un punto desde porcentajes en referencia al punto pasado
     * @param ptl Punto top Left a tener de referencia
     * @param width ancho sobre el que calculará el porcentaje
     * @param height alto sobre el que calculará el porcentaje
     * @return devuelve el punto en unidades local ( pixeles dependiendo de densidad de movil) a
     * en funcion del punto
     */
    public Point percToPixelFromPoint(Point ptl, double width, double height ){
        return new Point( ptl.x + Utils.calcPercentage(width,this.getPerX()),
                          ptl.y + Utils.calcPercentage(height,this.getPerY()));

    }



}
