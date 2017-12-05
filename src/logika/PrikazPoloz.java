/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy PrikazPoloz implementují pro hru příkaz Poloz.
 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class PrikazPoloz implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================

    private static final String NAZEV = "polož";
    private HerniPlan herniPlan;
    private Hra hra;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param hra řídící celkový průběh (hl. karmu) 
     */
    public PrikazPoloz(Hra hra) {
        this.herniPlan = hra.getHerniPlan();
        this.hra = hra;
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     *  Provádí příkaz "polož". Pokud hráč nic neuvede nebo věc v brašně není, vypíše chybovou hlášku.
     *  Jinak věc položí na zem v aktuálním prostoru.  
     *  Zvláštní vyjímka udělena pro položení PC_hry v Ježíškově pokoji, protože tato akce má vliv na karmu
     *
     *  @param  parametry   název pokládané věci
     *  @return             zpráva, o úspěšném či neúspěšném položení
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            return "Řekni mi nejdřív, co mám položit!";
        }

        String nazevVeci = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Brasna brasna = herniPlan.getBrasna();
        Vec mazana = brasna.getVec(nazevVeci);

        if (mazana == null) {
            // pokud mazana věc není v brašně.
            return "Tohle tady ani nemám";   
        }
        
         if (nazevVeci.equals("PC_hra") && aktualniProstor.getNazev().equals("Ježíškův_pokoj")) {
              brasna.smazVec("PC_hra");
              hra.zhorsitKarmu();
              return "I Ježíšek si snad někdy může užít trochu zábavy, ne?\n";
              }
        
        else {
            // pokud je věc smazána z brašny, přesune se do aktualního prostoru
            mazana = brasna.smazVec(nazevVeci);
            aktualniProstor.vlozVec(mazana);
            return "Položil jsem věc " + nazevVeci + " tady na zem v prostoru " + aktualniProstor.getNazev();
        }
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return NAZEV - pojmenování prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
    
    //== Soukromé metody (instancí i třídy) ========================================

}
