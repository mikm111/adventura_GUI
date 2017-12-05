/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */

package main;

import UI.AktualniProstor;
import UI.Inventar;
import UI.Karma;
import UI.Mapa;
import UI.MenuPole;
import UI.VeciProstoru;
import UI.Vychody;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 * Třída Main - hlavní třída spouštění obsahuje především prvky vytváření grafiky.
 * Zde se vytváří hlavní scéna hry a její panely - jednotlivé pany,boxy...
 * 
 * @author     Marie Mikešová
 * @version    ZS 2017/2018
 */
public class Main extends Application {

    private Mapa mapa;
    private MenuPole menu;
    private IHra hra;
    private TextArea centerText;
    private Stage primaryStage;
    private Inventar inventar;
    private VeciProstoru prostory;
    private AktualniProstor aktualniProstor;
    private Vychody vychody;
    private Karma karma;

     /**  Vytváří hru, primary stage - hlavní okno s rozmistenim 
      * s scénou borderPane, a vklada do nej dalsi jednotlive graficke panely, objekty:
      * Vytváří a řadí do scény pole textu, mapu, obrázek aktuálního prostoru,
      * horní menu, příkazové okno, flowpany s inventářem, východy a věcmi v prostoru
      *  @param primaryStage - hlavní okno hry  */
    
    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        hra = new Hra();
        centerText = new TextArea();
        centerText.setText(hra.vratUvitani());
        centerText.setEditable(false);
        
        

        mapa = new Mapa(hra);
        menu = new MenuPole(this);
        inventar = new Inventar(hra,centerText);
        prostory = new VeciProstoru(hra,centerText);
        aktualniProstor = new AktualniProstor (hra);
        vychody = new Vychody (hra,centerText);
        karma = new Karma (hra);

        BorderPane borderPane = new BorderPane();

        //pole zadani prikazu
        borderPane.setCenter(centerText);
        Label zadejPrikazLabel = new Label("Zadej příkaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        
        TextField zadejPrikazTextField = new TextField("Sem zadej příkaz");
        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() {

            
            
            //*Zpracování zadaných textových příkazů*//
            
            @Override
            public void handle(ActionEvent event) {

                String zadanyPrikaz = zadejPrikazTextField.getText();
                String odpoved = hra.zpracujPrikaz(zadanyPrikaz);
                
                centerText.appendText("\n" + zadanyPrikaz + "\n");
                centerText.appendText("\n" + odpoved + "\n");
                
                zadejPrikazTextField.setText("");
                
                if(hra.konecHry()){
                    zadejPrikazTextField.setEditable(false);
                }
                
            }
        });
        
        FlowPane dolniPanel = new FlowPane();
        dolniPanel.setAlignment(Pos.CENTER);
        dolniPanel.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField, karma.getKarmaText());
        
        VBox prostoryBox = new VBox(3);
        
        Label lVeci = new Label("Věci v Prostoru");
        lVeci.setFont(Font.font("Arial", FontWeight.BOLD, 14));
         
        FlowPane pVeci = new FlowPane();
        pVeci.setPrefWidth(140);
        pVeci.getChildren().addAll(lVeci,prostory.getList());
        pVeci.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pVeci.setAlignment(Pos.CENTER);
        
        
        Label lVychod = new Label("Východy");
        lVychod.setFont(Font.font("Arial", FontWeight.BOLD, 14));
         
        FlowPane pVychod = new FlowPane();
        pVychod.setPrefWidth(140);
        pVychod.getChildren().addAll(lVychod,vychody.getList());
        pVychod.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pVychod.setAlignment(Pos.CENTER);
        
        Label lBrasna = new Label("Věci v brašně");
        lBrasna.setFont(Font.font("Arial", FontWeight.BOLD, 14));
         
        FlowPane pBrasna = new FlowPane();
        pBrasna.setPrefWidth(2);
        pBrasna.getChildren().addAll(lBrasna,inventar.getList());
        pBrasna.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pBrasna.setAlignment(Pos.CENTER);
        
        BorderPane polozky = new BorderPane();
        polozky.setPrefWidth(420);
        polozky.setLeft(pBrasna);
        polozky.setCenter(pVeci);
        polozky.setRight(pVychod);
        
        prostoryBox.getChildren().addAll(mapa, aktualniProstor);

        //panel prikaz
        borderPane.setBottom(dolniPanel);
        //obrazek s mapou
        borderPane.setLeft(prostoryBox);
        //menu adventury
        borderPane.setTop(menu);
        
        borderPane.setRight(polozky);
        
        Scene scene = new Scene(borderPane, 1550, 700);

        primaryStage.setTitle("Do pekla s Jožou!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        zadejPrikazTextField.requestFocus();
    }

    /**Metoda pro vytvoření funkcionality výběru verze hry - grafické nebo textové
     * dle zadaného parametru při spouštění
     * @param args argumenty příkazového řádku
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();
            } else {
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

        /**
     * Obsahuje akce provedené při zvolení nové hry - vytvoření nové hry a zavolani
     * metod novaHra, které obsahují odstranění původních observerů a jejich nahrazení 
     * novými.
     */
    
    public void novaHra() {
        hra = new Hra();
        centerText.setText(hra.vratUvitani());
        mapa.novaHra(hra);
        vychody.novaHra(hra);
        inventar.novaHra(hra);
        prostory.novaHra(hra);
        aktualniProstor.novaHra(hra);
        
    }

    /**
     * Getter pro získání hlavního okna hry
     * @return the primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
