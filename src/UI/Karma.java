/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;


import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import logika.IHra;
import utils.Observer;

/**
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 * 
 * Třída Karma - třída implementující funkcionalitu zobrazení dosaženého počtu
 * bodů (karmy) hráče a bodů, kterých musí hráč ještě dosáhnout
 */

public class Karma extends AnchorPane implements Observer{

    private IHra hra;
    private Label karmaText;
    
    /**Konstruktor - registruje nový observer, vytváří label s informací o 
     * počtu bodů hráče spolu s iniciací a nastavuje font tohoto textu labelu.
     * Poté volá aktualizační metodu update
     * @param hra - řídící celkový průběh hry. Vyskytuje se zde kvůli registraci 
     * observeru a zavolání getteru karmy.*/
    
    public Karma(IHra hra){
        this.hra = hra;
         hra.registerObserver(this);
         this.karmaText = new Label("  Hodnota karmy je: " + 0 + ". Potřebuješ ještě "+ 5 +" bodů.");
         karmaText.setFont(Font.font("Arial", 16));
         update();
    }
   
   /**Překrytá metoda (viz interface Observer), která nastavuje změnu hodnoty 
    * textu labelu
    */ 
    @Override
    public void update() { 
      this.karmaText.setText("  Hodnota karmy je: " + hra.getKarma()+ ". Potřebuješ ještě "+ hra.getKarmaZbyvajici()+" bodů.");
    }

    /**Metoda, která při zavolání vrací hodnotu textu karmy 
     @return karmaText - text, který informuje o stavu karmy*/ 
    public Label getKarmaText() {
        return karmaText;
    }
    /**Překrytá metoda (viz interface Observer), která pracuje s
     * observery v případě, kdy začne nová hra. Odstraní starý observer
     * (například ze hry minulé) a zaregistruje nový. 
     * 
     * @param hra řídící celkový průběh hry. Vyskytuje se zde vůli činnostem observeru.
     */ 
    @Override
    public void novaHra(IHra hra) {
        hra.deleteObserver(this);
        this.hra = hra;
        hra.registerObserver(this);
        update();
        
    }

}

