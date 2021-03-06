package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;
    private Drzava drzava;
    private ObservableList<Grad> listGradovi;
    public RadioButton tglRepublika;
    public RadioButton tglKraljevina;
    public RadioButton tglDrzava;

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.drzava = drzava;
        listGradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(listGradovi);
        if (drzava != null) {
            fieldNaziv.setText(drzava.getPraviNaziv());
            //choiceGrad.getSelectionModel().select(drzava.getGlavniGrad());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave

            for(int i=0; i < listGradovi.size(); i++)
                if (listGradovi.get(i).getId() == drzava.getGlavniGrad().getId())
                    choiceGrad.getSelectionModel().select(i);

            if(drzava instanceof Republika){
                tglDrzava.setSelected(false);
                tglRepublika.setSelected(true);
                tglKraljevina.setSelected(false);
            }else if(drzava instanceof Kraljevina){
                tglDrzava.setSelected(false);
                tglRepublika.setSelected(false);
                tglKraljevina.setSelected(true);
            }else{
                tglDrzava.setSelected(true);
                tglRepublika.setSelected(false);
                tglKraljevina.setSelected(false);
            }
        } else {
            tglDrzava.setSelected(true);
            tglRepublika.setSelected(false);
            tglKraljevina.setSelected(false);
            choiceGrad.getSelectionModel().selectFirst();

        }


    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        int id=0;
        if (drzava != null) id=drzava.getId();

        if(tglKraljevina.isSelected())  drzava = new Kraljevina(id, fieldNaziv.getText(), choiceGrad.getSelectionModel().getSelectedItem());
        else if(tglRepublika.isSelected()) drzava = new Republika(id, fieldNaziv.getText(), choiceGrad.getSelectionModel().getSelectedItem());
        else  drzava = new Drzava(id, fieldNaziv.getText(), choiceGrad.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
