import javafx.application.Application;
import javafx.stage.Stage;

public class GUICourses extends Application {

    @Override
    public void start(Stage window){
        window.setTitle("Courses");
        window.show();
    }

    public static void main(String[] args){
        launch(GUICourses.class);
    }
} 