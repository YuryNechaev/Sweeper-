package Sweeper;

public class Game {

    private final Bomb bomb;
    private final Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs) { // конструктор принимает кол-во стольбцов, строк и бомб

        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }
    public void start(){

        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    /**
     * Метод определяет, что отобразить в том или ином месте экрана
     * @param coord
     * @return
     */
    public Box getBox(Coord coord) {

        if (flag.get(coord) == Box.OPENED)  // логика открытия верхнего и нижнего слоя
            return bomb.get(coord);
        else
            return flag.get(coord);

    }

    public void pressLeftButton(Coord coord) {

        if(gameOver()) return;
        openBox(coord);
        checkWinner();  //после каждого открытия окошка, запускаем проверку на выйгрыш
    }

    /**
     * Метод проверки на выйгрыш. Сравнивает количество закрытых клеток с количеством бомб.
     */
    private void checkWinner(){

        if(state == GameState.PLAYED) // если мы еще в игре
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs())  //если количество закрытых клеток совпадает с количеством бомб
                state = GameState.WINNER;
    }
    private void openBox(Coord coord){

        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED:return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO:openBoxesAround (coord);return;
                    case BOMB: openBombs(coord); return;
                    default: flag.setOpenedToBox(coord);return;
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {

        if(bomb.get(coord) != Box.BOMB)
        if(flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
            for (Coord around:Ranges.getCoordsAround(coord))
                if(flag.get(around) == Box.CLOSED)
                    openBox(around);


    }

    private void openBombs(Coord bombed) {

        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for(Coord coord:Ranges.getAllCoords())
            if(bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox(coord); //открывает закрытую клеку с бомбой
        else
            flag.setNoBombToFlagedSafeBox(coord);

    }

    /**
     * Метод для открытия пустых клеток вокруг текущей клетки(рекурсия)
     * @param coord
     */
    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for(Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {

        if(gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {

        if(state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
