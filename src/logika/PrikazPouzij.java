/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy PrikazPouzij implementují pro hru příkaz Pouzij.

 *
 * @author    Marie Mikešová
 * @version   ZS 2016/2017
 */
public class PrikazPouzij implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================

    private static final String NAZEV = "použij";
    private Hra hra;
    private HerniPlan herniPlan;

    // vyskytuje se vícekrát
    String nepouzitelne = "Nevím, proč bych měl tohle tady používat";
    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     *  
     *   @param hra   hra řídící celkový průběh (hl. karmu)
     */
    public PrikazPouzij(Hra hra)
    {
        this.hra = hra;
        this.herniPlan = hra.getHerniPlan();
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     * Metoda zpracuje příkaz pouzij, tzn. použije věc z brasny, pokud tam je
     * a je vhodné ji zadaným způsobem použít. V některých případech má vliv na karmu
     * 
     * @param   parametry   používaná věc
     * @return  vypis       vrací informaci o způsobu použití a zda-li je to vůbec možné
     */
    public String proved(String... parametry) {
        String vypis;
        
        if (parametry.length == 0) {
            vypis = "Promiň, ale nevím, co mám použít";
        }
        else {
    
        String pouzivanaVec = parametry[0];
        Prostor aktualniProstor = herniPlan.getAktualniProstor();
        Brasna brasna = herniPlan.getBrasna();
        
             if (brasna.getVec(pouzivanaVec) == null) {           
                 // pokud věc není v brasně
                 vypis = "To tady v brašně ani nemám";
                }
              else {
                 vypis = nepouzitelne;
                 if (pouzivanaVec.equals("staré_noty" ) && aktualniProstor.getNazev().equals("prostranství")) {
                    // mohu použít noty, pokud se nacházím ve prostranství
                     vypis = "\nHIGHWAY TO HELL!! To byla ale legrace! Ty pohledy a zacpaný uši všech andělů!\n"
                                            + "Však já je ještě naučím, co je dobrá muzika!";
                     brasna.smazVec("staré_noty");
                     hra.zhorsitKarmu();
                   }  
                 
                 if (pouzivanaVec.equals("jablko")){
                     vypis = "Zakázané chutná nejlépe!\n"
                           + "Aspoň jsem zahnal trochu hlad";         
                     brasna.smazVec("jablko");
                   }  
                 
                     if (pouzivanaVec.equals("polévka")){
                     vypis = "Trochu studená, ale i tak\n"
                           + "docela dobrá! ... Počkat,"   
                           + "to je červ na tom prázdném"
                           + "talíři? No fuj!";      
                      brasna.smazVec("polévka");
                   }  
            
                 if (pouzivanaVec.equals("sirky" ) && aktualniProstor.getNazev().equals("prostranství") && aktualniProstor.obsahujeVec("rostlina")!=null) {
                     // mohu použít sirky, pokud se nacházím ve prostranství, do kterého jsem vložil rostlinu
                     vypis = "Myslel jsi na to, co já, když jsi uviděl tu kytku tady? \n"
                           + "Nebudu jsem tak zlý, že bych odešel a oni všech těm andílkům nedopřál\n"
                           + "trochu legrace s Marjánkou. Tak jdu zapálit tuhle suchou kytku\n"
                           + "Hm...už to cítím. Hluboký nádech a výdech";
                     brasna.smazVec("sirky");
                     hra.zhorsitKarmu();
                   }
                 
                 if (pouzivanaVec.equals("PC_hra" ) && aktualniProstor.getNazev().equals("Ježíškův_pokoj")) {
                     // mohu použít PC hru, pokud se nacházím v Ježíškově pokoji (je tam totiž nepřenositelný počítač)
                     vypis = "Konečně je tady na nebi trochu zábava!\n";
                   }

                 if (pouzivanaVec.equals("propiska" ) && aktualniProstor.obsahujeVec("červená_kniha")!=null) {
                     // mohu použít propisku, pokud se nacházím někde, kde je Kniha hříchů
                     vypis = "Kniha hříchů... Strana 536. Tady je moje milá tchýně!\n"
                           + "Tady to musíme ještě trochu přibarvit!";
                     brasna.smazVec("propiska");
                     hra.zhorsitKarmu();
                   }   
                 
                 if (pouzivanaVec.equals("zmačkané_noty" ) && aktualniProstor.getNazev().equals("prostranství")) {
                    // mohu použít noty, pokud se nacházím ve prostranství
                     vypis = "\nSTAIRWAY TO HEAVEN!! Bombová písnička! Dokonce i těm andělům\n"
                                            + "se to líbílo. Zase pociťuji ten hřejivý pocit\n"
                                            + "u srdce";
                     brasna.smazVec("zmačkané_noty");
                     if (hra.getKarma()>0){
                     hra.zlepsitKarmu();
                    }
                   }

                 if (pouzivanaVec.equals("černá_kniha")){
                     // po použití Bible se ukončí hra
                     vypis = "Bible! Docela to na mě zapůsobilo. Už nechci být dál hříšný!\n"
                           + "Zase se napravím a budu tady nahoře žít cnostný život\n"
                           + "Sbohem má milá, Josefíno!";
                     brasna.smazVec("černá kniha");
                     hra.setKonecHry(true);
                  } 
         }
    }
        return vypis;
    }

    /**
     * Metoda vrací název příkazu
     * 
     * @return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }

    //== Soukromé metody (instancí i třídy) ========================================

}
