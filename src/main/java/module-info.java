module ru.vsu.cs.yurov.task2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports ru.vsu.cs.yurov;
    opens ru.vsu.cs.yurov to javafx.fxml;
    exports ru.vsu.cs.yurov.graphics.fx;
    opens ru.vsu.cs.yurov.graphics.fx to javafx.fxml;
}