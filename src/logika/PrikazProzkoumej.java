/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;

/*******************************************************************************
 * Instance třídy PrikazProzkoumej implementují pro hru příkaz Prozkoumej.
 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class PrikazProzkoumej implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "prozkoumej";
    private HerniPlan hPlan;
    private Prostor prostor;
    private List<Observer> listObserveru = new ArrayList<Observer>();
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor zpracovávající příkaz prozkoumej
     * @param plan - herní plán, ve kterém se bude ve hře "chodit"
     */
    public PrikazProzkoumej(HerniPlan plan)
    {
      this.hPlan = plan;
      this.prostor = prostor;
    }

    PrikazProzkoumej() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
        /**
     *  Zpracovává příkaz "prozkoumej". Prozkoumává věci - jejich popis a skutečnost,
     *  zda-li neobsahují věci jiné. Dále je možné díky této metodě získat seznam věcí
     *  v brašně, pokud tam nějaké jsou. Dále vypíše chybové hlášky, pokud nebylo zadáno, se věci
     *  co se má hledat, nebo pokud se věc v prostoru nenachází. 

     *  @param parametry    název prozkoumávané věci
     *  @return             zpráva, o popisu a obsahu věcí
     *                      či seznam věcí v inventáři nebo chybová hláška
     */ 
   public String proved(String... parametry) {
        if (parametry.length < 1) {
            return "Nevím, co mám prozkoumat";
        }

        String nazev = parametry[0];
        
        if (nazev.equals("brašna")){
            
            if (hPlan.getBrasna().getVeci().isEmpty()) {
            // pokud je batoh prázdný
            return "Zatím jsem nikde nic nevzal";  
            }
            return "V brasně mám" + hPlan.getBrasna().getVeci();
        }
        
        Vec vec = hPlan.getAktualniProstor().obsahujeVec(nazev);
        String text;
        text = "";
        
        if (vec == null) {
            text = "Věc '" + nazev + "' tu fakt nevidím";
       }
       else {
            if (vec.getUvnitr().isEmpty())
                {
                   text = vec.getPopis();
                } 
            else{   
               String nalezenePredmety;
               nalezenePredmety = "";
              
                for (Vec aktualni : vec.getUvnitr()) {
                aktualni.setViditelna(true);
                hPlan.getAktualniProstor().vlozVec(aktualni);
                nalezenePredmety += " " + aktualni.getNazev();
               }
               
               vec.getUvnitr().clear();
               text =  vec.getPopis() + "\nA hele, našel jsem tu tohle:" + nalezenePredmety;
               }
           }
        return text;
    }
    
        /**
     *  Metoda vrací název příkazu 
     *  
     *  @return NAZEV - pojmenování prikazu
     */
	public String getNazev() {
	    return NAZEV;
	}

    //== Soukromé metody (instancí i třídy) ========================================



}
