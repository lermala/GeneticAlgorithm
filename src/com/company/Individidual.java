package com.company;

import java.util.Arrays;

public class Individidual implements Comparable<Individidual>{
    int numberOfSigns; // количество признаков
    double[] signs; // массив признаков для текущей особи

    double euclidDist;

    public Individidual(int numberOfSigns) {
        this.numberOfSigns = numberOfSigns;
        signs = new double[numberOfSigns];
    }

    public Individidual() {
    }

    public void setSigns(double[] signs) {
        this.signs = signs;
    }

    /**
     * СКРЕЩИВАНИЕ (одноточечный оператор кроссинговера)
     * 1. случайным образом определяется разрезающая точка оператора кроссинговера idOfCuttingPoint.
     * Эта точка определяет место в двух хромосомах, где они должны быть «разрезаны».
     * 2. Меняем элементы после точки оператора кроссинговера между двумя родителями. Так получаем два новых потомка.
     * @param parent1 - первый родитель
     * @param parent2 - второй родитель
     * @return individiduals - результат скрещивания (два потомка).
     */
    public static Individidual[] crossoverBySinglePoint(Individidual parent1, Individidual parent2){
        int countOfSigns = parent1.getNumberOfSigns(); // количество признаков в особи

        // точка разреза, после которой выполняется разрез хромосомы (получаем рандомно от 0 до последнего признака)
        int idOfCuttingPoint = WorkWithNumbers.getRandomFromTo(0, countOfSigns);
        System.out.println("CUT= " + idOfCuttingPoint);

        // создаем два потомка
        Individidual child1 = new Individidual(countOfSigns);
        Individidual child2 = new Individidual(countOfSigns);

        // до точки разрыва признаки оставляем те же
        for (int i = 0; i < idOfCuttingPoint; i++){
            child1.signs[i] = parent1.signs[i];
            child2.signs[i] = parent2.signs[i];
        }

        // После точки разрыва признаки меняем
        for (int i = idOfCuttingPoint; i < countOfSigns; i++){
            child1.signs[i] = parent2.signs[i];
            child2.signs[i] = parent1.signs[i];
        }

        // Записываем детей в массив и возвращаем
        Individidual[] individiduals = {child1, child2};
        return individiduals;
    }

    /**
     * СКРЕЩИВАНИЕ (двуточечный оператор кроссинговера)
     * В каждой хромосоме определяются две точки оператора кроссинговера,
     * хромосомы обмениваются участками, расположенными между двумя точками оператора кроссинговера.
     * Точки оператора кроссинговера в двухточечном операторе кроссинговера также определяются случайно.
     * @param parent1 - первый родитель
     * @param parent2 - второй родитель
     * @return individiduals - результат скрещивания (два потомка).
     */
    public static Individidual[] crossoverByTwoPoints(Individidual parent1, Individidual parent2){
        int countOfSigns = parent1.getNumberOfSigns(); // количество признаков в особи

        // точка разреза, после которой выполняется разрез хромосомы (получаем рандомно от 1 до последнего признака)
        int idOfCuttingPoint1 = WorkWithNumbers.getRandomFromTo(1, countOfSigns);
        int idOfCuttingPoint2 = WorkWithNumbers.getRandomFromTo(1, countOfSigns);

        // определяем левую (меньшую) точку
        int leftPoint, rightPoint;
        if (idOfCuttingPoint1 < idOfCuttingPoint2){
            leftPoint = idOfCuttingPoint1;
            rightPoint = idOfCuttingPoint2;
        } else if (idOfCuttingPoint1 == idOfCuttingPoint2){
            leftPoint = idOfCuttingPoint1 - 1;
            rightPoint = idOfCuttingPoint2;
        } else {
            leftPoint = idOfCuttingPoint2;
            rightPoint = idOfCuttingPoint1;
        }

        // создаем два потомка
        Individidual child1 = new Individidual(countOfSigns);
        Individidual child2 = new Individidual(countOfSigns);

        // до точки разрыва признаки оставляем те же
        for (int i = 0; i < leftPoint; i++){
            child1.signs[i] = parent1.signs[i];
            child2.signs[i] = parent2.signs[i];
        }

        // После точки разрыва до следующей точки признаки меняем
        for (int i = leftPoint; i < rightPoint; i++){
            child1.signs[i] = parent2.signs[i];
            child2.signs[i] = parent1.signs[i];
        }

        // После правой точки разрыва признаки оставляем те же
        for (int i = rightPoint; i < countOfSigns; i++){
            child1.signs[i] = parent1.signs[i];
            child2.signs[i] = parent2.signs[i];
        }

        // Записываем детей в массив и возвращаем
        Individidual[] individiduals = {child1, child2};
        return individiduals;
    }

