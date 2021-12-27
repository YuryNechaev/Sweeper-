package Sweeper;

/**
 * Класс для работы с нижним слоем поля (BOMB, BOMBED, NOBOMB,ZZERO, NUMBERS)
 */
class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs){ //конструктор принимает кол-во бомб

        this.totalBombs = totalBombs; // конструктор сохраняет кол-во бомб
        fixBombsCount();  //контроль кол-ва бомб
    }

    void start(){  // размещаем бомбы заново при перезапуске
        bombMap = new Matrix(Box.ZERO);
        for(int i = 0; i < totalBombs; i++)  //размещаем бомбы с использованием цикла
        placeBomb();
    }

    Box get (Coord coord){

        return bombMap.get(coord);
    }

    /**
     * Метод ограничения максимального значения бомб
     */
    private void fixBombsCount(){
        int maxBombs = Ranges.getSize().x*Ranges.getSize().y/2;
        if (totalBombs>maxBombs)
        totalBombs=maxBombs;

    }
    private void placeBomb() {

        while (true) {
            Coord coord = Ranges.getRandomCoard();
            if (Box.BOMB == bombMap.get(coord))//проверка на наличие бомбы в ячейке при ее установке, чтобы не установить ее дважды
                continue;
            bombMap.set(coord, Box.BOMB); // размещаем бомбу в случайном месте
            incNumbersAroundBomb(coord);
            break;
        }
    }

    /**
     * Метод увеличивает значение числе вокруг бомбы
     * @param coord
     */

    private void incNumbersAroundBomb(Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord))
            if(Box.BOMB != bombMap.get(around))
            bombMap.set(around, bombMap.get(around).getNextNumberBox());

    }


    int getTotalBombs() {

        return totalBombs;
    }
}

