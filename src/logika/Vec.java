/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.Collection;
import java.util.ArrayList;

/*******************************************************************************
 * Instance třídy Vec představují věci, které ve hře existují
 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class Vec
{
    //== Datové atributy (statické i instancí)======================================
    private String nazev;
    private String popis;
    private boolean prenositelna;
    private boolean viditelna;
    
    // Kolekce věcí, které jsou uvnitř určité jiné věci.
    private Collection<Vec> uvnitr; 
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  @param nazev          název věci
     *  @param popis          popis, jak věc vypadá
     *  @param prenositelna   true, pokud je možné věc vložit do brašny
     *  @param viditelna      true, pokud je věc umístěna v místnosti, a není ve věci
     */
    public Vec(String nazev, String popis, boolean prenositelna, boolean viditelna)
    {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelna = prenositelna;
        this.viditelna = viditelna;
        this.uvnitr = new ArrayList<>();
    }



    //== Nesoukromé metody (instancí i třídy) ======================================
    /**
     * Metoda vrací název věci.
     * 
     * @return název věci
     */
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Metoda vrací popis věci.
     * 
     * @return popis věci
     */
    public String getPopis() {
        return popis;
    }
    
    /**
     * Metoda vrací přenositelnost věci.
     *
     * @return true, pokud je přenositelná
     */
    public boolean isPrenositelna() {
        return prenositelna;
    }
    
        /**
     * Metoda vrací viditelnost věci.
     *
     * @return true, pokud je viditelná
     */
    public boolean isViditelna() {
        return this.viditelna;
    }
    
        /**
     * Metoda nastavuje, zda je věc v prostoru viditelná
     *
     * @param isViditelna  -true, pokud je viditelná
     */
    public void setViditelna (boolean isViditelna) {
        this.viditelna = isViditelna;
    }
    
    /**
     * Metoda vkládá věc do jiné
     *
     * @param vkladana  -věc, kterou chceme vkládat do jiné věci
     */
    public void vlozVec (Vec vkladana) {
        uvnitr.add(vkladana);
    }
    
    /**
     * Metoda zjišťuje, jestli je věc umístěna v jiné věci.
     *
     * @param nazev     -název hledané věci
     * @return true     -pokud hledaná věc ve věci existuje
     */
    public boolean obsahujeVec(String nazev) {
        boolean obsahuje = false;
        for(Vec aktualni: uvnitr) {
            if(aktualni.getNazev().equals(nazev)) {
                obsahuje = true;
                break;
            }
        }
        return obsahuje;
    }
    
    /**
     * Metoda vrací kolekci věcí, které daná věc obsahuje
     *
     * @return veciUvnitr -kolekce věcí
     */
    public Collection<Vec> getUvnitr() {
        return uvnitr;
    }
    //== Soukromé metody (instancí i třídy) ========================================


}