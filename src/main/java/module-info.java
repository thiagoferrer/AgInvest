module com.example.aginvest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.net.http;
    requires bcrypt;
    requires java.sql;
    requires jjwt.api;

    opens com.example.aginvest to javafx.fxml;
    //exports com.example.aginvest;
    exports com.example.aginvest.controller.viewcontroller;
    opens com.example.aginvest.controller.viewcontroller to javafx.fxml;
    exports com.example.aginvest;
}