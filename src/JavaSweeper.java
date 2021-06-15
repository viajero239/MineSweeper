import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;

/* Наследование JFrame необходимо для создания простого оконного приложения. */
public class JavaSweeper extends JFrame {
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int IMAGE_SIZE = 50;
    private final int BOMBS = 10;
    private Game game;
    private JPanel panel;

    /* Конструктор игры. */
    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
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
        /* Выравнивание размера окна, чтобы поместились все объекты. */
        pack();
    }

    private void initPanel() {
        panel = new JPanel() {
            /* Для отрисовки картинок. */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    /* this - экземпляр отрисовки */
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        /* Установка размеров панели с помощью класса Dimension. */
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
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
