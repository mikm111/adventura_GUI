/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
 package logika;

/*******************************************************************************
 * Instance třídy PrikazKarma implementují pro hru příkaz Karma.
 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class PrikazKarma implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================

    private static final String NAZEV = "karma";
    private Hra hra;
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param hra       hra řídící celkový průběh 
     */
    public PrikazKarma(Hra hra) {
        this.hra = hra;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================
    
    /**
     *  Provádí příkaz karma. Informuje hráče o stavu jeho karmy a kolik bodů je potřeba pro
     *  vstup do pekla.
     * 
     *  @return zpráva, informující hráče o karmě
     */ 
    @Override
    public String proved(String... parametry) {
        return "Zatím sis zhoršil karmu o " + hra.getKarma() + " bodů.\n"
             + "Pro vstup do pekla potřebuješ 5.";
    }

    /**
     *  Metoda vrací název příkazu
     *  
     *  @return NAZEV - pojmenování příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
    
    //== Soukromé metody (instancí i třídy) ========================================

}
