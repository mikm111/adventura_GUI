/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazKonec implementuje pro hru příkaz konec.
 * Tato třída je součástí jednoduché textové hry.
 *  
 * @author     Jarmila Pavlickova, Marie Mikešová
 * @version    ZS 2016/2017
 */

class PrikazKonec implements IPrikaz {

    private static final String NAZEV = "konec";
    private Hra hra;

    /**
     *  Konstruktor třídy
     *  
     *  @param hra odkaz na hru, která má být příkazem konec ukončena
     */    
    public PrikazKonec(Hra hra) {
        this.hra = hra;
    }

    /**
     * V případě, že příkaz má jen jedno slovo "konec" hra končí(volá se metoda setKonecHry(true))
     * jinak pokračuje např. při zadání "konec a".
     * 
     * @return zpráva, kterou vypíše hra hráči
     */

    @Override
    public String proved(String... parametry) {
        if (parametry.length > 0) {
            return "Ukončit co? Nechápu, proč jsi zadal to druhé slovo.";
        }
        else {
            hra.setKonecHry(true);
            return "Tebe už hra nebavila? Jak jsi to mohl Jožovi udělat?\n"
                  + "Ten chudák bude kvůli tobě navždy poslouchat chytré rady\n"
                  + "archanděla Michaela";
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
