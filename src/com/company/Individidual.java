package com.company;

import java.util.Arrays;

public class Individidual implements Comparable<Individidual>{
    int numberOfSigns; // количество признаков
    double[] signs; // массив признаков для текущей особи

    double euclidDist; // эвклидово расстояние между особью и искомой функцией

    public Individidual(int numberOfSigns) {
        this.numberOfSigns = numberOfSigns;
        signs = new double[numberOfSigns];

//        // считаем х для У. х
//        double[] x2 = new double[y2.length];
//        x2[0] = 0;
//        for (int i = 1; i < y2.length; i++){
//            x2[i] = x2[i-1] + 0.1;
//            x2[i] = WorkWithNumbers.round(x2[i], 1);
//            // System.out.println(x2[i]);
//        }
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

        // определяются случайным образом 3 позиции
        int position1 = WorkWithNumbers.getRandomFromTo(0, size);
        int position2 = WorkWithNumbers.getRandomFromTo(0, size);
        int position3 = WorkWithNumbers.getRandomFromTo(0, size);

        // Гены, соответствующие выбранным позициям, переставляются
        double temp = signs[position1];
        signs[position1] = signs[position3];
        signs[position3] = signs[position2];
        signs[position2] = temp;
    }

    public void mutateByChanging1() {
        int size = this.numberOfSigns;
        int position1 = WorkWithNumbers.getRandomFromTo(0, size);
        int position2 = WorkWithNumbers.getRandomFromTo(0, size);
        int position3 = WorkWithNumbers.getRandomFromTo(0, size);
        this.signs[position1] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2.0, 2.), 2);
        this.signs[position2] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);
        this.signs[position3] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);
    }

    public void mutateByChanging2(int countOfChanging) {
        int size = this.numberOfSigns;

        for(int i = 0; i < countOfChanging; ++i) {
            this.signs[i] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);
        }

    }

    /**
     * Мутация в зависимости от эклидова расстояния. При минимальном - не мутируем.
     */
    public void mutateFromEuclid(double[] fitnessFunc){
        //double[] x = WorkWithNumbers.getArrayFromTo(0.1, 0, 100);

        // 1. считаем эвклидово расстояние для каждого из 10 участков для fitness function
        double[] euclidForGroups = new double[10]; // эвклидово расстояние для каждого из 10 участков для fitness function
        int startId = 0; // =0 так как отсчет начинаем в нулевого элемента и заканчиваем 10 - это первая группа
        int endId = 10; // =10 так как отсчет заканчиваем на 10 - это первая группа

        // в цикле проходим по каждому отрезку и считаем значение эвклидового расстояния
        for (int i = 0; i < euclidForGroups.length; i++){
            //double[] curXforFitness = new double[10]; // текущий отрезок по оси Х для искомой функции (fitness)
            // и для признаков тоже, так как у них иксы совпадают (один из 10 участков функции)
            double[] curYforFitness = new double[10]; // текущий отрезок по оси У для искомой функции (fitness) (один из 10 участков функции)
            double[] curYforSigns = new double[10]; // текущий отрезок по оси У для признаков (один из 10 участков функции)

            // здесь вносим в curXforFitness и curYforFitness значения из отрезка функции
            for (int j = 0; j < curYforFitness.length; j++){
                for (int k = startId; k < endId; k++){
                    //curXforFitness[j] = fitnessFunc[k]; // вносим иксы
                    curYforFitness[j] = fitnessFunc[k]; // вносим У для искомой ф-и
                    curYforSigns[j] = signs[k]; // вносим признаки (признаки это У)
                    signs[k] = fitnessFunc[k];
                }
            }

            // считаем эвклидово расстояние между признаками и искомой функцией для текущего отрезка
            euclidForGroups[i] = WorkWithNumbers.getEuclideanDistance(curYforFitness, curYforSigns);
            //System.out.println("euclidForGroups " + i + ") ="+ euclidForGroups[i]);

            // прибавляем 10 к началу и к концу, чтобы посчитать следующий отрезок
            startId += 10;
            endId += 10;
        }
        // итог: посчитали эвклидово расст между признаками и искомой функцией

//        // 2. теперь сравниваем эвклидовые расст для групп и мутируем ту, что удаленна больше 1
//        double EUCLID = 0.2;
//        for (int i = 0; i < euclidForGroups.length; i++){
//            if (euclidForGroups[i] > EUCLID){
//                int idOfLastElementOfGroup = (i + 1) * 10;
//                int idOfStartElementOfGroup = idOfLastElementOfGroup - 10;
//
//                // тут применяем трехточечную мутацию
//                int size = 10; // количество элементов в группе
//
//                // определяются случайным образом три позиции
//                // вызываем метод getRandom и прибавляем idOfStartElementOfGroup, так как мы действуем в рамках отрезка
//                int position1 = WorkWithNumbers.getRandomFromTo(0, size-1) + idOfStartElementOfGroup;
//                int position2 = WorkWithNumbers.getRandomFromTo(0, size-1) + idOfStartElementOfGroup;
//
//                // Гены, соответствующие выбранным позициям, переставляются
//                double temp = signs[position1];
//                signs[position1] = signs[position2];
//                signs[position2] = temp;
//
//
//            }
//        }
    }

    public void mutateFromEuclid2(double[] fitnessFunc){
        // 1. считаем эвклидово расстояние для каждого из 10 участков
        double[] euclidForGroups = new double[10]; // эвклидово расстояние для каждого из 10 участков для fitness function
        int startId = 0; // =0 так как отсчет начинаем в нулевого элемента и заканчиваем 10 - это первая группа
        int endId = 10; // =10 так как отсчет заканчиваем на 10 - это первая группа

        double EUCLID = 1;

        // в цикле проходим по каждому отрезку и считаем значение эвклидового расстояния
        for (int i = 0; i < euclidForGroups.length; i++){

            double[] curYforFitness = new double[10]; // текущий отрезок по оси У для искомой функции (fitness) (один из 10 участков функции)
            double[] curYforSigns = new double[10]; // текущий отрезок по оси У для признаков (один из 10 участков функции)

            // здесь вносим в curXforFitness и curYforFitness значения из отрезка функции
            for (int j = 0; j < curYforFitness.length; j++){
                for (int k = startId; k < endId; k++){
                    curYforFitness[j] = fitnessFunc[k]; // вносим У для искомой ф-и
                    curYforSigns[j] = signs[k]; // вносим признаки (признаки это У)

                }
            }

            // считаем эвклидово расстояние между признаками и искомой функцией для текущего отрезка
            euclidForGroups[i] = WorkWithNumbers.getEuclideanDistance(curYforFitness, curYforSigns);

            double lastEuclid = euclidForGroups[i];
            double newEuclid = lastEuclid; // пока что

            do {
//                System.out.println("START OF DO-WHILE");
//                System.out.println("BEFORE 1");
//                System.out.println("euclidForGroups[i]=" + euclidForGroups[i]);
//                System.out.println("newEuclid=" + newEuclid);
//                System.out.println("lastEuclid=" + lastEuclid);
                lastEuclid = newEuclid;
//
//                System.out.println("BEFORE 2");
//                System.out.println("euclidForGroups[i]=" + euclidForGroups[i]);
//                System.out.println("newEuclid=" + newEuclid);
//                System.out.println("lastEuclid=" + lastEuclid);

                for (int l = startId; l < endId; l++){
                    System.out.print(signs[l] + " ");
                }

                // тут применяем трехточечную мутацию
                int size = 10; // количество элементов в группе

                int position1 = WorkWithNumbers.getRandomFromTo(0, size) + startId;
                int position2 = WorkWithNumbers.getRandomFromTo(0, size) + startId;
                int position3 = WorkWithNumbers.getRandomFromTo(0, size) + startId;

                signs[position1] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);
                signs[position2] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);
                signs[position3] = WorkWithNumbers.round(WorkWithNumbers.getRandomDoubleFromTo(-2., 2.), 2);


//                // определяются случайным образом три позиции
//                // вызываем метод getRandom и прибавляем startId, так как мы действуем в рамках отрезка
//                int position1 = WorkWithNumbers.getRandomFromTo(0, size-1) + startId;
//                int position2 = WorkWithNumbers.getRandomFromTo(0, size-1) + startId;
//
//                // Гены, соответствующие выбранным позициям, переставляются
//                double temp = signs[position1];
//                signs[position1] = signs[position2];
//                signs[position2] = temp;

                // 2. пересчитываем эвклидово расстояние
                // здесь вносим в newYforSigns значения из нового отрезка функции
                double[] newYforSigns = new double[10];
                for (int j = 0; j < newYforSigns.length; j++){
                    for (int k = startId; k < endId; k++){
                        newYforSigns[j] = signs[k]; // вносим признаки (признаки это У)
                    }
                }

                newEuclid = WorkWithNumbers.getEuclideanDistance(curYforFitness, newYforSigns);
//                System.out.println("AFTER");
//                for (int l = startId; l < endId; l++){
//                    System.out.print(signs[l] + " ");
//                }
//                System.out.println("euclidForGroups[i]=" + euclidForGroups[i]);
//                System.out.println("newEuclid=" + newEuclid);
//                System.out.println("lastEuclid=" + lastEuclid);
//                System.out.println("END OF DO-WHILE");
            } while ((euclidForGroups[i] > EUCLID)
                    && (newEuclid > lastEuclid)
            );
            System.out.println("STOP OF DO-WHILE");




            // прибавляем 10 к началу и к концу, чтобы посчитать следующий отрезок
            startId += 10;
            endId += 10;
        }
        // итог: посчитали эвклидово расст между признаками и искомой функцией

