/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy PrikazVezmi implementují pro hru příkaz Vezmi.
 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class PrikazVezmi implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    
    private static final String NAZEV = "vezmi";
     private Hra hra;
    private HerniPlan herniPlan;
    private Brasna brasna;
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param  hra řídící celkový průběh (zde hlavně kvůli karmě)
     */
    public PrikazVezmi(Hra hra)
    {
        this.hra = hra;
        this.herniPlan = hra.getHerniPlan();
        this.brasna = herniPlan.getBrasna();
        
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda realizuje vkládání věcí do inventáře
     * Pokud nebylo zadáno, co se má vložit, věc v místnosti není, nebo není přenositelná
     * či je už brašna plná, neprovede se vložení.
     * 
     * Speciálně odlišeny podmínkami jsou věci, které po sebrání mění karmu
     * 
     * @param   parametry   braná věc
     * @return              výpis informující o tom, zda-li byla věc sebrána
     */    
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            return "Co mám vzít?";
        }

        String nazevVeci = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        String text;
        text = "";

       if (aktualniProstor.obsahujeVec(nazevVeci) == null) {
            // pokud v aktuálním prostoru věc není
            text =  "Asi jsem slepý, ale věc '" + nazevVeci + "' tu fakt nevidím";
       }
       else{
          Vec sbirana = aktualniProstor.vezmiVec(nazevVeci);
          if (sbirana == null) {
           // pokud věc není přenositelná
           text =  "Mysli taky trochu na moje stará záda! Věc '" + nazevVeci + "' fakt neunesu";
                              }
         else {
             if (brasna.vlozVec(sbirana) == null) {
                  // vrátí věc do prostoru, pokud je brasna plná
                  aktualniProstor.vlozVec(sbirana);
                  text =  "Ty seš takový vetešník! Tohle už tam fakt dávat nebudu. \n"
                          + "Ještě bych si tuhle krásnou brašnu proděravěl! Hybaj něco vyhodit.";
                  } 
             else {
                if (nazevVeci.equals("jablko")){
                    if (aktualniProstor.obsahujeVec("žebřík")==null){ 
                       aktualniProstor.vlozVec(sbirana);
                       herniPlan.getBrasna().smazVec("jablko");
                       //vymazat z brašny
                       text =  "Je moc vysoko, nedosáhnu na něj. A přeborník v lezení po stromech zrovna nejsem.";
                     }
                      else {
                        // pokud se podaří věc vložit do brasny
                        text =  "Věc " + nazevVeci + " jsem hodil do batohu";
                        hra.zhorsitKarmu();
                     } 
                } 
                else
                    {if (nazevVeci.equals("plyšák")){
                         text =  "Co teď bude chukák Ježíšek dělat? Ty jsi ale krutý!";
                         hra.zhorsitKarmu();
                     }
                     else {
                        // pokud se podaží věc vložit do brasny
                        text =  "Věc " + nazevVeci + " jsem hodil do batohu";
           
                     } 
                    }
                }
           } 
        }
       return text;
    }
   
    /**
     * Metoda vrací název příkazu
     * 
     * @return  název příkazu
     */
    public String getNazev () {
        return NAZEV;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
