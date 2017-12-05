/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
 
 package logika;
 import java.util.List;
 import java.util.ArrayList;
import utils.Observer;
import utils.Subject;

/*******************************************************************************
 * Instance třídy Brasna představují kolekci věcí, které si uživatel přidal 
 * do inventáře
 *
 * @author   Marie Mikešová
 * @version  ZS 2016/2018
 */
public class Brasna implements Subject
{
    //== Datové atributy (statické i instancí)======================================
    
    private static final int MAXIMUM = 5;    // Maximální počet věcí v brasně
    private List<Vec> obsah;                 // Seznam věcí v brasně
        private IHra hra;
           private List<Observer> listObserveru = new ArrayList<Observer>();

    
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public Brasna() {
        obsah = new ArrayList<Vec>();
       
    }
    
    //== Nesoukromé metody (instancí i třídy) ======================================
    
    /**
     * Metoda na vkládání věci do inventáře.
     * 
     * @param  vkladana    přidávaná věc
     * @return  vec    vrátí přidávanou věc, pokud je vložení úspěšné, jinak null.
     * 
     * 
     */
    

    
    public Vec vlozVec(Vec vkladana) {
        if (neniPlno()) {    
            
            obsah.add(vkladana);
            notifyAllObservers();
            return vkladana;
            
           
        }
       
        return null;
    }
    
    /**
     * Zjišťuje, zda-li se dá do brašny ještě něco vložit. Zda-li není už plná.
     * 
     * @return  true  není-li plná.
     */
     public boolean neniPlno() {
        if (obsah.size() < MAXIMUM) {
            return true;   
        }        
        return false;
    }
    
    /**
     * Zjišťuje, zda se určitá věc nachází v inventáři/brašně.
     *  
     * @param   hledana    hledana vec
     * @return  true       pokud se věc v brasně nachází, jinak vrátí false
     */
    public boolean obsahujeVec(String hledana) {
        for (Vec aktualni: obsah) {
            if (aktualni.getNazev().equals(hledana)) {
                 return true;
            }
        }
        return false;
    }
    
    /**
     * Vrací seznam věcí v brasně.
     * 
     * @return   veci      seznam všech věcí v inventáři
     */
    public String getVeci() {
        String veci = "";
        for (Vec aktualni: obsah) {
            veci += " " + aktualni.getNazev();
        }
        return veci;
    }
    
    /**
     * Metoda najde věc, na kterou chceme odkaz dle jejího názvu.
     * 
     * @param nazev      název věci, kterou chceme najít
     * @return hledana   odkaz na nalezenou věc, pokud taková věc není, vrátí null
     */
    public Vec getVec(String nazev) {
        Vec hledana = null;
        for (Vec aktualni: obsah) {
            if(aktualni.getNazev().equals(nazev)) {
                hledana = aktualni;
                break;
            }
        }
        return hledana;
    }
    
    /**
     * Maže věci z inventáře
     * 
     * @param   mazana      odstraňovaná věc
     * @return  smazana     odstraněná věc, nebo null, pokud žádná taková v brašně nebyla
     */
    public Vec smazVec (String mazana) {
        Vec smazana = null;
        for(Vec aktualni: obsah) {
            if(aktualni.getNazev().equals(mazana)) {

                smazana = aktualni;
                 
                obsah.remove(aktualni);
                notifyAllObservers();
                break;
            }
        }
        return smazana;
    }
    
    /**
     * Metoda vrací maximální počet věcí, které lze přidat do inventáře.
     * 
     * @return  počet věcí
     */
    public int getMaxVeci() {
        return MAXIMUM;
    }
    
    
        /**
     * Getter, který vrací seznam všech věcí v brašně
     * @return  obsah - samotný list
     */
    public List<Vec> getSeznamVeci(){
    return obsah;
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

    

