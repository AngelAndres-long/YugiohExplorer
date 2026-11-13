module com.example.yugiohexplorer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.yugiohexplorer to javafx.fxml;
    opens com.example.yugiohexplorer.vista to javafx.fxml;

    exports com.example.yugiohexplorer;
    exports com.example.yugiohexplorer.modelo;
    exports com.example.yugiohexplorer.vista;
    exports com.example.yugiohexplorer.controlador;
}