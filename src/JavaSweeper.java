import sweeper.Box;

import javax.swing.*;
import java.awt.*;

/* Наследование JFrame необходимо для создания простого оконного приложения. */
public class JavaSweeper extends JFrame {
    private final int COLS = 15;
    private final int ROWS = 1;
    private final int IMAGE_SIZE = 50;
    private JPanel panel;

    /* Конструктор игры. */
    private JavaSweeper() {
        /* Подгрузка изображений. */
        setImages();
        /* Сначала инициализируем панель. */
        initPanel();
        initFrame();
    }

    public static void main(String[] args) {
        /* Создаём новый экземпляр класса. */
        new JavaSweeper();
    }

    private void initFrame() {
        /* Выравнивание размера окна, чтобы поместились все объекты. */
        pack();
        /* Автоматическое завершение выполнения программы при нажатии на крестик. */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        /* Выравнивание окна по центру. */
        setLocationRelativeTo(null);
        setResizable(false);
        /* С помощью метода setVisible делаем окно видимым. */
        setVisible(true);
        /* Установка иконки программы. */
        setIconImage(getImage("icon"));
    }

    private void initPanel() {
        panel = new JPanel() {
            /* Для отрисовки картинок. */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Box box : Box.values()) {
                    /* this - экземпляр отрисовки
                     * box.ordinal() возвращает порядковый номер объекта в перечислении */
                    g.drawImage((Image) box.image, box.ordinal() * IMAGE_SIZE, 0, this);
                }
            }
        };
        /* Установка размеров панели с помощью класса Dimension. */
        panel.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROWS * IMAGE_SIZE));
        /* Добавим панель. */
        add(panel);
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        /* Чтобы путь к файлу был найден необходимо пометить директорию как ресурсную:
         *  res -> Mark Directory as -> Resources Root. */
        /* С помощью команды getClass().getResource подгрузим ресурс. */
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        /* Вернём картинку. */
        return icon.getImage();
    }
}

/* Для сборки приложения:
 * File -> Project Structure -> Artifacts -> + -> Jar -> from module with dependencies ->
 * main class -> ok.
 * Теперь нужно собрать файл: Build -> Build Artifacts.
 * Далее в out -> artifacts появится jar файл, который и является приложением. */
