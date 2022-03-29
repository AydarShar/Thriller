/* Проект написан исключительно с целью набить руку и понимать проходимый материал.
Я просмотрел туториал
урок 1 https://www.youtube.com/watch?v=ozq32dlAn2E
урок 2 https://www.youtube.com/watch?v=z8fBapvfH4g
и вместе с преподавателем писал код.
Проверки с использованием xi yi понял не очень, всё остальное более-менее.

Нашел бота для игры в морской бой https://ru.stackoverflow.com/questions/1242401/%D0%91%D0%BE%D1%82-%D0%B4%D0%BB%D1%8F-%D0%B8%D0%B3%D1%80%D1%8B-%D0%B2-%D0%9C%D0%BE%D1%80%D1%81%D0%BA%D0%BE%D0%B9-%D0%B1%D0%BE%D0%B9
Было бы интересно внедрить его вместо второго игрока. Но боюсь для меня это вне сроков нашего курса.)
 */

import java.io.IOException;
import java.util.Scanner;

public class Battleship {
    // Глобальные переменные:
    static String player1; // создаем игрока
    static String player2; // computer bot??
    static Scanner sc = new Scanner(System.in); // создаем сканер, он считывает инфо вводимое игроками.
    static int[][] battlefield1 = new int[10][10]; // вложенный массив поля c кораблями игрока 1.
    static int[][] battlefield2 = new int[10][10]; // вложенный массив поля с кораблями игрока 2.
    static int[][] monitor1 = new int[10][10]; // вложенный массив поля для отображения выстрелов 1 игрока.
    static int[][] monitor2 = new int[10][10]; // вложенный массив поля для отображения выстрелов 2 игрока.

    public static void main(String[] args) {
        System.out.println("Enter your name below, captain №1: ");
        player1 = sc.nextLine(); // вводимое значение
        System.out.println("Enter your name below, captain №2: ");
        player2 = sc.nextLine();
        placeShips(player1, battlefield1);
        placeShips(player2, battlefield2); // Вызвал метод расстановки кораблей игрока.
        boolean endGame = isWinCondition(); // Для себя для наглядности оставил.
        while (true) {
            makeTurn(player1, monitor1, battlefield2); // Вызвал метод хода. Параметры: имя игрока1, его выстрелы и поле противника.
            if (isWinCondition()) {
                break;
            }
            makeTurn(player2, monitor2, battlefield1); // Вызвал метод хода. Параметры: имя игрока2, его выстрелы и поле противника.
            if (isWinCondition()) {
                break;
            }
        }
    }


    public static void placeShips(String player, int[][] battlefield) { // имя игрока и его поле с его кораблями
        int deck = 4;
        while (deck >= 1) {
            System.out.println("Take command, captain " + player + "! And place your " + deck + " deck ship: ");
            System.out.println();

            drawField(battlefield); //отображение поля боя

            System.out.println("Enter OX coordinate: ");
            int x = sc.nextInt();
            System.out.println("Enter OY coordinate: ");
            int y = sc.nextInt();
            System.out.println("Choose direction: ");
            System.out.println("1. Vertical");
            System.out.println("2. Horizontal");
            int direction = sc.nextInt();
            if (!isAvailable(x, y, deck, direction, battlefield)) {
                System.out.println("Wrong coordinates!");
                continue; // прерывает и начинает сначала текущую итерацию
            }

            for (int i = 0; i < deck; i++) {
                if (direction == 1) {
                    battlefield[x][y + i] = 1;
                } else {
                    battlefield[x + i][y] = 1;
                }
            }

            deck--; // счетчик палуб уменьшаем после каждой итерации
            clearScreen();
        }
    }

