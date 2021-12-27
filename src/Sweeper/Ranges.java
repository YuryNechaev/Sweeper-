package Sweeper;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс для хранения размеров поля
 */

public class Ranges {

    private static Coord size; // хранит в себе размеры поля
    private static ArrayList<Coord> allCoords; // переменная для перебора всех координат в списке
    private static Random random = new Random(); //генератор случайных чисел для генерации случайных координат

    /**
     * Метод для установки размеров поля
     * @param _size
     */
    public static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList<Coord>();// заполняем список всех координат
        for (int y = 0; y < size.y; y++)
            for (int x = 0; x < size.x; x++)
                allCoords.add(new Coord(x, y));
    }

    public static Coord getSize() {
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    /**
     * Метод определяющий, находится ли клетка в пределах игрового поля
     * @param coord
     * @return
     */
    static boolean inRange(Coord coord) {
        return coord.x >= 0 && coord.x < size.x && coord.y >= 0 && coord.y < size.y;
    }

    /**
     * Метод получения случайных координат
     * @return
     */
    static Coord getRandomCoard() {

        return new Coord(random.nextInt(size.x), random.nextInt(size.y));

    }

    /**
     * Метод для получения координат вокруг какой либо клетки
     * @param coord клетка, вокруг которой проверяем координаты
     * @return
     */
    static ArrayList<Coord> getCoordsAround(Coord coord) {

        Coord around;
        ArrayList<Coord> list = new ArrayList<Coord>(); // список координато, которые будут вокруг
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++)
                if(inRange(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        list.add(around);
                    return list;
    }
}
