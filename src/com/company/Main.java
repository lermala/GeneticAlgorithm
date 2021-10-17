package com.company;


public class Main {
    // вариант 10 Y=ln(2x)

    public static void main(String[] args) {
        // 1. Получаем рандомные х из отрезка от 0 до 10


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




}
