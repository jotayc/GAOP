package ph.edu.dlsu.cmt;

import java.util.ArrayList;
import java.util.List;

import ugr.lsi.ui.Model.Art;
import ugr.lsi.ui.Model.Exposition;
import ugr.lsi.ui.Model.PercPoint;
import ugr.lsi.ui.Model.Place;
import ugr.lsi.ui.Model.SectionArt;


/**
 * Created by JotaC on 16/10/17.
 */

public class Data {

    public static String INFO_ID = "info";

    public final static int TITLE_CODE = 1;
    public final static int EYE_CODE   = 2;

    public static String info_title = "El personaje principal de la novela es Winston Smith, que trabaja en el Ministerio" +
            " de la Verdad. Su cometido es reescribir la historia, ironizando así el ideal declarado en el nombre del " +
            "Ministerio. Tras años trabajando para dicho Ministerio, Winston Smith se va volviendo consciente de que los " +
            "retoques de la historia en los que consiste su trabajo son sólo una parte de la gran farsa en la que se basa " +
            "su gobierno, y descubre la falsedad intencionada de todas las informaciones procedentes del Partido Único. " +
            "En su ansia de evadir la omnipresente vigilancia del Gran Hermano (que llega inclusive a todas las casas) " +
            "encuentra el amor de una joven rebelde llamada Julia, también desengañada del sistema político; " +
            "ambos encarnan así una resistencia de dos contra una sociedad que se vigila a sí misma.";

    public static String info_eye   = "Big Brother (conocido en castellano como Gran Hermano o Hermano Mayor) " +
            "es un personaje de la novela de George Orwell 1984, y, por tanto, también de las películas del mismo nombre " +
            "basadas en dicha novela. Es el ente que gobierna a Oceanía, según el Ingsoc. Si bien nadie lo conoce, la " +
            "presencia del Hermano Mayor o Gran Hermano es una constante a lo largo de toda la novela, apareciendo " +
            "constantemente a través de las telepantallas en la fuerte propaganda de \"El Partido\", el partido único " +
            "Ingsoc, y en enormes murales en cada rincón de la sociedad descrita por Orwell.";


    public static ArrayList<Place> placeList;
    public static ArrayList<Exposition> expoList;
    public static List<Art> artList;

    static {

        artList = new ArrayList<>();
        placeList = new ArrayList<>();
        expoList  = new ArrayList<>();

        String descrp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam neque lorem, rutrum non " +
                "ullamcorper ac, condimentum et velit. Nam vitae eros ultricies, feugiat urna sed, molestie ante." +
                " In rhoncus diam dui, ac blandit dolor dictum in. Morbi eget porta libero. Sed vel tortor erat. " +
                "Duis in elit at tortor ornare finibus. Nulla dictum magna in orci pellentesque euismod. Nunc tincidunt " +
                "turpis quis erat facilisis egestas. Vestibulum vel lectus finibus, imperdiet purus eget, tempor tortor. " +
                "Nulla metus nisl, auctor et lectus porttitor, efficitur ultricies lectus.";
        ArrayList<SectionArt> sectionsArts = new ArrayList<>();

        //*** Obra 0 ***//
        SectionArt scA1 = new SectionArt(0,"No nos deja indiferentes y llama la atención el " +
                "llamativo sombrero con flor, el pelo que parece recién peinado y ese abrigo con " +
                "pespuntes.", new PercPoint(38,11));
        SectionArt scA2 = new SectionArt(1,"En Mujer que llora, los dientes de la protagonista " +
                "aferran convulsivamente un pañuelo arrugado, que en lugar de tener la apariencia de" +
                " tela blanda está pintado con líneas decididas en zigzag, como si fuese rígido y " +
                "encrespado evocando el dolor de la mujer que protagoniza la obra.", new PercPoint(55,65));

        sectionsArts.add(scA1);
        sectionsArts.add(scA2);

        Art art = new Art( "La mujer que llora", descrp, R.raw.picasso1_test, sectionsArts);

        artList.add(art);

        //**************//

        sectionsArts = new ArrayList<>();
        scA1 = new SectionArt(0," Por fondo, el barco de vela latina, acompañado de la consabida " +
                "banda de gaviotas; amén de la presencia importante del azul del mar, nuestro mar, " +
                "la mar en un éxtasis gozoso.", new PercPoint(80,45));
        scA2 = new SectionArt(1,"No cabe duda que Pablo Picasso logró plantear cuidadosamente sus " +
                "visiones marineras, mediterráneas, cuando fijó la residencia en Vallauris, finca " +
                "La Galloise, empinada sobre una colina mirando al Golfo Juan. "
                ,new PercPoint(32,22));

        sectionsArts.add(scA1);
        sectionsArts.add(scA2);

        art = new Art( "Paisaje mediterraneo", descrp, R.drawable.piccasso2, sectionsArts);
        artList.add(art);
        //*** Obra 1 ***//


        //**************//
        sectionsArts = new ArrayList<>();
        //*** Obra 2 ***//
        scA1 = new SectionArt(0,"Paloma", new PercPoint(25,50));
        scA2 = new SectionArt(1,"Cara", new PercPoint(70,45));

        sectionsArts.add(scA1);
        sectionsArts.add(scA2);

        art = new Art( "Picaso 3", descrp, R.raw.piccaso3_test, sectionsArts);
        artList.add(art);

        //**************//


        //*** Expo 0 ***//
            Exposition exposition = new Exposition(0, "Picasso sus obras",descrp,"07/09/18",
                    R.drawable.flayerpicasso,(ArrayList<Art>) artList);
        //**************//

        //*** Expo 1 ***//
            final Exposition exposition2 = new Exposition(0, "Elementos de tortura",descrp,"07/09/18",
                R.drawable.flayerinquisicion, new ArrayList<Art>());
        //**************//

        //*** Lugar 0 ***//


        expoList.add(exposition);
        Place p1 = new Place(0, "Casa de porras", descrp, "Granada", R.drawable.casaporras, expoList);
        placeList.add(p1);
        //**************//

        //*** Lugar 1 ***//
         p1 = new Place(0, "Palacio de los olvidados", descrp, "Granada", R.drawable.palolvidados,
                 new ArrayList<Exposition>(){{ add(exposition2);}});
        placeList.add(p1);
        //**************//



    }

}