//        // 2. теперь сравниваем эвклидовые расст для групп и мутируем ту, что удаленна больше 1.
//        // Если после мутации эвклидово расстояние >= предыдущему то пересчет.
//        double EUCLID = 1;
//        // проходим по каждому элементу эвклидового расст
//        for (int i = 0; i < euclidForGroups.length; i++){
//            double lastEuclid = euclidForGroups[i];
//            double newEuclid = lastEuclid;
//
//            do {
//                // считаем индексы, т. к. работаем в рамках группы
//                int idOfLastElementOfGroup = (i + 1) * 10;
//                int idOfStartElementOfGroup = idOfLastElementOfGroup - 10;
//
//                // тут применяем трехточечную мутацию
//                int size = 10; // количество элементов в группе
//
//                // определяются случайным образом три позиции
//                // вызываем метод getRandom и прибавляем idOfStartElementOfGroup, так как мы действуем в рамках отрезка
//                int position1 = WorkWithNumbers.getRandomFromTo(0, size-1) + idOfStartElementOfGroup;
//                int position2 = WorkWithNumbers.getRandomFromTo(0, size-1) + idOfStartElementOfGroup;
//
//                // Гены, соответствующие выбранным позициям, переставляются
//                double temp = signs[position1];
//                signs[position1] = signs[position2];
//                signs[position2] = temp;
//
//                // 2. пересчитываем эвклидово расстояние
//                lastEuclid = WorkWithNumbers.getEuclideanDistance();
//
//                double[] curYforFitness = new double[10]; // текущий отрезок по оси У для искомой функции (fitness) (один из 10 участков функции)
//                double[] curYforSigns = new double[10]; // текущий отрезок по оси У для признаков (один из 10 участков функции)
//
//                // здесь вносим в curXforFitness и curYforFitness значения из отрезка функции
//                for (int j = 0; j < curYforFitness.length; j++){
//                    for (int k = startId; k < endId; k++){
//                        //curXforFitness[j] = fitnessFunc[k]; // вносим иксы
//                        curYforFitness[j] = fitnessFunc[k]; // вносим У для искомой ф-и
//                        curYforSigns[j] = signs[k]; // вносим признаки (признаки это У)
//                    }
//                }
//
//
////                // проходим по массиву признаков между индексов idOfLastElementOfGroup и idOfStartElementOfGroup
////                for (int j = idOfStartElementOfGroup; j < idOfLastElementOfGroup; j++) {
////                    // 1. тут применяем трехточечную мутацию
////                    int size = 10; // количество элементов в группе
////
////                    // определяются случайным образом три позиции
////                    // вызываем метод getRandom и прибавляем idOfStartElementOfGroup, так как мы действуем в рамках отрезка
////                    int position1 = WorkWithNumbers.getRandomFromTo(0, size - 1) + idOfStartElementOfGroup;
////                    int position2 = WorkWithNumbers.getRandomFromTo(0, size - 1) + idOfStartElementOfGroup;
////                    int position3 = WorkWithNumbers.getRandomFromTo(0, size - 1) + idOfStartElementOfGroup;
////
////                    // Гены, соответствующие выбранным позициям, переставляются
////                    double temp = signs[position1];
////                    signs[position1] = signs[position3];
////                    signs[position3] = signs[position2];
////                    signs[position2] = temp;
////
////
////                }
//
//            } while ((euclidForGroups[i] > EUCLID) && (newEuclid > lastEuclid));
//
//
//        }


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
        this.euclidDist = WorkWithNumbers.getEuclideanDistance(this.signs, requiredFunction);
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
