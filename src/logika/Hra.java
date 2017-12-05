/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;
import utils.Subject;

/**
 * Třída Hra - třída představující logiku adventury.
 * 
 * Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan,
 * která inicializuje místnosti hry a vytváří seznam platných příkazů a instance tříd
 * provádějící jednotlivé příkazy. Vypisuje uvítací a ukončovací text hry. Také
 * vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Marie Mikešová
 * @version    ZS 2017/2018
 */

public class Hra implements IHra, Subject{
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;      // změna na true, při výhře i prohře
    private int body = 0;                  //původní stav karmy
    private boolean vyhral = false;  
     private List<Observer> listObserveru = new ArrayList<Observer>();


    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();

        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(this));
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(this));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(this));
        platnePrikazy.vlozPrikaz(new PrikazKarma(this));
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Jožo, ačkoliv milionář , zůstal po celý svůj život velice ctnostný\n"
             + "a dobrotivý člověk. Měl jen jedinou chybu, byl zamilovaný do své zlé\n"
             + "a peněz chtivé choti Josefíny. Jednoho dne si však životem znuděná\n"
             + "Josefína od Joži vyžádala dovolenou na žraločí pláži New Smyrna,\n"
             + "která byla i jejich poslední.\n\n"
             + "Zatímco Josefínu pohltil pekelný žár, Jožova naivní a čistá duše pocítila\n"
             + "lehkost nebeských křídel. Během pobytu v ráji však Jožo není šťastný,\n"
             + "andělské chóry mu najednou připadají nudné a andělská křídla ho tíží, místo\n"
             + "aby nadnášela. Především mu ale někdo schází.\n\n"
             + "Pokud jsi stejně zmatený jako Joža a nevíš, co si počnout s jeho životem\n"
             + "zadej 'napoveda' a možná ti pomůže duch svatý.\n\n"
             + herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        if (vyhral) {
        return "Obklopil tě šedý dým, tvé uši krvácí ze všeho křiku, pláče a skřípění zubů\n"
             + "kolem a tvé paže právě pocítily hřejivý dotek plamenů ohnivého jezera. Gratuluji.\n"
             + "Vyhrál jsi. Tvá černá duše je navždy zaprodána peklu. Ohlédneš se zpátky k pekelné\n"
             + "bráně a spatříš Josefínu, jak prochází očistcem kajíc se ze svých hříchů. Zatímco\n"
             + "ty stále klesáš, ona míří vzhůru.\n";
            }
        else {
        return "Tvá mise skončila neúspěšně. Navěky věků (nebo-li do té doby, než začneš novou hru a vyhraješ)\n"
              + "bude tvým domovem ráj nebeský! Buď pochválena tvá čistá duše. A hle, to je překvapení! Támhle\n"
              + "vidíš Josefínu! Nakonec se obrátila k Bohu, vzdala se svých hříchů a dostala se do nebe.\n\n";
        }    
  
    }
   
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }

        /**
     *  Vrátí informaci o tom, zda-li hráč vyhrál.
     *  
     *  @return  vrátí true, pokud se postava dostala do místnosti "peklo".
     */
    public boolean getHracVyhral() {
        return this.vyhral;
    }
        
    /**
     *  Nastaví stav informující o výhře.
     *  
     *  @param vyhral  -nastaví true pokud hráč vyhrál (vstoupil do místnosti "peklo")
     */
    public void setHracVyhral(boolean vyhral) {
        this.vyhral = vyhral;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */

     public String zpracujPrikaz(String radek) {
                            
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
       
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
            notifyAllObservers();
            
            if(herniPlan.hracVyhral()) {
            konecHry = true;
            }
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní IPrikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
    
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return herniPlan;
     }
         
    /**
     * Metoda zhorší karmu (zvýší hodnotu karmy o 1 bod).
     */
    public void zhorsitKarmu() {
        this.body++;
        notifyAllObservers();
    }
    
    /**
     * Metoda zlepší karmu (sníží její hodnotu o 1 bod).
     */
    public void zlepsitKarmu() {
        this.body--;
        notifyAllObservers();
    }
    
            /**
     * Metoda nastaví hodnotu karmy na 0.
     */
    public void ocisteniKarmy() {
        this.body = 0;
        notifyAllObservers();
    }
    
    /**
     * Metoda vrací informaci o stavu karmy
     * 
     * @return  počet bodů/hříchů
     */
    public int getKarma() {
        return this.body;
        
    }
    
        public int getKarmaZbyvajici() {
        int zbyvajiciBody = 5 - this.body;
        return zbyvajiciBody;
        
    }
    
    

       @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }
    
     /**odstranění observeru
     @param observer - samotný listener, pozorovatel*/
    
    @Override
    public void deleteObserver(Observer observer) {
        listObserveru.remove(observer);
    }
    /**upozornění observerů na změny*/
    
    @Override
    public void notifyAllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }

}
}