    public static void drawField(int[][] battlefield) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < battlefield.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < battlefield[1].length; j++) {
                if (battlefield[j][i] == 0) { // Инверсия. Нужно поменять местами переменные в цикле вложенного массива, чтобы всё отображалось без разворота на 90°.
                    System.out.print("? ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    public static void makeTurn(String player, int[][] monitor, int[][] battlefield) { //Поле куда стреляет игрок. Параметры: имя игрока, его монитор, поле противника
        while (true) { // цикл вместо рекурсии (посмотреть). Бесконечный цикл.
            System.out.println(player + " , make your move.");
            System.out.println("  0 1 2 3 4 5 6 7 8 9");
            for (int i = 0; i < monitor.length; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < monitor[1].length; j++) {
                    if (monitor[j][i] == 0) {
                        System.out.print("? "); // не стрелял (массив с 0)
                    } else if (monitor[j][i] == 1) {
                        System.out.print(". "); // выстрел и не попал
                    } else {
                        System.out.print("X "); // выстрел и попадание
                    }
                }
                System.out.println();
            }
            System.out.println("Enter OX coordinate: ");
            int x = sc.nextInt();
            System.out.println("Enter OY coordinate: ");
            int y = sc.nextInt();
            if (battlefield[x][y] == 1) { // условие попал игрок или нет.
                System.out.println("Hit! Shoot again!");
                monitor[x][y] = 2;
            } else { // рекурсия? почитать как воспроизваодить
                System.out.println("Miss! Your opponents turn! ");
                monitor[x][y] = 1;
                break; // прервали бесконечный цикл
            }
            clearScreen();
        }
    }

    public static boolean isWinCondition() {
        int counter1 = 0; // счетчик подбитых палуб игрока 1
        for (int i = 0; i < monitor1.length; i++) {
            for (int j = 0; j < monitor1[i].length; j++) {
                if (monitor1[i][j] == 2) {
                    counter1++;
                }
            }
        }
        int counter2 = 0; // счетчик подбитых палуб игрока 2
        for (int i = 0; i < monitor2.length; i++) {
            for (int j = 0; j < monitor2[i].length; j++) {
                if (monitor2[i][j] == 2) {
                    counter1++;
                }
            }
        }
        if (counter1 >= 10) {
            System.out.println(player1 + "WIN!");
            return true; // Возвращая значение true - метод закончил работу. Игра закончилась. Игрок 1 выиграл.
        }
        if (counter2 >= 10) {
            System.out.println(player2 + "WIN!");
            return true; // Останавливает выполнение метода, если игрок 2 выиграл. Игра закончилась. Игрок 2 выиграл.
        }
        return false; // Если ни один из счетчиков не насчитал 10 подбитых палуб противника - стоп игра.
    }

    public static boolean isAvailable(int x, int y, int deck, int rotation, int[][] battlefield) {
        // корабль не выходит за границы оси x или y
        if (rotation == 1) {
            if (y + deck > battlefield.length) {
                return false;
            }
        }
        if (rotation == 2) {
            if (x + deck > battlefield[0].length) {
                return false;
            }
        }
        // проверка на то, что по соседству нет других кораблей
        while (deck != 0) {
            for (int i = 0; i < deck; i++) { // отклонение от начальной точки, которое зависит от текщей дальты, которую мы проверяем
                int xi = 0;
                int yi = 0;
                if (rotation == 1) { // если направление 1 | 2
                    yi = i; // то проверяем вот эту палубу по этому же направлению
                } else {
                    xi = i; // то насколько нужно сместить проверку по x
                }
                // battlefield [x][y];
                if (x + 1 + xi < battlefield.length && x + 1 + xi >= 0) {
                    if (battlefield[x + 1 + xi][y + yi] != 0) {
                        return false;
                    }
                }
                if (x - 1 + xi < battlefield.length && x - 1 + xi >= 0) {
                    if (battlefield[x - 1 + xi][y + yi] != 0) {
                        return false;
                    }
                }
                if (y + 1 + yi < battlefield.length && y + 1 + yi >= 0) {
                    if (battlefield[x + xi][y + 1 + yi] != 0) {
                        return false;
                    }
                }
                if (y - 1 + yi < battlefield.length && y - 1 + yi >= 0) {
                    if (battlefield[x + xi][y - 1 + yi] != 0) {
                        return false;
                    }
                }
            }
            deck--; //счетчик палуб уменьшается после каждой итерации
        }
        return true;
    }
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //не нашел аналога для Mac OS
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}


