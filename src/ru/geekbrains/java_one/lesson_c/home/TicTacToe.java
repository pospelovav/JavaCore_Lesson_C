package ru.geekbrains.java_one.lesson_c.home;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    //init field
    private static void initField() {
        fieldSizeY = 5;
        fieldSizeX = 5;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    // printField
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++)
            System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
        System.out.println();

        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < fieldSizeX; j++)
                System.out.print(field[i][j] + "|");
            System.out.println();
        }

        for (int i = 0; i <= fieldSizeX * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }

    // humanTurn
    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("Введите координаты хода X и Y (от 1 до " + fieldSizeX + ") через пробел >>> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    // aiTurn
    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    // checkWin
    private static boolean checkWin(char c) {
        char[][] checkDiag = new char[2][fieldSizeX];   //массив для диагоналей

        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < fieldSizeY; l++) {
                for (int i = 0, fixCheck = 0; i < fieldSizeX; i++) {
                    if (k == 0){            //горизонтали
                        if (field[l][i] == c){
                            fixCheck ++;
                        }
                        if ((i == fieldSizeX-1) || (field[l][i] != c)) {
                            if (fixCheck < fieldSizeX-1) {  //количество элементов подряд (размерность - 1)
                                fixCheck = 0;
                            } else {return true;}
                        }
                    }
                    if (k == 1){        //вертикали
                        if (field[i][l] == c){
                            fixCheck ++;
                        }
                        if ((i == fieldSizeX-1) || (field[i][l] != c)) {
                            if (fixCheck < fieldSizeX-1) {  //количество элементов подряд (размерность - 1)
                                fixCheck = 0;
                            } else {return true;}
                        }
                    }

                }
            }

            for (int i = 0; i < fieldSizeX; i++) {                       //диагонали
                checkDiag[0][i] = field[i][i];
                checkDiag[1][i] = field[i][fieldSizeX - i - 1];
            }
            for (int g = 0; g < 2; g++) {
                for (int j = 0, fixCheck = 0; j < fieldSizeX; j++) {
                    if (checkDiag[g][j] == c){
                        fixCheck ++;
                    }
                    if ((j == fieldSizeX-1) || (checkDiag[g][j] != c)) {
                        if (fixCheck < fieldSizeX-1) {  //количество элементов подряд (размерность - 1)
                            fixCheck = 0;
                        } else {return true;}
                    }
                }
            }


        }


        return false;
    }

    //checkDraw
    private static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    private static boolean gameChecks(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            initField();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (gameChecks(DOT_HUMAN, "Human win")) break;
                //aiTurn();
                //printField();
                if (gameChecks(DOT_AI, "Computer win")) break;
            }
            System.out.println("To play again enter 'y'");
            if (!SCANNER.next().equals("y"))
                break;
        }
    }


}