    /**
     * СКРЕЩИВАНИЕ (двуточечный оператор кроссинговера)
     * Здесь точки оператора кроссинговера делят хромосому на ряд строительных блоков (в данном случае 4).
     * Потомок Р1’ образуется из нечетных блоков родителя Р1 (1 и 3) и четных блоков родителя Р2 (2 и 4).
     * Потомок Р2' образуется соответственно из нечетных блоков родителя Р2 и четных блоков родителя P1.
     * @param parent1 - первый родитель
     * @param parent2 - второй родитель
     * @return individiduals - результат скрещивания (два потомка).
     */
    public static Individidual[] crossoverByThreePoints(Individidual parent1, Individidual parent2){
        int countOfSigns = parent1.getNumberOfSigns(); // количество признаков в особи

        // точка разреза, после которой выполняется разрез хромосомы (получаем рандомно от 1 до последнего признака)
        int[] points = WorkWithNumbers.getIntRandomArray(3);

        int leftPoint = points[0], rightPoint = points[0], midPoint = points[0];
        for (int i = 0; i < points.length; i++){
            if (points[i] <= leftPoint){
                leftPoint = points[i];
            } else if (points[i] >= rightPoint){
                rightPoint = points[i];
            } else {
                midPoint = points[i];
            }
        }

        // создаем два потомка
        Individidual child1 = new Individidual(countOfSigns);
        Individidual child2 = new Individidual(countOfSigns);

        // до точки разрыва признаки оставляем те же
        for (int i = 0; i < leftPoint; i++){
            child1.signs[i] = parent1.signs[i];
            child2.signs[i] = parent2.signs[i];
        }

        // до точки разрыва признаки оставляем те же
        for (int i = leftPoint; i < midPoint; i++){
            child1.signs[i] = parent2.signs[i];
            child2.signs[i] = parent1.signs[i];
        }

        // После точки разрыва до следующей точки признаки меняем
        for (int i = midPoint; i < rightPoint; i++){
            child1.signs[i] = parent1.signs[i];
            child2.signs[i] = parent2.signs[i];
        }

        // После правой точки разрыва признаки оставляем те же
        for (int i = rightPoint; i < countOfSigns; i++){
            child1.signs[i] = parent2.signs[i];
            child2.signs[i] = parent1.signs[i];
        }

        // Записываем детей в массив и возвращаем
        Individidual[] individiduals = {child1, child2};
        return individiduals;
    }

    /**
     * Оператор мутации — языковая конструкция, позволяющая на основе преобразования
     * родительской хромосомы (или ее части) создавать хромосому потомка.
     *
     * 1.	В хромосоме А = (а1, а2, а3,	aL-2, aL-1, aL)  определяются случайным образом две позиции (например, а2 и aL-1).
     * 2. Гены, соответствующие выбранным позициям, переставляются, и формируется новая хромосома
     * А' = (а1, аL-1, a3, ••• ,аL-2, a2, aL)
     * @return мутировавшая особь
     */
    public void mutateByTwoPoints(){
        int size = numberOfSigns; // количество признаков в особи

        // определяются случайным образом две позиции
        int position1 = WorkWithNumbers.getRandomFromTo(0, size);
        int position2 = WorkWithNumbers.getRandomFromTo(0, size);

        // Гены, соответствующие выбранным позициям, переставляются
        double temp = signs[position1];
        signs[position1] = signs[position2];
        signs[position2] = temp;
    }

    /**
     * Оператор мутации — языковая конструкция, позволяющая на основе преобразования
     * родительской хромосомы (или ее части) создавать хромосому потомка.
     *
     * 1.	В хромосоме А = (а1, а2, а3,	aL-2, aL-1, aL)  определяются случайным образом две позиции (например, а2 и aL-1).
     * 2. Гены, соответствующие выбранным позициям, переставляются, и формируется новая хромосома
     * А' = (а1, аL-1, a3, ••• ,аL-2, a2, aL)
     * @return мутировавшая особь
     */
    public void mutateByThreePoints(){
        int size = numberOfSigns; // количество признаков в особи

        // определяются случайным образом две позиции
        int position1 = WorkWithNumbers.getRandomFromTo(0, size);
        int position2 = WorkWithNumbers.getRandomFromTo(0, size);
        int position3 = WorkWithNumbers.getRandomFromTo(0, size);

        // Гены, соответствующие выбранным позициям, переставляются
        double temp = signs[position1];
        signs[position1] = signs[position3];
        signs[position3] = signs[position2];
        signs[position2] = temp;
    }

    public int getNumberOfSigns() {
        return numberOfSigns;
    }

    public double[] getSigns() {
        return signs;
    }

    /**
     * Считаем эвклидово расстояние между признаками и искомой функцией
     * @param requiredFunction - искомая функция
     * @return
     */
    public void getEuclidDistanceForSignsWithRequiredFunc(double[] requiredFunction) {
        double[] A = WorkWithNumbers.getFunction(signs); // т. к. признаки - это значение Х, то считаем У для них
        euclidDist = WorkWithNumbers.getEuclideanDistance(A, requiredFunction);
    }

    @Override
    public String toString() {
        return "Individidual{" +
                "signs=" + Arrays.toString(signs) +
                ", euclidDist=" + euclidDist +
                '}';
    }

    public double getEuclidDist() {
        return euclidDist;
    }


    @Override
    public int compareTo(Individidual ind) {
        if (this.getEuclidDist() < ind.getEuclidDist()){
            return -1;
        }else{
            return 1;
        }
    }
}
