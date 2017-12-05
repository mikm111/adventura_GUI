/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import main.Main;
import utils.Observer;


/**
 * Třída AktualniProstor - třída zobrazijící obrázek, náhled prostoru,
 * ve kterém se hráč momentálně nachází. Rozšířený AnchorPane. 
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */


public class AktualniProstor extends AnchorPane implements Observer{

  private IHra hra;

      /**
     *  Konstruktor - pracuje s parametrem hra, registruje observer třídy a vyskytuje se zde update
     *  @param hra řídící celkový průběh hry. Vyskytuje se zde kvůli činnostem observeru.
     */
  
    public AktualniProstor(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
    }
    
    /**Překrytá metoda (viz interface Observer), která si zjišťuje
     * název aktuálního prostoru a dle toho k němu je schopna přiřadit
     * prostoru obrázek, který mu odpovídá.
     */ 
    @Override
    public void update() {
        String nazevProstoru;
        nazevProstoru = hra.getHerniPlan().getAktualniProstor().getNazev();
        ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/" + nazevProstoru + ".jpg"),500,322,false,false));
        this.getChildren().add(obrazek);
    }
    
    
    /**Překrytá metoda (viz interface Observer), která pracuje s
     * observery v případě, kdy začne nová hra. Odstraní starý observer
     * (například ze hry minulé) a zaregistruje nový. 
     * 
     * @param hra řídící celkový průběh hry. Vyskytuje se zde vůli činnostem observeru.
     */ 
    @Override
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
        
}
    
}
