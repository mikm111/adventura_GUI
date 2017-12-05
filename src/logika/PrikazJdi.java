/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůń?? úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Riha, Marie Mikešová
 * @version    ZS 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Hra hra;
    
    /**
    *  Konstruktor třídy
    *  
    *  @param hra řídící celkový průběh (zde kvůli karmě)" 
    */    
    public PrikazJdi(Hra hra) {
        this.plan = hra.getHerniPlan();
        this.hra = hra;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *  
     *  Zvláštní vyjímka byla stanovena pro vstup do prostoru peklo a očistec. Vstup do 
     *  pekla není možný, dokud karma hráče nedosáhne hodnoty 5ti bodů. Při vstupu do očistce
     *  karma získá hodnotu 0.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá!";
        }
        
        if (smer.equals("očistec")) {
            hra.ocisteniKarmy();
            plan.setAktualniProstor(sousedniProstor);
            return "TABULA RASA! Tvoje duše byla očištěna od všech hříchů\n"
                 + sousedniProstor.dlouhyPopis();
        }
        
        if (smer.equals("peklo") && hra.getKarma()>=5) {
            hra.setHracVyhral(true);
            hra.setKonecHry(true);
            plan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
        }
        
        if (smer.equals("peklo") && hra.getKarma()<5) {
            return "Nemůžeš jít do pekla! Tvá duše je příliš čistá!";
        }
        
        else {
            plan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
