import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* Наследование JFrame необходимо для создания простого оконного приложения. */
public class JavaSweeper extends JFrame {
    private final int IMAGE_SIZE = 50;
    private final Game game;
    private JPanel panel;
    /* метка-сообщение */
    private JLabel label;

    /* Конструктор игры. */
    private JavaSweeper() {
        int BOMBS = 10;
        int ROWS = 9;
        int COLS = 9;
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        /* Подгрузка изображений. */
        setImages();
        /* Сначала инициализируем метку. */
        initLabel();
        /* Потом инициализируем панель. */
        initPanel();
        initFrame();
    }

    public static void main(String[] args) {
        /* Создаём новый экземпляр класса. */
        new JavaSweeper();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        /* Прикрепляем метку вниз. */
        add(label, BorderLayout.SOUTH);
    }

    private void initFrame() {
        /* Автоматическое завершение выполнения программы при нажатии на крестик. */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        /* С помощью метода setVisible делаем окно видимым. */
        setVisible(true);
        /* Выравнивание размера окна, чтобы поместились все объекты. */
        pack();
        /* Выравнивание окна по центру. */
        setLocationRelativeTo(null);
        /* Установка иконки программы. */
        setIconImage(getImage("icon"));
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
        /* Добавление мышечного адаптера. */
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                /* getButton() возвращает тип нажатой кнопки
                 * MouseEvent.BUTTON1 - левая кнопка мыши */
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                /* MouseEvent.BUTTON3 - правая кнопка мыши */
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                /* MouseEvent.BUTTON2 - средняя кнопка мыши
                 * start() перезапускает программу */
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                /* Установка на метку нового текста. */
                label.setText(getMessage());
                /* Перерисовка панели для отображения изменений. */
                panel.repaint();
            }
        });
        /* Установка размеров панели с помощью класса Dimension. */
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        /* Добавим панель. */
        add(panel);
    }

    private String getMessage() {
        return switch (game.getState()) {
            case PLAYED -> "Think twice!";
            case BOMBED -> "You lose!";
            case WINNER -> "Congratulations!";
        };
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
