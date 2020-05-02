package calculator.calculation;

import java.util.ArrayList;

public class Operations {
    // значения, используемые в нескольких методах
    private static int firstIndex; // операнд перед оператором
    private static int lastIndex; // операнд после оператора
    private static int operatorIndex; // индекс обрабатываемого оператора

    // метод для проверки строки на возможно преобразования к числу
    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // проверка и обработка скобок
    public static void checkAndCalcBrackets(ArrayList<String> array) {
        // индекс открывающей и закрывающей скобки со значением по умолчанию
        int bracketOpenIndex = -1;
        int bracketCloseIndex = -1;

        // пока массив содержит открывающие скобки, происходит обработка последней из них
        while (array.contains("(")) {
            bracketOpenIndex = array.lastIndexOf("(");
            // поиск ближайшей закрывающей скобки
            if (array.subList(bracketOpenIndex, array.size()).contains(")")) {
                for (int i = bracketOpenIndex; i < array.size(); i++) {
                    if (array.get(i) == ")") {
                        bracketCloseIndex = i;
                        break;
                    }
                }

                // получение среза между открывающей и закрывающей скобками и подсчет выражения внутри них
                ArrayList<String> currentArray = new ArrayList<>(array.subList(bracketOpenIndex + 1, bracketCloseIndex));
                checkAndCalcAll(currentArray);

                // добавление результата в исходный массив и удаление уже обработанного выражения вместе со скобками
                array.set(bracketCloseIndex, currentArray.get(0));
                for (int i = bracketCloseIndex - 1; i >= bracketOpenIndex; i--) {
                    array.remove(i);
                }
            } else {
                // если после открывающей скобки нет закрывающей, ее нужно удалить
                array.remove(bracketOpenIndex);
            }
        }

        // если после обработки всех открывающих скобок остались закрывающие, их нужно удалить
        while (array.contains(")")) {
            bracketCloseIndex = array.lastIndexOf(")");
            array.remove(bracketCloseIndex);
        }
    }

    // обработка процентов
    public static void checkAndCalcPercent(ArrayList<String> array) {
        double percentNumber; // значение процента (5 для 5%)
        int beforePercentNumberIndex = -1; // индекс предыдущего числа со значением по умолчанию
        double beforePercentNumber; // предыдущее число
        double percent; // процент в виде десятичной дроби (10% = 0,1)

        while (array.contains("%")) {
            operatorIndex = array.indexOf("%");
            percentNumber = Double.parseDouble(array.get(operatorIndex - 1));
            percent = percentNumber / 100;

            // поиск предыдущего числа (10 в выражении 10 - 50%)
            for (int i = operatorIndex - 3; i >= 0; i--) {
                if (isNumeric(array.get(i))) {
                    beforePercentNumberIndex = i;
                    break;
                }
            }

            if (beforePercentNumberIndex != -1) {
                // если предыдущее число есть, высчитывается процент от него (10 - 50% = 10 - (10*0,5) = 10 - 5 = 5)
                beforePercentNumber = Double.parseDouble(array.get(beforePercentNumberIndex));
                double newNumber = beforePercentNumber * percent;
                array.set(operatorIndex - 1, Double.toString(newNumber));
                array.remove(operatorIndex);
            } else {
                // если предыдущего числа нет, процент считается как десятичная дробь (10% = 10 / 100 = 0,1)
                array.set(operatorIndex - 1, Double.toString(percent));
                array.remove(operatorIndex);
            }
        }
    }

    // поиск о обработка квадратных корней
    public static void checkAndCalcSquare(ArrayList<String> array) {
        int squareIndex;
        double currentSquare;

        // поскольку возможно выражение вида √√16, обработка начинается с последнего знака извлечения корня
        while (array.contains("√")) {
            operatorIndex = array.lastIndexOf("√");
            squareIndex = operatorIndex + 1;
            currentSquare = Math.sqrt(Double.parseDouble(array.get(squareIndex).toString()));
            array.set(squareIndex, Double.toString(currentSquare));
            array.remove(operatorIndex);
        }
    }

    // проверка и обработка возведения в степень
    public static void checkAndCalcPow(ArrayList<String> array) {
        double currentPow;

        while (array.contains("^")) {
            operatorIndex = array.indexOf("^");
            firstIndex = operatorIndex - 1;
            lastIndex = operatorIndex + 1;
            currentPow = Math.pow(Double.parseDouble(array.get(firstIndex)), Double.parseDouble(array.get(lastIndex)));
            array.set(operatorIndex, Double.toString(currentPow));
            array.remove(lastIndex);
            array.remove(firstIndex);
        }
    }

    // поиск о обработка деления
    public static void checkAndCalcDivide(ArrayList<String> array) {
        double currentDivide;

        while (array.contains("/")) {
            operatorIndex = array.indexOf("/");
            firstIndex = operatorIndex - 1;
            lastIndex = operatorIndex + 1;
            currentDivide = Double.parseDouble(array.get(firstIndex)) / Double.parseDouble(array.get(lastIndex));
            array.set(operatorIndex, Double.toString(currentDivide));
            array.remove(lastIndex);
            array.remove(firstIndex);
        }
    }

    // поиск и обработка умножения
    public static void checkAndCalcMultiply(ArrayList<String> array) {
        double currentProduct;

        while (array.indexOf("*") != -1) {
            operatorIndex = array.indexOf("*");
            firstIndex = operatorIndex - 1;
            lastIndex = operatorIndex + 1;
            currentProduct = Double.parseDouble(array.get(firstIndex).toString()) * Double.parseDouble(array.get(lastIndex).toString());
            array.set(operatorIndex, Double.toString(currentProduct));
            array.remove(lastIndex);
            array.remove(firstIndex);
        }
    }

    // поиск и обработка сложения и вычитания
    public static void checkAndCalcPlusAndMinus(ArrayList<String> array) {
        double firstNumber;
        double secondNumber;
        double currentNum;
        String operator;

        while (array.size() > 2) {
            firstNumber = Double.parseDouble(array.get(0));
            operator = array.get(1);
            secondNumber = Double.parseDouble(array.get(2));

            if (operator == "+") {
                currentNum = firstNumber + secondNumber;
            } else {
                currentNum = firstNumber - secondNumber;
            }

            array.set(1, Double.toString(currentNum));
            array.remove(2);
            array.remove(0);
        }
    }

    // вызов всех обрабатывающих функций (кроме обработки скобок)
    public static void checkAndCalcAll(ArrayList<String> array) {
        // для корректного вычисления сначала подсчитываются проценты, потом - квадратные корни и степени, после - умножение и деление, а в конце сложение и вычитание
        checkAndCalcPercent(array);
        checkAndCalcSquare(array);
        checkAndCalcPow(array);
        checkAndCalcMultiply(array);
        checkAndCalcDivide(array);
        checkAndCalcPlusAndMinus(array);
    }
}
