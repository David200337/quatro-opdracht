// package src.domain;

// import javafx.beans.value.ObservableValue;
// import javafx.scene.control.TableCell;
// import javafx.scene.control.TextField;

// public class IntegerEditingCell extends TableCell<Student, Integer> {
//     private TextField textField;

//     public IntegerEditingCell(){
//     }

//     @Override
//     public void startEdit(){
//         if(!isEmpty()){
//             super.startEdit();
//             createTextField();
//             setText(null);
//             setGraphic(textField);
//             textField.selectAll();
//         }
//     }

//     @Override
//     public void cancelEdit() {
//         super.cancelEdit();

//         setItem((Integer) getItem());
//         setGraphic(null);
//     }

//     @Override
//     public void updateItem(Integer item, boolean empty) {
//         super.updateItem(item, empty);

//         if (empty) {
//             setItem(item);
//             setGraphic(null);
//         } else {
//             if (isEditing()) {
//                 if (textField != null) {
//                     textField.setItem(getInteger());
// //                  setGraphic(null);
//                 }
//                 setText(null);
//                 setGraphic(textField);
//             } else {
//                 setItem(getInteger());
//                 setGraphic(null);
//             }
//         }
//     }

//     private void createTextField() {
//         textField = new TextField(getInteger());
//         textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
//         textField.setOnAction((e) -> commitEdit(textField.getText()));
//         textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//             if (!newValue) {
//                 System.out.println("Commiting " + textField.getText());
//                 commitEdit(textField.getText());
//             }
//         });
//     }

//     private Integer getInteger() {
//         return getItem() == null ? "" : getItem();
//     }
// }
