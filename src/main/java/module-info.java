module edu.uni.lab {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.uni.lab to javafx.fxml;
    exports edu.uni.lab;
    exports edu.uni.lab.controller to javafx.fxml;
    opens edu.uni.lab.controller to javafx.fxml;
}