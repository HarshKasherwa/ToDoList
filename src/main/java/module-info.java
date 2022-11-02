module com.kasherwa.todolist.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kasherwa.todolist to javafx.fxml;
    exports com.kasherwa.todolist;
}