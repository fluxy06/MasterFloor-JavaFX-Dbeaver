package com.example.partners;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEditFormController {
    @FXML
    private Button ButtonBack;

    @FXML
    private GridPane GridBox;

    @FXML
    private TextField InnField;
    @FXML
    private TextField NamePartnerField;
    @FXML
    private TextField NameDirectorField;
    @FXML
    private TextField RateField;
    @FXML
    private TextField AdresField;
    @FXML
    private TextField PhoneNumberField;
    @FXML
    private TextField EmailField;
    @FXML
    private ComboBox<String> ComboBoxField;
    @FXML
    private Button ButtonSave;
    Service service = new Service();
    DbHandler dbhandler = new DbHandler();
    private Partner selectedPartner;

    public void initialize() {
        ComboBoxField.getItems().add("ООО");
        ComboBoxField.getItems().add("ЗАО");
        ComboBoxField.getItems().add("АО");
        ComboBoxField.getItems().add("ПАО");
        ComboBoxField.getItems().add("ОДО");
        ComboBoxField.getItems().add("ПТ");
    }
    public void Back(ActionEvent actionEvent) throws IOException {
        try {
            Node source = (Node) actionEvent.getSource();
            Stage current_stage = (Stage) source.getScene().getWindow();
            service.openForm("PartnersForm.fxml", current_stage);
        }catch (Exception e) {
            service.ErorMessage(
                    "Ошибка 0-0-2",
                    "Ошибка при переходе на главную форму",
                    e.getMessage()
            );
        }
    }


    @FXML
    public void SaveAction(ActionEvent actionEvent) {
        try {
            if (selectedPartner == null) {
                dbhandler.DbConnection();
                long inn = Long.parseLong(InnField.getText().trim());
                String type = ComboBoxField.getValue();
                String name_company = NamePartnerField.getText().trim();
                String name_director = NameDirectorField.getText().trim();
                String email = EmailField.getText().trim();
                String phone_number = PhoneNumberField.getText().trim();
                long rate = Long.parseLong(RateField.getText().trim());
                String adres = AdresField.getText().trim();

                String query = String.format(
                        "INSERT INTO public.partners VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s', '%s')",
                        name_director, email, phone_number, adres, inn, rate, type, name_company
                );
                dbhandler.SetDataAboutNewPartnerToDataBase(query);
            } else {
                dbhandler.DbConnection();
                String query = String.format(
                        "UPDATE public.partners SET " +
                                "\"Director\"='%s', \"Email\"='%s', \"Phone_number\"='%s', " +
                                "\"Adres\"='%s', \"Rate\"=%d, type_partner='%s', name_partner='%s' " +
                                "WHERE \"Inn\"=%d",
                        NameDirectorField.getText(),
                        EmailField.getText(),
                        PhoneNumberField.getText(),
                        AdresField.getText(),
                        Long.parseLong(RateField.getText()),
                        ComboBoxField.getValue(),
                        NamePartnerField.getText(),
                        selectedPartner.getInn()
                );
                dbhandler.SetDataAboutNewPartnerToDataBase(query);
            }
        } catch (Exception e) {
            service.ErorMessage("Ошибка", "Ошибка сохранения", e.getMessage());
        }
    }


    public void initData(Partner partner) {
        this.selectedPartner = partner;
        InnField.setText(String.valueOf(partner.getInn()));
        NamePartnerField.setText(partner.getName_company());
        NameDirectorField.setText(partner.getName_director());
        RateField.setText(String.valueOf(partner.getRate()));
        AdresField.setText(partner.getAdres());
        PhoneNumberField.setText(partner.getPhone_number());
        EmailField.setText(partner.getEmail());
        ComboBoxField.setValue(partner.getType());
    }
}
