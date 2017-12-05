/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package utils;

import logika.IHra;

/**
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */

/**
 * Rozhraní které musí implementovat hra, aby správně UI správně fungovalo
 * tj. aby se aktualizovala mapa prostorů, aktuální obrázek prostoru, inventář,
 * východy, věci v prostoru.*/

public interface Observer {
    
    /** aktualizace objektů*/
    void update();
    /**nastavení hry do původního stavu při zadání nové hry
     @param hra řídící celkový průběh hry. */
    void novaHra(IHra hra);
    
}
