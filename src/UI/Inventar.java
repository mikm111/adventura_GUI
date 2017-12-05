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
 * Třída Inventář - třída vytvářející seznam věcí v brašně pro UI
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */
public class Inventar extends AnchorPane implements Observer{

    private IHra hra;
     public Brasna brasna;
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
    
    public Inventar(IHra hra,TextArea text ){
        this.hra = hra;
         hra.getHerniPlan().getBrasna().registerObserver(this);
        this.centralText = text;
        init();
    }
    
    
     /**Metoda, která vrací list objektů v brašně. Zavoláním této metody 
      * pak můžeme tento list připojit do Panu v třídě Main
     * @return listObjektu - list objektů v brašně
     */
    public ListView<Object> getList() {
        return listObjektu;
    }
    
      /**Inicializační metoda, která vytváří srolovatelný (feature rolování ale
       * nebude potřeba díky omezení na počet věcí v brašně) hlavní list pro brašnu,
       * jeho výšku a šířku, list polí, který při zavolání metody kontaktovává 
       * observery, vložený do hlavního. Poté vytváří funkcionalitu kliknutí myší
       * (dvojklik levým tlačítkem pro položení + výpis a klik pravým tlačítkem 
       * pro použití věci v aktuálním prostoru ) pro jednotlivé objekty v listu.
       * Nakonec kontaktuje observery.
      */
    private void init(){
     listObjektu = new ListView<>();
     objekty =  FXCollections.observableArrayList();
     listObjektu.setItems(objekty); 
     listObjektu.setPrefWidth(140);
     listObjektu.setPrefHeight(633);

     
     
     listObjektu.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            
            /**Vytváří funkcionalitu dvojkliku - položení objektu z inventáře do
             * prostoru, a funkcionalitu kliknutí pravým tlačítkem - použití věci
             * v aktuálním prostoru. + Výpis příkazu, upozornění observeru*/
            
            @Override
            public void handle(MouseEvent click)
            {
                if (click.getClickCount() == 2) 
                {
                    int index = listObjektu.getSelectionModel().getSelectedIndex();
                    List<Vec> seznam;
                    seznam = hra.getHerniPlan().getBrasna().getSeznamVeci();
                    
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
                    
                    String vstupniPrikaz = "polož "+nazev;
                    String odpovedHry = hra.zpracujPrikaz("polož "+nazev);

                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
               
                    hra.notifyAllObservers();
                }
                 MouseButton button = click.getButton();
                 if(button==MouseButton.SECONDARY) 
                {
                    int index = listObjektu.getSelectionModel().getSelectedIndex();
                    List<Vec> seznam;
                    seznam = hra.getHerniPlan().getBrasna().getSeznamVeci();
                    
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
                    
                    String vstupniPrikaz = "použij "+nazev;
                    String odpovedHry = hra.zpracujPrikaz("použij "+nazev);

                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
               
                    hra.notifyAllObservers();
                }
            }
        });
     
     
     update();
    

    }

    /**Překrytá metoda, která dle názvu přiřazuje věcem obrázky ve zdrojích
    * a poté je vkládá do observatelného listu polí objekty.*/
    @Override
    public void update() {
      
        List<Vec> seznam;
        String nazev;
        seznam = hra.getHerniPlan().getBrasna().getSeznamVeci();
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
        this.hra = hra;
        hra.getHerniPlan().getBrasna().registerObserver(this);
        update();
        
    }
    
}
