import javax.swing.*;

/* Наследование JFrame необходимо для создания простого оконного приложения. */
public class JavaSweeper extends JFrame {
    public static void main(String[] args) {
        /* Создаём новый экземпляр класса. С помощью метода setVisible делаем окно видимым. */
        new JavaSweeper().setVisible(true);
    }
}
