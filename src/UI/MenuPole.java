/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.Main;

/**
 * Třída MenuPole - třída vytvářející horní menu s nápovědou, informacemi o hře
 * s možností hru ukončit nebo spustit novou
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */

public class MenuPole extends MenuBar{
    
    private Main main;
    
     /**  Konstruktor - pracuje s parametrem main, poté volá inicializační metodu
     * init 
     *  @param main - z této třídě potřebujeme například získat přístup k metodě 
     *  novaHra() v mainu
     */  
    
    public MenuPole(Main main){
        this.main = main;
        init();
    }
    
      /**Inicializační metoda, která vytváří vytváří položky menu a jeho jednotlivé
       * prvky, přiřazuje klávesovou zkratku, zobrazuje okno s html nápovědou a 
       * vytváří okno s informacemi o programu, umožňuje skončit nebo začít novou hru.
      */
    
    private void init(){
        Menu menuSoubor = new Menu("Advenura");       
        MenuItem itemNovaHra = new MenuItem("Nová hra");
        itemNovaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));      
        MenuItem itemKonec = new MenuItem("Konec");
        
        
        Menu menuHelp = new Menu("Nápověda");
        MenuItem itemOProgramu = new MenuItem("O programu");
        MenuItem itemNapoveda = new MenuItem("Uživatelská příručka");
        
        
        menuSoubor.getItems().addAll(itemNovaHra, itemKonec);
        menuHelp.getItems().addAll(itemOProgramu, itemNapoveda);
        
        this.getMenus().addAll(menuSoubor, menuHelp);
        
        itemOProgramu.setOnAction(new EventHandler<ActionEvent>() {
            
            /**Vytváří, ukazuje informační okno informující o adventuře. */
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("O Adventuře");
                alert.setHeaderText("Grafická verze adventury S Jožou do pekla");
                alert.setContentText("Vytvořeno: ZS 2017/18 v rámci předmětu 4IT115 - Softwarové inženýrství, autor Marie Mikešová");
                alert.initOwner(main.getPrimaryStage());
                alert.showAndWait();
            }
        });
        
        itemNapoveda.setOnAction(new EventHandler<ActionEvent>() {

            /**Vytváří, ukazuje okno s nápovědou */
            
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("Napoveda");
                WebView webview = new WebView();
                
                webview.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webview, 1000, 700));
                stage.show();
            }
        });
        
        itemKonec.setOnAction(new EventHandler<ActionEvent>() {

            /**Ukončuje hru*/
            
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
       /**Vytváří novou hru */
        itemNovaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                main.novaHra();
            }
        });
        
    }
    
}
