module ru.vsu.cs.yurov.task2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports ru.vsu.cs.yurov.graphics;
    opens ru.vsu.cs.yurov.graphics to javafx.fxml;
}