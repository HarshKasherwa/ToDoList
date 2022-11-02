package com.kasherwa.todolist;
import com.kasherwa.todolist.datamodel.ToDoData;
import com.kasherwa.todolist.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<ToDoItem> todoItems;
    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineLabel;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {

//        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 30th birthday card for John"
//                , LocalDate.of(2022, 11, 20));
//
//        ToDoItem item2 = new ToDoItem("Doctor Appointment", "Dentist: Show teeth"
//                , LocalDate.of(2022, 11, 25));
//
//        ToDoItem item3 = new ToDoItem("DL Assignment", "Complete DL assignment"
//                , LocalDate.of(2022, 12, 11));
//
//        ToDoItem item4 = new ToDoItem("AI Project", "Complete AI Project"
//                , LocalDate.of(2022, 12, 30));
//
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//
//        ToDoData.getInstance().setToDoItems(todoItems);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observableValue, ToDoItem toDoItem, ToDoItem t1) {
                if (t1 != null){
                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadLineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleClickListView()   {
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is: " + item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString());

        itemDetailsTextArea.setText(item.getDetails());
        deadLineLabel.setText(item.getDeadline().toString());
    }

    public void showNewItemDialogue()   {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("toDoItemDialogue.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e)  {
            System.out.println("Couldn't load the dialog box");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)    {
            DialogueController controller = fxmlLoader.getController();
            controller.processResults();
            todoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());

            System.out.println("OK Pressed");
        }
        else {
            System.out.println("Cancel Pressed");
        }
    }
}