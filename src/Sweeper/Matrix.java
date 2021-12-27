package Sweeper;

/**
 * Метод для хранения нижнего и верхнего слоев игрового поля
 */
class Matrix {
    private Box[][] matrix;

    Matrix(Box defaultBox) { // конструктор создает матрицу и заполняет значениями переданного параметра
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords())
            matrix[coord.x][coord.y] = defaultBox;
    }

    Box get(Coord coord) { // геттер указанных координат
        if (Ranges.inRange(coord))  //проверка на переполнение массива
            return matrix[coord.x][coord.y];
        return null;
    }

    void set(Coord coord, Box box) { // сеттер указанных координат

        if (Ranges.inRange(coord))  //проверка на переполнение массива
            matrix[coord.x][coord.y] = box;

    }
}
