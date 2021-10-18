package com.company;

import java.util.Random;

public class WorkWithNumbers {

    /**
     * Получить рандомное число от a до b
     * @param a Начальное значение диапазона - "от"
     * @param b Конечное значение диапазона - "до"
     * @return рандомное число от a до b
     */
    public static int getRandomFromTo(int a, int b){
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

    /**
     * Создание массива int со случайными числами (здесь от 0.1 до 10.0) можно убрать и поставить
     * в переменные функции, но мне было лень
     * @param countOfElements - количество элементов, которые нужно сгенерировать
     * @return массив чисел
     */
    public static int[] getIntRandomArray(int countOfElements) {
        // double d = Math.random() * (max - min) + min;
        int[] randomArray = new int[countOfElements];
        for (int i = 0; i < countOfElements; i++){
            randomArray[i] = (int) (Math.random() * ((100 - 0) + 1)) + 0;
        }
        return randomArray;
    }

    /**
     * Создание массива double со случайными числами (здесь от 0.1 до 10.0) можно убрать и поставить
     * в переменные функции, но мне было лень
     * @param countOfElements - количество элементов, которые нужно сгенерировать
     * @return массив чисел
     */
    public static double[] getRandomArray(int countOfElements) {
        double[] randomArray = new double[countOfElements];
        double rangeMin = 0.1;
        double rangeMax = 10.0;
        Random r = new Random();
        for (int i = 0; i < countOfElements; i++){
            randomArray[i] = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            randomArray[i] = round(randomArray[i], 1);
        }
        return randomArray;
    }

    /**
     * Создание массива с заданным шагом от точки start до точки end
     * @param step - шаг
     * @param start - начальная точка
     * @param end - конечная точка
     * @return
     */
    public static double[] getArrayFromTo(double step, int start, int end){
        double[] result = new double[end - start];
        // здесь формируем массив с шагом
        double lastElement = 0; // последний сгенерированный признак
        for (int k = 0; k < end; k++){
            double currentElement = lastElement + step;  // считаем элмент

            // округляем до 2го знака
            double scale = Math.pow(10, 1);
            result[k] = Math.ceil(currentElement * scale) / scale;

            lastElement = result[k]; // последний сгенерированный признак
        }
        return result;
    }

    /**
     * создаем искомую функцию Y=ln(2x)
     * @param x - массив иксов
     * @return массив Y = искомая функция для ГА
     */
    public static double[] getFunction(double[] x){
        int countOfElements = x.length;
        double[] y = new double[countOfElements];

        for (int i = 0; i < countOfElements; i++){
            y[i] = 2 * Math.log10(x[i]);
        }

        return y;
    }

    /**
     * Округление
     * @param value число, которое нужно округлить
     * @param precision - до какого знака
     * @return округленное число
     */
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


}
