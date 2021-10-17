package com.company;

import com.sun.xml.internal.ws.commons.xmlutil.Converter;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    // вариант 10 Y=ln(2x)

    public static void main(String[] args) {
        // 1. Получаем рандомные х из отрезка от 0 до 10
        double[] x = getRandomArray(100);
        //System.out.println(printArray(x));

       // System.out.println("************* Y = *************");

        // Получаем Y=ln(2x)
        double[] y = createFunction(x);
        //System.out.println(printArray(y));

        // 2. Создать популяцию из 20 особей, со ста признаками, соответствующими отрезку
        // [0..10] (интервал между признаками = 0,1)
        Population population = new Population(20, 100);
        population.createPopulation2(); // заполняем популяцию особями

        System.out.println("************* POPULATION *************");
        System.out.println(population.toString());

        // Здесь в цикле будем размножать, уничтожать особи до тех пор, пока эвклидово расстояние не будет равно 0,5.
        final double EUCLIDDIST = 0.5; // Остановить мутационный процесс при достижении эвклидова расстояния = 0,5.
        double minEuclid = 0.6; // в начале ставим 0.6, так как эвклидово расстояние для особей еще не посчитано
        Individidual indWithMinEuclidDist = population.get(0); // особь с минимальным эвклидовым расстоянием
//        do {
//            // 3. Создать алгоритм, размножения и мутации, способный на каждом шаге удваивать популяцию.
//            population.crossoverPop();
//
//            System.out.println("************* CROSSOVERED POPULATION *************");
//            System.out.println(population.toString());
//
//            // 4. Уничтожать половину удвоенной популяции, эвклидово расстояние признаков
//            // которых максимально удалено от искомой функции.
//            population.delete();
//
//            System.out.println("************* DELETED POPULATION *************");
//            for (int i = 0; i < population.size(); i++){
//                Individidual ind = population.get(i);
//                System.out.println(i + ") " + ind.toString() + " euclid=" + ind.getEuclidDist());
//            }
//
//            // Ищем минимальное эвклидовое расстояние
//            indWithMinEuclidDist = population.getIndWithMinEuclidDist(); // особь с min dist
//            minEuclid = indWithMinEuclidDist.getEuclidDist();
//
//        } while (minEuclid > EUCLIDDIST); // 5. Остановить мутационный процесс при достижении эвклидова расстояния = 0,5.

        // 6. Вывести признаки особи с наименьшим эвклидовым расстоянием в виде графика и
        // рядом график функции для сравнения.
//        System.out.println("MIN= " + indWithMinEuclidDist.toString());



    }

    public static double[] getRandomArray(int countOfElements) {
        double[] randomArray = new double[countOfElements];
        for (int i = 0; i < countOfElements; i++){
            randomArray[i] = (Math.random() * ((9 - 0) + 1)) + 0;
        }
        return randomArray;
    }

    public static String printArray(double[] array){
        String result = "";
        for (int i = 0; i < array.length; i++){
            result += Double.toString(array[i]) + '\n';
        }
        return result;
    }

    // создаем искомую функцию Y=ln(2x)
    public static double[] createFunction(double[] x){
        int countOfElements = x.length;
        double[] y = new double[countOfElements];

        for (int i = 0; i < countOfElements; i++){
            y[i] = 2 * Math.log10(x[i]);
        }

        return y;
    }

    /**
     *  Создать популяцию из 20 особей, со ста признаками, соответствующими отрезку
     *  [0..10] (интервал между признаками = 0,1)
     * @param numberOfIndividual - количество особей (20)
     * @param numberOfSigns - количество признаков (100)
     * @return
     */
    public static ArrayList<double[]> createPopulation(int numberOfIndividual, int numberOfSigns){
        ArrayList<double[]> population = new ArrayList<>();

        // создаем 20 особей, у каждой 100 признаков
        double lastElement = 0; // последний сгенерированный признак
        for (int i = 0; i < numberOfIndividual; i++){

            // здесь формируем массив признков с шагом 0.1
            double[] signs = new double[numberOfSigns]; // массив признаков для текущей особи
            //signs[0] = lastElement; // присваиваем первому элементу текущего массива последний элемент прошлого массива
            for (int k = 0; k < numberOfSigns; k++){
                double step = 0.1;
                signs[k] = lastElement + step;
                lastElement = signs[k]; // последний сгенерированный признак
            }

            population.add(signs); // добавляем в популяцию массив со ста признаками
        }

        return population;
    }


}
