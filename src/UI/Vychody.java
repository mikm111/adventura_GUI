/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;

import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logika.IHra;
import logika.Prostor;
import main.Main;
import utils.Observer;

/**
 * Třída Vychody - třída vytvářející seznam východů pro UI
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */
public class Vychody extends AnchorPane implements Observer{

    private IHra hra;
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
    
    public Vychody(IHra hra,TextArea text){
        this.hra = hra;
         hra.getHerniPlan().registerObserver(this);
         this.centralText = text;
         init();
    }
    
       /**Inicializační metoda, která vytváří hlavní list pro
       * východy, výšku a šířku, list polí, který při zavolání metody 
       * kontaktovává observery, vložený do hlavního. Poté vytváří funkcionalitu
       * kliknutí myší pro jednotlivé objekty v listu. Dvojklik levým tlačítkem 
       * pro přechod ezi jednotlivými východy v aktuálním prostoru.
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
            
            /**Vytváří funkcionalitu dvojkliku - vchod do jiného prostoru, výpis 
             příkazu, upozornění observeru*/
            
            @Override
            public void handle(MouseEvent click)
            {
                if (click.getClickCount() == 2) 
                {
                    int index = listObjektu.getSelectionModel().getSelectedIndex();
                    Collection<Prostor> seznam;
                    seznam = hra.getHerniPlan().getAktualniProstor().getVychody();
                    
                    String nazev = "";
                    int pomocna = 0;
                    for (Prostor x : seznam) 
                    {
                       if(pomocna == index)
                       {
                           nazev = x.getNazev();
                       }
                       pomocna++;
                    }
                    
                    String vstupniPrikaz = "jdi "+nazev;
                    String odpovedHry = hra.zpracujPrikaz("jdi "+nazev);

                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
               
                    hra.notifyAllObservers();
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
    public void update() {
        Collection<Prostor> seznam;
        String nazev;
        seznam = hra.getHerniPlan().getAktualniProstor().getVychody();
        objekty.clear();
         for (Prostor prostor : seznam)
         {
         nazev=prostor.getNazev();
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

        hra.getHerniPlan().deleteObserver(this);
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        update();
        
    }
    
}
