package com.example.partners;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartnersController {
    Service service = new Service();
    @FXML
    private ComboBox<String> ComboBoxField;
    @FXML
    private ListView<Partner> listPartners;
    @FXML
    private VBox container;
    DbHandler dbhand = new DbHandler();
    public void initialize() throws SQLException {
        listPartners.getItems().clear();
        loadPartners();

        // Убедитесь, что эта настройка есть:
        listPartners.setCellFactory(param -> new ListCell<Partner>() {
            @Override
            protected void updateItem(Partner item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Используем переопределенный toString()
                }
            }
        });
    }

    public void loadPartners() {
        try {
            dbhand.DbConnection();
            String query = "SELECT \"Director\", \"Email\", \"Phone_number\", \"Adres\", \"Inn\", \"Rate\", type_partner, name_partner FROM public.partners;";
            ResultSet rs = dbhand.executeQuery(query);

            // Проверка на null
            if (rs == null) {
                throw new SQLException("ResultSet is null. Проверьте SQL-запрос.");
            }

            while (rs.next()) {
                long inn = rs.getLong("Inn");
                double discount = calculateDiscount(inn); // Рассчитайте скидку

                Partner partner = new Partner(
                        rs.getString("name_partner"),
                        rs.getString("type_partner"),
                        rs.getString("Director"),
                        rs.getString("Email"),
                        rs.getString("Phone_number"),
                        rs.getLong("Rate"),
                        rs.getString("Adres"),
                        inn,
                        discount
                );
                listPartners.getItems().add(partner);
            }

        } catch (SQLException e) {
            service.ErorMessage(
                    "Ошибка 0-0-0",
                    "Ошибка при загрузке данных",
                    e.getMessage()
            );
        } catch (Exception e) {
            service.ErorMessage(
                    "Ошибка 0-0-0",
                    "Неизвестная ошибка",
                    e.getMessage()
            );
        }
    }

    // Добавьте метод расчета скидки в контроллер
    private double calculateDiscount(long inn) {
        try {
            String query = String.format(
                    "SELECT SUM(\"Count_product\") as total FROM public.partner_products WHERE \"Partner_inn\" = %d;",
                    inn
            );
            ResultSet rs = dbhand.executeQuery(query);

            if (rs == null || !rs.next()) {
                return 0.0;
            }

            double totalSales = rs.getDouble("total");
            if (totalSales >= 300000) return 15.0;
            else if (totalSales >= 50000) return 10.0;
            else if (totalSales >= 10000) return 5.0;
            else return 0.0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }


    public void ClickBtnAdd(ActionEvent actionEvent) {
        try {
            Node source = (Node) actionEvent.getSource();
            Stage current_stage = (Stage) source.getScene().getWindow();
            service.openForm("addEditForm.fxml", current_stage);


        }catch (Exception e) {
            service.ErorMessage(
                    "Ошибка 0-0-1",
                    "Ошибка при открытии формы добавления партнера",
                    e.getMessage()
            );
        }
    }

    public void RedactAction(ActionEvent actionEvent) throws IOException {
        Partner selectedPartner = listPartners.getSelectionModel().getSelectedItem();
        if (selectedPartner == null) {
            Service.ErorMessage(
                    "Ошибка",
                    "Партнер не выбран",
                    "Выберите партнера из списка перед редактированием"
            );
            return;
        }

        try {
            // Загрузка FXML и контроллера
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEditForm.fxml"));
            Parent root = loader.load();

            // Передача данных в контроллер формы
            AddEditFormController controller = loader.getController();
            controller.initData(selectedPartner); // Ключевой момент!
            Node source = (Node) actionEvent.getSource();
            Stage current_stage = (Stage) source.getScene().getWindow();
            current_stage.close();
            // Отображение формы
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Обновление данных после закрытия формы
            listPartners.getItems().clear();
            loadPartners();

        } catch (IOException e) {
            Service.ErorMessage(
                    "Ошибка",
                    "Ошибка открытия формы",
                    e.getMessage()
            );
        }
    }
}
