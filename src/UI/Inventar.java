/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň? úpěl ďábelské ódy. */
package UI;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logika.Brasna;
import logika.IHra;
import logika.Vec;
import utils.Observer;

/**
 * Třída Inventář - třída vytvářející seznam věcí v brašně pro UI.
 *
 * @author Marie Mikešová
 * @version ZS 2017/2018
 */
public class Inventar extends AnchorPane implements Observer {

    private IHra hra;
    private Brasna brasna;
    private ListView<Vec> listObjektu;

    /**
     * Konstruktor - pracuje s parametrem hra, registruje observer třídy,
     * inicializuje hlavní činnost třídy.
     *
     * @param hra řídící celkový průběh hry. Vyskytuje se zde kvůli registraci observeru.
     *            pole uskutečněných pomocí IU prvku - v tomto případě provedení akce kliknutím na obrázek
     */

    public Inventar(IHra hra) {
        setupList();
        // TODO: remove after new game will be called after fresh start
        novaHra(hra);
    }

    /**
     * Metoda, která vrací list objektů v brašně. Zavoláním této metody
     * pak můžeme tento list připojit do Panu v třídě Main
     *
     * @return listObjektu - list objektů v brašně
     */
    public ListView<Vec> getList() {
        return listObjektu;
    }

    /**
     * Inicializační metoda, která vytváří srolovatelný (feature rolování ale
     * nebude potřeba díky omezení na počet věcí v brašně) hlavní list pro brašnu,
     * jeho výšku a šířku, list polí, který při zavolání metody kontaktovává
     * observery, vložený do hlavního. Poté vytváří funkcionalitu kliknutí myší
     * (dvojklik levým tlačítkem pro položení + výpis a klik pravým tlačítkem
     * pro použití věci v aktuálním prostoru ) pro jednotlivé objekty v listu.
     * Nakonec kontaktuje observery.
     */
    private void setupList() {
        listObjektu = new ListView<>();
        listObjektu.setPrefWidth(140);
        listObjektu.setPrefHeight(633);
        listObjektu.setCellFactory(vecListView -> new InventarCell());

        listObjektu.setOnMouseClicked(click -> {
            Vec vybranaVec = brasna.getSeznamVeci().get(listObjektu.getSelectionModel().getSelectedIndex());

            if (isDoubleClick(click)) {
                hra.zpracujPrikaz(vybranaVec.poloz());
            } else if (isRightClick(click)) {
                hra.zpracujPrikaz(vybranaVec.pouzij());
            }
        });
    }

    private static class InventarCell extends ListCell<Vec> {

        private final ImageView imageView;

        public InventarCell() {
            imageView = new ImageView();
            imageView.setFitWidth(120);
            imageView.setFitHeight(118);

            setGraphic(imageView);
        }

        @Override
        protected void updateItem(Vec item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                imageView.setImage(null);
            } else {
                imageView.setImage(item.getImage());
            }
        }
    }

    /**
     * Překrytá metoda (viz interface Observer), která pracuje s
     * observery v případě, kdy začne nová hra. Odstraní starý observer
     * (například ze hry minulé) a zaregistruje nový.
     *
     * @param hra řídící celkový průběh hry. Vyskytuje se zde vůli činnostem observeru.
     */
    @Override
    public void novaHra(IHra hra) {
        this.hra = hra;
        brasna = hra.getHerniPlan().getBrasna();
        listObjektu.setItems(brasna.getSeznamVeci());
    }

    @Override
    public void update() {
        // Implementation intentionally left blank. All updates are driven by ObservableList.
    }

    private boolean isRightClick(MouseEvent click) {
        return click.getButton() == MouseButton.SECONDARY;
    }

    private boolean isDoubleClick(MouseEvent click) {
        return click.getClickCount() == 2;
    }
}
