import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

public class JavaSweeper extends JFrame {

    private Game game;
    private JPanel panel; /// игровое поле
    private JLabel label; /// метка о состоянии игры
    private final int COLS = 10; /// количество столбцов
    private final int ROWS = 10; /// количество строк
    private final int BOMBS = 20; /// количество бомб
    private final int IMAGE_SIZE = 50; /// размер изображения одной клетки игрового поля

    public static void main(String[] args) {

        new JavaSweeper(); // создаем экземпляр нашей программы

    }
    private JavaSweeper(){ // создаем приватный конструктор, в нем погдотавливаем все необходимое для запуска нашего фрейма

        game = new Game(COLS, ROWS, BOMBS); // устанавливаем размер поля
        game.start();
        initLabel();       // запускаем инициализацию метки
        setImages();       // запускаем инициализацию картинок
        initPanel();       // запускаем инициализацию игрового поля
        initFrame();       // запускаем инициализацию рамки (фрейма)

    }

    private void initLabel(){
        label = new JLabel("Welcome!"); //создаем метку состояния игры
        add(label, BorderLayout.SOUTH); // добавляем метку на панель
    }

    /**
     * Задаем характеристики игрового поля
     */
    private void initPanel(){
        panel = new JPanel()// создаем объект игрового поля

                /**
                 * анонимный класс с методом для отрисовки картинок (переопределяем)
                 */
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (Coord coord:Ranges.getAllCoords()) // перебор всех координат поля
                    g.drawImage((Image) game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this); // выводим изображение при инициализации поля


            }
        };

        /**
         * Добавляем мышку к панели
         */
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int x = e.getX()/ IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if(e.getButton() == MouseEvent.BUTTON1) // если кликнули левой кнопкой мыши
                    game.pressLeftButton(coord);
                if(e.getButton() == MouseEvent.BUTTON3) // если кликнули правой кнопкой мыши
                    game.pressRightButton(coord);
                if(e.getButton() == MouseEvent.BUTTON2) // если кликнули средней кнопкой мыши
                    game.start(); // перезапуск игры
                label.setText(getMessage()); // вывод текста для панели lable в зависимости от текущего состояния игры
                panel.repaint(); // перерисовка игрового поля
            }
        });

        // устанавливаем размер игрового поля
        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE));
        add(panel); // добавляем игровое поле на форму
    }

    /**
     * Метод выводв текта с поле label в зависимости от состояния игры
     * @return
     */
    private String getMessage() {

        switch (game.getState()){    // определяем состояние игры и выводим текст
            case PLAYED:return "Think twice!";
            case BOMBED:return "You loose! BOOM!";
            case WINNER:return "Congratulations!";
            default:return "Welcome!";
        }
    }

    /**
     * Задаем характеристики окна(рамки) программы
     */
    private void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// функция закрытия программы по умолчанию
        setTitle("Java Sweeper"); // устанавливаем название
        pack();// устанавливает минимальный размер контейнера, которого достаточно для размещения всех компонентов
        setLocationRelativeTo(null);// расположение окна опрограммы по центру
        setResizable(false); //изменение размера окна
        setVisible(true); //устанавливаем видимость формы
        setIconImage(getImage("icon")); // добавление иконки в рамку
    }

    /**
     * Устанавливаем картинку для каждого экземпляра класса Box
     */
    private void setImages(){

        for(Box box:Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    /**
     * Метод получения картинки
     * @param name имя нужного изображения
     * @return изображение из папки RES
     */

    private Image getImage(String name){

        String filename = "img/"+name+".png"; // определяем имя файла для каждой картинки
        ImageIcon icon = new ImageIcon(getClass().getResource(filename)); // создаем объект из файла картинки
        return icon.getImage();
    }
}
