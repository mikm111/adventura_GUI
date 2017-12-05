/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Subject;
import utils.Observer;

/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny prostory, propojuje je vzájemně pomocí východů
 * a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 * 
 *  vytváří objekty hry a umisťuje je do prostorů či jiných objektů v prostoru.
 *  Některé lze vložit do inventáře - brašny
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Marie Mikešová
 * @version    ZS 2017/2018
 */
public class HerniPlan implements Subject {
    
    private Prostor aktualniProstor;
    private Brasna brasna;
    private Prostor prostor;
    private Vec vec;
    private static final String VITEZNY_PROSTOR = "peklo"; //opakující se termín
    
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Vytváří se i inventář hry - brašna
     */
    public HerniPlan() {
        zalozProstoryHry();
        brasna = new Brasna();
    }
    /**
     *  Vytváří jednotlivé prostory, jejich popisy a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví prostranství.
     *  Vytváří objekty a vkládá je do místností či do jiných objektů
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor dilna = new Prostor("dílna","nejoblíbenější místo každého andělského kutila v celém nebeském království.\n"
                                             + "I Přemek Podlaha by tu zdejšímu mistorvi tuto dílnu záviděl.",200,7);
        Prostor zahrada = new Prostor("zahrada","místo plné barev, samý strom a samá rostlina, kam se jen podíváš.\n"
                                             + "Stojíš zde omámen vůněmi nebeských květin", 400,7);
        Prostor prostranstvi = new Prostor("prostranství","místo s velkým kamenným stolem a malou knihovnou.\n"
                                             + "Ale především je tu všude slyšet hluk - veselý zpěv malých andílků i moudrosti\n"
                                            + "místních vzdělaných staříků", 220,70);
        Prostor bozskaKancelar = new Prostor("božská_kancelář","nejváženější místnost na celém nebi, ve které panuje zde\n"
                                             + "čistě pracovní nálada. Nejvyšší z nejvyšších tu ale zrovna není. Asi bys tady\n"
                                             + "neměl co dělat",70,143);
        Prostor kuchyn = new Prostor("kuchyň","poměrně prostě vybavené místo. Největší gurmáni by si tu ale asi nepochutnali.\n"
                                             + "Tak buďme k sobe upřímní, není pečené maso nad pekelným ohněm ten hlavní\n"
                                             + "důvod, proč jdu do pekla? A hle, na plotně něco je.",435,97);
        Prostor jeziskuvPokoj = new Prostor("Ježíškův_pokoj","opravdu moc pěkný dětský pokojík. To se teda Pán Bůh plácl přes kapsu.\n"
                                             + "A všude naklizeno, čisto. Chudák se ale musí dost nudit, kdy tu má na hraní\n"
                                             + "jen toho plyšovýho beránka.", 15,40);
        Prostor scesti = new Prostor("scestí","rozcestí na scestí. Sem by žádný příkladný anděl rozhodně neměl být\n"
                                             + "Pečlivě si rozmysli další skutky! ...Tady to už vypadá fakt divně a podezřele.",360,200);
        Prostor ocistec = new Prostor("očistec","místo, které zbaví každého jeho hříchů. Modli se, kaj a pros o odpuštění Boží!", 60,270);
        Prostor peklo = new Prostor(VITEZNY_PROSTOR,"nejtemnější místo všech míst. Silný zápach cítíš a ohně pekelné tu něco smaží", 435,270);
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        dilna.setVychod(zahrada);
        zahrada.setVychod(dilna);
        zahrada.setVychod(kuchyn);
        zahrada.setVychod(prostranstvi);
        kuchyn.setVychod(zahrada);
        kuchyn.setVychod(prostranstvi);
        prostranstvi.setVychod(zahrada);
        prostranstvi.setVychod(kuchyn);
        prostranstvi.setVychod(jeziskuvPokoj);
        prostranstvi.setVychod(bozskaKancelar);
        jeziskuvPokoj.setVychod(bozskaKancelar);
        jeziskuvPokoj.setVychod(prostranstvi);
        prostranstvi.setVychod(scesti);
        bozskaKancelar.setVychod(prostranstvi);
        bozskaKancelar.setVychod(jeziskuvPokoj);
        scesti.setVychod(prostranstvi);
        scesti.setVychod(peklo);
        scesti.setVychod(ocistec);
        ocistec.setVychod(scesti);
                
        aktualniProstor = prostranstvi;  // hra začíná ve prostranství
        
        // vytvoříme věci
        Vec kladivo = new Vec("kladivo", "Poměrně opotřebované kladivo se zakulacenými rohy patřící místnímu opraváři", true, true);
        Vec zebrik = new Vec("žebřík", "Dřevěný žebřík s kovovým úchytem", true, true);
        Vec konev = new Vec("konev", "Plechová konev s kropítkem a s nápisem: vlastnictví archanděla Gabriela", true, true);
        Vec sirky = new Vec("sirky", "Krabička sušických zápalek z roku 1973 obsahující jednu sirku", true, true); 
        Vec plotna = new Vec("plotna", "Čistá, nová plotna, kterou dostal k narozeninám anděl Rauel", false, true);
        Vec polevka = new Vec("polévka", "Hm, vypadá celkem chutně.... a ještě je horká", true, false);
        Vec pracovniStul = new Vec("pracovní_stůl", "Dřevěný stůl. V těch zásuvkách asi něco bude", false, true);
        Vec klic = new Vec("klíč", "Zvláštně zahnutý, trochu rezavý  klíč", true, false);
        Vec cervenaKniha = new Vec("červená_kniha", "Pěkně silná a hodně používaná kniha. Tohle všechno že\n"
                                                    + "vyvedla Dorota Máchalová? To vypadá na knihu hříchů.", true, false);
        Vec propiska = new Vec("propiska", "Běžná modrá propiska. Překvapivě píše.", true, false);
        Vec tuzka = new Vec("tužka", "Obyčejná dřevěná tužka. Ale abych s ní mohl něco napsat,\n"
                                     + "potřeboval bych ořezávátko", true, true);
        Vec rostlina = new Vec("rostlina", "Hle, tímhle v menší podobě přece Josefína zdobila naše okna.\n"
                                          + "Asi metr dlouhá, úplně seschlá květinka s listy s pilovitým okrajem\n"
                                          + "O tuhle rostlinku se tu evidentně nikdo nestaral",  true, true);
        Vec jablon = new Vec("jabloň", "Až hřích pohledět na tuto jabloň s velkými červenými jablky\n"
                                      + "Vidím tam z dálky jedno krásné červené jablíčko,\n"
                                      + "ale nedosáhnu na něj", false, true);
        Vec jablko = new Vec("jablko", "Krásné, červené jablíčko. K nakousnutí!", true, false);
        Vec brana = new Vec("pekelná_brána", "Velká zlatá brána k mému vysvobození a zatracení zároveň!\n"
                                           + "Bohužel, zamčená!", false, true);
        Vec stareNoty = new Vec("staré_noty", "Noty neumím, ale je tu i text...\n"
                                                                           + "Livin' easy\n"
                                                                           + "Livin' free\n"
                                                                           + "Season ticket on a one way ride"
                                                                           +  "...♪ ♫ ♪"
                                                                           +  "I'm on the highway to hell!", true, true);
        Vec PCHra = new Vec("PC_hra", "Píše se tu... Counter Strike: Global Offensive", true, true);
        Vec knihovna = new Vec("knihovna", "Malá dřevěná knihovnička. Ale možná se tu najde pár zajímavých\n"
                                         + "knižních titulů", false, true);
        Vec bilaKniha = new Vec("bílá_kniha", "Tohle vypadá jako docela naučný druh literatury.\n"
                                            + "Zřejmě povinná četba. Rozděleno na části:\n"
                                            + "Peklo, Očistec a Ráj. Vypadá to na dílo Danta Alighieriho.", true, false);
        Vec cernaKniha = new Vec("černá_kniha", "Kniha knih! Písmo svaté! Tohle je Bible.\n"
                                            + "Obsahuje Starý i Nový zákon.", true, false);
        Vec zmackaneNoty = new Vec("zmačkané_noty", "Zkusím to rozluštit...\n"
                                                                           + "There's a lady who's sure\n"
                                                                           + "all that glitters is gold.\n"
                                                                           + "And she's buying a stairway to heaven."
                                                                           +  "...♪ ♫ ♪", true, false);
        Vec plysak = new Vec("plyšák", "Ježíškův plyšový beránek - jeho oblíbená hračka", true, true);
        Vec pocitac = new Vec("počítač", "Hm.. tahle bedna nevypadá na nejnovější model\n"
                                       + "A běží to na Windows 95! To byly časy! Ale nechápu,\n"
                                       + "proč to tady má, když tu nemá připojení k internetu", false, true);
                                         
        // vložíme věci do prostorů/věcí
        dilna.vlozVec(kladivo);
        dilna.vlozVec(zebrik);
        dilna.vlozVec(konev);
        dilna.vlozVec(sirky);
        kuchyn.vlozVec(plotna);
        plotna.vlozVec(polevka);
        bozskaKancelar.vlozVec(pracovniStul);
        pracovniStul.vlozVec(klic);
        pracovniStul.vlozVec(cervenaKniha);
        pracovniStul.vlozVec(propiska);
        zahrada.vlozVec(rostlina);
        zahrada.vlozVec(jablon);
        jablon.vlozVec(jablko);
        scesti.vlozVec(brana);
        scesti.vlozVec(stareNoty);
        scesti.vlozVec(PCHra);
        prostranstvi.vlozVec(tuzka);
        prostranstvi.vlozVec(knihovna);
        knihovna.vlozVec(bilaKniha);
        knihovna.vlozVec(cernaKniha);
        knihovna.vlozVec(zmackaneNoty);
        jeziskuvPokoj.vlozVec(plysak);
        jeziskuvPokoj.vlozVec(pocitac);
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
        /**
     *  Getter vrací odkaz na věc
     *
     *@return     věc
     */
        public Vec getVec() {
        return vec;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       notifyAllObservers();
    }

    /**
     *  Metoda vrací seznam vecí v brašně, které má postava u sebe
     * 
     *  @return    brasna    brašna - inventar věcí
     */
    public Brasna getBrasna() {
        return this.brasna;
    }
    
     /**
     *  Vrátí informaci o tom, zda-li hráč vyhrál.
     *  
     *  @return  vrátí true, pokud se postava dostala do místnosti VÍTĚZNÝ_PROSTOR.
     */ 
    public boolean hracVyhral(){
        return aktualniProstor.getNazev().equals(VITEZNY_PROSTOR);
       }
    
     /**vytvoření nového observeru
     @param observer - samotný listener, pozorovatel*/
    
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
