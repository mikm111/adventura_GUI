/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import main.Main;
import utils.Observer;

/**
 * Třída Mapa - třída vytvářející obrázek mapy prostorů
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */

public class Mapa extends AnchorPane implements Observer{

    private IHra hra;
    private Circle tecka;
    
      /**  Konstruktor - pracuje s parametrem hra, registruje observer, 
      * poté volá inicializační metodu init 
      *  @param hra - řídící celkový průběh hry. Vyskytuje se zde kvůli registraci observeru.*/  
    
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
     /**Inicializační metoda, která vytváří obrázek mapy a tečku poukazující
      * na aktuální prostor na mapě*/
    
    private void init(){
        ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.jpg"),500,322,false,false));
        tecka = new Circle(10, Paint.valueOf("red"));
        this.getChildren().addAll(obrazek, tecka);
        update();
    }
 
    /**Aktualizační metoda nastavující pozice tečky dle jednotlivých 
     * prostorů */
    
    @Override
    public void update() {
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosY());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosX());
    }

    /**Překrytá metoda (viz interface Observer), která pracuje s
     * observery v případě, kdy začne nová hra. Odstraní starý observer
     * (například ze hry minulé) a zaregistruje nový. 
     * 
     * @param hra řídící celkový průběh hry. Vyskytuje se zde vůli činnostem observeru. */
    
    @Override
    public void novaHra(IHra hra) {
        hra.getHerniPlan().deleteObserver(this);
        
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
        
    }
    
}
