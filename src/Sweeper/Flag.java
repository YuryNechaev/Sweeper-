package Sweeper;

class Flag {

    private Matrix flagMap;
    private int countOfClosedBoxes;

    /**
     * Старт игры и отображение состояния верхнего слоя
     */
    void start(){
         flagMap = new Matrix(Box.CLOSED);
         countOfClosedBoxes = Ranges.getSize().x*Ranges.getSize().y; // при старте игры присваиваем начальное значение переменной

    }
    Box get (Coord coord){

        return flagMap.get(coord);
    }

    /**
     * Метод открытия клетки при нажатии мышки
     * @param coord
     */
    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;  // уменьшаем количество закрытых клеток при открытии
    }

    /**
     * Метод проверки наличия флажка при нажатии правой кнопки мышки и выполнение
     * соответствующего действия
     * @param coord
     */
    void toggleFlagedToBox(Coord coord) {

        switch (flagMap.get(coord)){
            case FLAGED:setClosedToBox(coord); break;
            case CLOSED:setFlagedToBox(coord); break;
        }

    }
    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);

    }

    int getCountOfClosedBoxes() {

        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord) {

        if(flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);

    }

    void setNoBombToFlagedSafeBox(Coord coord) {

        if(flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);

    }


    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for(Coord around:Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.FLAGED)
                count++;
            return count;
    }
}
