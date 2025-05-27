package com.example.partners;

import java.sql.*;

public class DbHandler {
    private Connection connection;
    Service service = new Service();
    public void DbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            String user_name = "postgres";
            String pass = "123";
            String con_url = "jdbc:postgresql://localhost:5432/postgres";
            connection = DriverManager.getConnection(con_url, user_name, pass);
        }catch (Exception e) {
            service.ErorMessage(
                    "Ошибка 9-9-9",
                    "Ошибка при попытки аунтификации в БД",
                    e.getMessage()
            );
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            service.WarningMessage(
                    "Предупреждение 0-0-0",
                    "Нет активных подключений к БД",
                    String.valueOf(new IllegalStateException())
            );
        }

        try {
            PreparedStatement st = connection.prepareStatement(query);
            return st.executeQuery();
        }catch (Exception e) {
            service.ErorMessage(
                    "Ошибка 9-9-8",
                    "Ошибка при попытке выполнения загрузки информации из БД",
                    e.getMessage()
            );
        }
        return null;
    }

    public int SetDataAboutNewPartnerToDataBase(String query) throws SQLException {
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            service.WarningMessage(
                    "Уведомление",
                    "Попытка добавления/изменения нового партнера\n",
                    String.valueOf(stmt.executeUpdate())
            );
            return stmt.executeUpdate();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
