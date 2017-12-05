/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Marie Mikešová
 * @version ZS 2017/2018
 */
public class Prostor{

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private ArrayList<Vec> veci;    // obsahuje seznam všech viditelných předmětů v prostoru
    
    private double posX;
    private double posY;
    
     

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     * @param posX souřadnice X
     * @param posY souřadnice Y
     */
    public Prostor(String nazev, String popis, double posX, double posY) {
        this.nazev = nazev;
        this.popis = popis;
        
        this.posX = posX;
        this.posY = posY;
        
        vychody = new HashSet<>();
        veci = new ArrayList<Vec>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Nacházíš se v prostoru " + nazev + ". Lze jej popsat jako\n"+ popis +"\n"
                + popisVychodu() + "\n"
                + popisVeci();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "Jsou tu tyto VÝCHODY:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }
    
    /**
     * Vrací kolekci obsahující objekty v prostoru. Do ní se nezahrnují věci
     * v jiných věcech
     *
     * @return seznam objektů.
     */
    public ArrayList<Vec> GetVeci() {
        return veci;
    }
    
     /**
     * Vrací textový řetězec, který popisuje viditelné předměty v místnosti, pokud tam nějaké jsou.
     *
     * @return Popis předmětů - názvů
     */
    private String popisVeci() {
        String vracenyText = "";
        if(!veci.isEmpty()) {
         vracenyText = "Nacházejí se zde tyto VĚCI:";
        for (Vec aktualni : veci) {
            vracenyText += " " + aktualni.getNazev();
        }
       }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     * Metoda vloží věc do místnosti.
     *
     * @param vec       -věc, kerou chceme vložit
     * @return true     -pokud se ji podaří vložit
     */
    public boolean vlozVec(Vec vec) {
        veci.add(vec);
        return true;
    }
    
        /**
     * Metoda vrací odkaz na věc, kterou vymaže ze seznamu
     * věcí v místnosti
     * @param nazev - název konkrétní věci
     * @return vrátí vybranou věc
     */
    public Vec vezmiVec(String nazev) {
        Vec vzata = null;
        for (Vec aktualni: veci) {
            if (nazev.equals(aktualni.getNazev()) && aktualni.isPrenositelna()){
                vzata = aktualni;
                veci.remove(aktualni);
                break;
            }
        }
        return vzata;
    }
    
    /**
     * Vrací věc, pokud se nachází v místnosti(ale ne ve věcech)
     *
     * @param nazev     název hledané věci
     * @return pritomna  vrátí odkaz na věc, v případě že se v místnosti nachází. Jinak vrací null.
     */
    public Vec obsahujeVec(String nazev) {
        Vec pritomna = null;
        for (Vec aktualni: veci) {
            if (aktualni.getNazev().equals(nazev)) {
                pritomna = aktualni;
                break;
            }
        }
        return pritomna;
    }
    
     /** Getter, který vrací pozici X prostoru na mapě
     * @return the posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     /** Getter, který vrací pozici Y prostoru na mapě
     * @return - souřadnice Y
     */
    public double getPosY() {
        return posY;
    }


    
}

