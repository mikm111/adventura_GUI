/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logika.Brasna;
import logika.IHra;
import logika.Vec;
import main.Main;
import utils.Observer;

/**
 * Třída VěciProstoru - třída vytvářející seznam věcí v prostoru pro UI
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */

public class VeciProstoru extends AnchorPane implements Observer{

    private IHra hra;
     public Brasna brasna;
     public Vec vec;
    ListView <Object> listObjektu;
    ObservableList <Object> objekty;
    private TextArea centralText;
    
     /**
     *  Konstruktor - pracuje s parametrem hra, registruje observer třídy, 
     *  inicializuje hlavní činnost třídy.
     *  @param hra řídící celkový průběh hry. Vyskytuje se zde kvůli registraci observeru.
     *  @param text - textový prvek zajišťující správné vypsání příkazů do textového 
     *  pole uskutečněných pomocí IU prvku - v tomto případě provedení akce kliknutím na obrázek 
     */
    
    public VeciProstoru(IHra hra,TextArea text){
        this.hra = hra;
         hra.getHerniPlan().getBrasna().registerObserver(this);
         hra.getHerniPlan().registerObserver(this);
         hra.registerObserver(this);
         
         this.centralText = text;
         init();
    }
    
     /**Inicializační metoda, která vytváří srolovatelný (feature rolování ale
       * nebude potřeba díky omezení na počet věcí v prostoru) hlavní list pro
       * věci v prostoru, výšku a šířku, list polí, který při zavolání metody 
       * kontaktovává observery, vložený do hlavního. Poté vytváří funkcionalitu
       * kliknutí myší pro jednotlivé objekty v listu.Dvojklik levým tlačítkem 
       * pro sebrání věci z prostoru do brašny a klik pravým tlačítkem pro 
       * prozkoumání věci, tj. pokud věc neobsahuje v sobě věci další, vypíše se
       * popis takové věci, pokud v sobě věci obsahuje vloží se tyto věci do prostoru.
       * Dále výpisy akcí. Nakonec se kontaktují observery.
      */
    
    private void init(){

     listObjektu = new ListView<>();
     objekty =  FXCollections.observableArrayList();
     listObjektu.setItems(objekty);
     listObjektu.setPrefWidth(140);
     listObjektu.setPrefHeight(633);
     
     listObjektu.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            
            /**Vytváří funkcionalitu dvojkliku - sebrání věci a kliku pravým tlačítkem
             * - prozkoumání věci, výpis příkazu, upozornění observeru*/
            @Override
            public void handle(MouseEvent click)
            {MouseButton button = click.getButton();
                if (click.getClickCount() == 2) 
                {
                    int index = listObjektu.getSelectionModel().getSelectedIndex();
                    List<Vec> seznam;
                    seznam = hra.getHerniPlan().getAktualniProstor().GetVeci();
                    
                    String nazev = "";
                    int pomocna = 0;
                    for (Vec x : seznam) 
                    {
                       if(pomocna == index)
                       {
                           nazev = x.getNazev();
                       }
                       pomocna++;
                    }
                    
                    hra.zpracujPrikaz("vezmi " + nazev);
                }
                
                 if(button==MouseButton.SECONDARY) 
                {
                    
                    int index = listObjektu.getSelectionModel().getSelectedIndex();
                    List<Vec> seznam;
                    seznam = hra.getHerniPlan().getAktualniProstor().GetVeci();
                    
                    String nazev = "";
                    int pomocna = 0;
                    for (Vec x : seznam) 
                    {
                       if(pomocna == index)
                       {
                           nazev = x.getNazev();
                       }
                       pomocna++;
                    }
                    
                    hra.zpracujPrikaz("prozkoumej " + nazev);
                }
            }
        });
     update();

    }
    
     /**Metoda, která vrací list objektů v prosotru. Zavoláním této metody 
      * pak můžeme tento list připojit do Panu v třídě Main
     * @return listObjektu - list objektů v prostoru
     */
    
    public ListView<Object> getList() {
        return listObjektu;
    }
    
     /**Překrytá metoda, která dle názvu přiřazuje věcem obrázky ve zdrojích
    * a poté je vkládá do observatelného listu polí objekty.*/
    
    @Override
    public void update() 
    {
        List<Vec> seznam;
        String nazev;
        seznam = hra.getHerniPlan().getAktualniProstor().GetVeci();
        objekty.clear();

        
        
          for (Vec vec : seznam)
          {
          nazev=vec.getNazev();
         ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/" + nazev + ".jpg"),120,118,false,false));
         objekty.addAll(obrazek);
            }
    }

     /**Překrytá metoda (viz interface Observer), která pracuje s
     * observery v případě, kdy začne nová hra. Odstraní starý observer
     * (například ze hry minulé) a zaregistruje nový. 
     * 
     * @param hra řídící celkový průběh hry. Vyskytuje se zde vůli činnostem observeru. */
    
    @Override
    
    public void novaHra(IHra hra) {

        
        hra.getHerniPlan().getBrasna().deleteObserver(this);
        hra.getHerniPlan().deleteObserver(this);
        hra.deleteObserver(this);

        this.hra = hra;
        hra.getHerniPlan().getBrasna().registerObserver(this);
        hra.registerObserver(this);

        update();
        
    }
    
}
