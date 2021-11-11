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
     * Общая формула евклидова расстояния для 2-мерного случая
     * @param x - первая точка
     * @param y - вторая точка
     * @return
     */
    public static double getEuclideanDistance(double x, double y){
        double d;
        d = Math.pow( (x - y), 2 );
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
     * @param rangeMin - от этого числа начнется генериция
     * @param rangeMax - до этого числа будем генерировать
     * @return массив чисел
     */
    public static double[] getRandomArray(int countOfElements, double rangeMin, double rangeMax) {
        double[] randomArray = new double[countOfElements];
        Random r = new Random();

        for(int i = 0; i < countOfElements; ++i) {
            randomArray[i] = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            randomArray[i] = round(randomArray[i], 2);
        }

        return randomArray;
    }

    /**
     * Создание массива с заданным шагом от точки start до точки end
     * @param step - шаг
     * @param start - начальная точка
     * @param countOfElements - кол-во элементов, которое нужно сгенерить
     * @return
     */
    public static double[] getArrayFromTo(double step, double start, int countOfElements){
        double[] result = new double[countOfElements];
        result[0] = start;

        for (int k = 1; k < countOfElements; k++){
            result[k] = result[k - 1] + step; // считаем значение прибавив к нему шаг
            result[k] = round(result[k], 1); // округляем до одного знака
        }
        return result;
    }

    /**
     * создаем искомую функцию Y=ln(2x)
     * @param x - массив иксов
     * @return массив Y = искомая функция для ГА
     */
    public static double[] getFunction(double[] x){
        // задаем размер массива
        int countOfElements = x.length;
        double[] y = new double[countOfElements];

        // здесь заполняем массив. Y=ln(2x)
        for (int i = 0; i < countOfElements; i++){
            y[i] = Math.log10(x[i] * 2); // TODO: поменять эту строчку. Здесь будет функция по твоему варианту
            y[i] = round(y[i], 2);
        }

        return y;
    }

    /**
     * Округление
     * @param value число, которое нужно округлить
     * @param precision - до какого знака
     * @return округленное число
     */
    static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


}
