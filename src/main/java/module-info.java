module by.teamwork.banksystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens by.teamwork.banksystem to javafx.fxml;
    exports by.teamwork.banksystem;
}