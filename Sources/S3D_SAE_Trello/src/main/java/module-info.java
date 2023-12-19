module com.example.s3d_sae_trello {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.s3d_sae_trello to javafx.fxml;
    exports com.example.s3d_sae_trello;
}