/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package utils;

/**
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */

/**
 * Rozhraní které musí implementovat třídy, na jejichž základu je UI založeno
 a jejichž některé metody jsou observery poslouchány*/

public interface Subject {
    /**vytvoření nového observeru
     @param observer - samotný listener, pozorovatel*/
    void registerObserver(Observer observer);
    /**odstranění observeru
     @param observer - samotný listener, pozorovatel*/
    void deleteObserver (Observer observer);
    /**upozornění observerů na změny*/
    void notifyAllObservers();
}
