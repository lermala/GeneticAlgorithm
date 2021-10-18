package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Population {
    ArrayList<Individidual> population = new ArrayList<>(); // список особей в популяции
    int numberOfIndividual;  // количество особей
    int numberOfSigns; // количество признаков

    private double[] requiredFunction; // искомая функция (Y)
    private double[] xForRequiredFunc; // x для искомой функции

    public Population(int numberOfIndividual, int numberOfSigns) {
        this.numberOfIndividual = numberOfIndividual;
        this.numberOfSigns = numberOfSigns;

        // Находим искомую функцию Y=ln(2x)
        //xForRequiredFunc = WorkWithNumbers.getRandomArray(numberOfSigns);
        xForRequiredFunc = WorkWithNumbers.getArrayFromTo(0.1, 0, 100);
        requiredFunction = getFunction(xForRequiredFunc);
    }

    public Population() {
    }

    public void createPopulation(){
        double[] signs = new double[numberOfSigns]; // массив признаков для текущей особи

        // здесь формируем массив признков с шагом 0.1
        double lastElement = 0; // последний сгенерированный признак
        for (int k = 0; k < numberOfSigns; k++){
            double step = 0.1;
            double currentElement = lastElement + step;  // считаем элмент

            // округляем до 2го знака
            double scale = Math.pow(10, 1);
            signs[k] = Math.ceil(currentElement * scale) / scale;

            lastElement = signs[k]; // последний сгенерированный признак
        }
        System.out.println("SIGNS = " + Arrays.toString(signs));

        // создаем 20 особей, у каждой 100 признаков
        for (int i = 0; i < numberOfIndividual; i++){
            Individidual individidual = new Individidual(numberOfSigns); // создаем особь
            //System.out.println("ind= " + individidual.);
            individidual.setSigns(signs); // добавляем особи признаки
            population.add(individidual); // добавляем особь в популяцию
        }

    }

    // RANDOM SIGNS
    public void createPopulation2(){
        // создаем 20 особей, у каждой 100 признаков
        for (int i = 0; i < numberOfIndividual; i++){
            double[] signs = WorkWithNumbers.getRandomArray(numberOfSigns); // массив признаков для текущей особи
            Individidual individidual = new Individidual(numberOfSigns); // создаем особь

            individidual.setSigns(signs); // добавляем особи признаки
            population.add(individidual); // добавляем особь в популяцию
        }

    }

    /**
     * Запускает генетический алгоритм. Здесь в цикле размножение, мутация и удаление особей.
     * Остановка цикла в случае, если эвклидово рассояние <= 0.5
     */
    public void startGeneticAlg(){
        System.out.println("************* POPULATION *************");
        System.out.println(population.toString());

        // Здесь в цикле будем размножать, уничтожать особи до тех пор, пока эвклидово расстояние не будет равно 0,5.
        final double EUCLIDDIST = 0.5; // Остановить мутационный процесс при достижении эвклидова расстояния = 0,5.
        double minEuclid; // в начале ставим 0.6, так как эвклидово расстояние для особей еще не посчитано
        Individidual indWithMinEuclidDist; // особь с минимальным эвклидовым расстоянием
        do {
            // 3. Создать алгоритм, размножения и мутации, способный на каждом шаге удваивать популяцию.
            // crossoverBySinglePointOperator(); // одноточечный кроссовер
            crossoverByPointToPointOperator(); // двухточечный кроссовер
            //crossoverByThreePointsOperator(); // трехточечный кроссовер
            System.out.println("************* CROSSOVERED POPULATION *************");
            System.out.println(population.toString());

            mutate();
//          System.out.println("************* MUTATED POPULATION *************");
//          System.out.println(population.toString());

            // 4. Уничтожать половину удвоенной популяции, эвклидово расстояние признаков
            // которых максимально удалено от искомой функции.
            delete();

            System.out.println("************* DELETED POPULATION *************");
            for (int i = 0; i < population.size(); i++){
                Individidual ind = population.get(i);
                System.out.println(i + ") " + ind.toString() + " euclid=" + ind.getEuclidDist());
            }

            // Ищем минимальное эвклидовое расстояние
            indWithMinEuclidDist = getIndWithMinEuclidDist(); // особь с min dist
            minEuclid = indWithMinEuclidDist.getEuclidDist();
            System.out.println("МИНИМАЛЬНОЕ ЭВКЛИДОВОЕ РАССТОЯНИЕ = " + minEuclid);


        } while (minEuclid > EUCLIDDIST); // 5. Остановить мутационный процесс при достижении эвклидова расстояния = 0,5.

        // 6. Вывести признаки особи с наименьшим эвклидовым расстоянием в виде графика и
        // рядом график функции для сравнения.
        // System.out.println("MIN= " + indWithMinEuclidDist.toString());
        System.out.println("************************************ THE END OF ALG ************************************ ");
    }

    /**
     * Размножение (ОДНОТОЧЕЧНЫЙ ОПЕРАТОР КРОССИНГОВЕРА)
     * 1. Две хромосомы А = а1,а2,... , аL, и В = а1’,а2’,... ,aL’ выбираются  случайно из текущей популяции.
     * 2. Число k выбирается из {1,2, ...,L— 1} также случайно. // TODO: Число k выбирается для каждой пары одно или разное?
     * Здесь L — длина хромосомы, k — точка оператора кроссинговера
     * (номер, значение или код гена, после которого выполняется разрез хромосомы).
     * 3. Две новые хромосомы формируются из А и В путем перестановок.
     */
    public void crossoverBySinglePointOperator(){
        // 1. выбираются 2 особи случайно из текущей популяции
        // 1.1 для этого перемешаем arraуlist
        Collections.shuffle(population);

        // 1.2 и будем брать особи по порядку и попарно скрещивать
        Population resOfCrossover = new Population(); // здесь храним результат скрещивания, новые сооби
        for (int i = 0; i < population.size() - 1; i += 2){
            // берем родителей
            Individidual parent1 = population.get(i);
            Individidual parent2 = population.get(i + 1);

            // скрещиваем и получаем еще две особи
            Individidual[] children = Individidual.crossoverBySinglePoint(parent1, parent2);
            // вносим особи в популяцию
            // population.add(children[0]);
            // population.add(children[1]);
            resOfCrossover.addInd(children[0]);
            resOfCrossover.addInd(children[1]);
        }

        // после того как посчитали новые особи добавляем их в общую популяцию:
        for (Individidual ind:
             resOfCrossover.getPopulation()) {
            population.add(ind);
        }

    }

    /**
     * Размножение (Двухточечный ОПЕРАТОР КРОССИНГОВЕРА)
     * В каждой хромосоме определяются две точки оператора кроссинговера,
     * и хромосомы обмениваются участками, расположенными между двумя точками оператора кроссинговера.
     * Точки оператора кроссинговера в двухточечном операторе кроссинговера также определяются случайно.
     */
    public void crossoverByPointToPointOperator(){
        // 1. выбираются 2 особи случайно из текущей популяции
        // 1.1 для этого перемешаем arraуlist
        Collections.shuffle(population);

        // 1.2 и будем брать особи по порядку и попарно скрещивать
        Population resOfCrossover = new Population(); // здесь храним результат скрещивания, новые сооби
        for (int i = 0; i < population.size() - 1; i += 2){
            // берем родителей
            Individidual parent1 = population.get(i);
            Individidual parent2 = population.get(i + 1);

            // скрещиваем и получаем еще две особи
            Individidual[] children = Individidual.crossoverByTwoPoints(parent1, parent2);
            // вносим особи в популяцию
            // population.add(children[0]);
            // population.add(children[1]);
            resOfCrossover.addInd(children[0]);
            resOfCrossover.addInd(children[1]);
        }

        // после того как посчитали новые особи добавляем их в общую популяцию:
        for (Individidual ind:
                resOfCrossover.getPopulation()) {
            population.add(ind);
        }

    }

    /**
     *
     */
    public void crossoverByThreePointsOperator(){
        // 1. выбираются 2 особи случайно из текущей популяции
        // 1.1 для этого перемешаем arraуlist
        Collections.shuffle(population);

        // 1.2 и будем брать особи по порядку и попарно скрещивать
        Population resOfCrossover = new Population(); // здесь храним результат скрещивания, новые сооби
        for (int i = 0; i < population.size() - 1; i += 2){
            // берем родителей
            Individidual parent1 = population.get(i);
            Individidual parent2 = population.get(i + 1);

            // скрещиваем и получаем еще две особи
            Individidual[] children = Individidual.crossoverByThreePoints(parent1, parent2);
            // вносим особи в популяцию
            resOfCrossover.addInd(children[0]);
            resOfCrossover.addInd(children[1]);
        }

        // после того как посчитали новые особи добавляем их в общую популяцию:
        for (Individidual ind:
                resOfCrossover.getPopulation()) {
            population.add(ind);
        }

    }

    public void addInd(Individidual ind) {
        population.add(ind);
    }

    /**
     * Проводим мутацию для каждой особи
     */
    public void mutate(){
        for (Individidual el:
             population) {
            el.mutateByThreePoints();
        }

    }

    // 4. Уничтожать половину удвоенной популяции, эвклидово расстояние признаков
    // которых максимально удалено от искомой функции.
    public void delete(){
//        // считаем эвклидово расстояние для всех особей
//        for (Individidual ind:
//             population) {
//            ind.getEuclidDistanceForSignsWithRequiredFunc(requiredFunction); // сравниваем с искомой функцией
//        }

        // здесь сортируем популяцию
        sortByEuclid();

        // удаляем половину (элементы, которые идут после среднего)
        int mid = population.size() / 2; // индекс среднего элемента
        System.out.println("mid=" + mid);

        // формируем новую популяцию
        // в нее включаем половину особей (тех, у кого эвклидово расстояние минимально)
        ArrayList<Individidual> newPopulation = new ArrayList<>();
        for (int i = 0; i < mid; i++){
            newPopulation.add(population.get(i));
        }

        // теперь новая популяция заняла место старой:
        population = newPopulation;
    }

    /**
     * Функция для сортировки особей по возрастанию по эвклидовому расстоянию между
     * признаками особи и искомой функцией
     */
    public void sortByEuclid(){
        // считаем эвклидово расстояние для всех особей
        for (Individidual ind:
                population) {
            ind.getEuclidDistanceForSignsWithRequiredFunc(requiredFunction); // сравниваем с искомой функцией
        }

        // здесь сортируем популяцию по возрастанию
        Collections.sort(population, new Comparator<Individidual>() {
            @Override
            public int compare(Individidual o1, Individidual o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Считаем эвклидово расстояние между признаками и искомой функцией
     * @return матрица евклидовых расстояний для каждого признака с искомой функцией
     */
    public double[] getEuclidDistanceForSignsWithRequiredFunc(){
        // матрица евклидовых расстояний для каждого признака с искомой функцией.
        double[] euclidDist = new double[population.size()];

        for (int i = 0; i < population.size(); i++){
            Individidual ind = population.get(i);

            double[] A = getFunction(ind.getSigns()); // т. к. признаки - это значение Х, то считаем У для них
            double[] B = requiredFunction; // искомая функция

            euclidDist[i] = WorkWithNumbers.getEuclideanDistance(A, B); // записываем значение в матрицу
        }
        return euclidDist;
    }

    // создаем искомую функцию Y=ln(2x)
    public double[] getFunction(double[] x){
        int countOfElements = x.length;
        double[] y = new double[countOfElements];

        for (int i = 0; i < countOfElements; i++){
            y[i] = 2 * Math.log10(x[i]);
        }

        return y;
    }


    @Override
    public String toString() {
        String formatted = "";

        for (int i = 0; i < population.size(); i++){
            formatted += i + ") ";
            formatted += population.get(i).toString();
            formatted += "\n";
        }

        return formatted;
    }

    public int size(){
        return population.size();
    }

    public Individidual get(int id){
        return population.get(id);
    }

    public void addPop(Population pop){
        for (Individidual ind:
             pop.getPopulation()) {
            population.add(ind);
        }
    }

    /**
     * Выводит особь с минимальным эвклидовым расстоянием.
     * @return
     */
    public Individidual getIndWithMinEuclidDist(){
        // минимум = первому элементу
        Individidual indWithMinEuclid = population.get(0);

        // смотрим по всей популяции
        for (Individidual ind:
             population) {
            double minEuclidDist = indWithMinEuclid.getEuclidDist(); // смотрим минимум на данный момент
            if (ind.getEuclidDist() < minEuclidDist){ // если он меньше то это новый минимум
                indWithMinEuclid = ind;
            }
        }

        return indWithMinEuclid;
    }

    public ArrayList<Individidual> getPopulation() {
        return population;
    }

    public double[] getRequiredFunction() {
        return requiredFunction;
    }

    public double[] getxForRequiredFunc() {
        return xForRequiredFunc;
    }
}
