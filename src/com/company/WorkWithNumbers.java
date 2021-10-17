package com.company;

public class WorkWithNumbers {

    public static int getRandomFromTo(int a, int b){
        // a = Начальное значение диапазона - "от"
        // b = Конечное значение диапазона - "до"
        int random_number = a + (int) (Math.random() * b); // Генерация числа
        return random_number;
    }

    /**
     * Общая формула евклидова расстояния для n-мерного случая (n переменных)  (https://studopedia.info/5-63128.html)
     * @param x - первая точка
     * @param y - вторая точка
     * @return
     */
    public static double getEuclideanDistance(double[] x, double[] y){
        double d = 0;

        for (int i = 0; i < x.length; i++){
            d += Math.pow( ( x[i] - y[i] ), 2 );
        }
        d = Math.sqrt(d);

        return d;
    }

    public static double[] getRandomArray(int countOfElements) {
        double[] randomArray = new double[countOfElements];
        for (int i = 0; i < countOfElements; i++){
            randomArray[i] = (Math.random() * ((9 - 0) + 1)) + 0;
        }
        return randomArray;
    }

    public static int[] getIntRandomArray(int countOfElements) {
        int[] randomArray = new int[countOfElements];
        for (int i = 0; i < countOfElements; i++){
            randomArray[i] = (int) (Math.random() * ((100 - 0) + 1)) + 0;
        }
        return randomArray;
    }

    // создаем искомую функцию Y=ln(2x)
    public static double[] getFunction(double[] x){
        int countOfElements = x.length;
        double[] y = new double[countOfElements];

        for (int i = 0; i < countOfElements; i++){
            y[i] = 2 * Math.log10(x[i]);
        }

        return y;
    }

}
