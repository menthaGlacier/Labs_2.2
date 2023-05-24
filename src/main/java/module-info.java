module edu.uni.lab {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.uni.lab to javafx.fxml;
    opens edu.uni.lab.controller to javafx.base, javafx.fxml;
    exports edu.uni.lab.utility to javafx.fxml;
    exports edu.uni.lab;
    exports edu.uni.lab.model;
    exports edu.uni.lab.model.ai;
}