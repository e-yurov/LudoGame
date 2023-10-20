module ru.vsu.cs.yurov.task2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports ru.vsu.cs.yurov;
    opens ru.vsu.cs.yurov to javafx.fxml;
}