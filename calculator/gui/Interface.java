package calculator.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import calculator.calculation.Operations;

public class Interface extends JFrame implements ActionListener{
    protected static final String OPERATORS = "+-*/^√"; // список символов
    protected static final String OPERATORS_WITHOUT_SQUARE = "+-*/^"; // список символов без квадратного корня
    protected static StringBuilder currentSymbol = new StringBuilder(); // переменная, которая нужна для сохранения чисел, состоящих из двух и более цифр
    protected static StringBuilder content = new StringBuilder(); // незаконченное выражение, набираемое пользователем
    protected static DefaultListModel contentList = new DefaultListModel(); // список завершенных выражений
    protected static ArrayList<String> operandsAndOperators = new ArrayList<>(); // список чисел и операторов текущего выражения для подсчета
    // кнопки с цифрами
    protected static Button zero;
    protected static Button one;
    protected static Button two;
    protected static Button three;
    protected static Button four;
    protected static Button five;
    protected static Button six;
    protected static Button seven;
    protected static Button eight;
    protected static Button nine;
    protected static Button point;
    // кнопки с операторами
    protected static Button plus;
    protected static Button minus;
    protected static Button divide;
    protected static Button multiply;
    protected static Button pow;
    protected static Button delete;
    protected static Button square;
    protected static Button bracketOpen;
    protected static Button bracketClose;
    protected static Button percent;
    protected static Button equal;
    // элементы окна приложения
    protected static JList results; // список завершенных выражений
    protected static JLabel process; // текущее выражение
    protected static JPanel numbers; // панель с цифрами
    protected static JPanel operators; // панель с операторами
    protected static JPanel topPanel; // панель, включающая results и process
    protected static JPanel operationsPanel; // панель с кнопками
    protected static JPanel calculatorField; // общая панель

    // инициализация кнопок, панелей и сборка интерфейса
    public Interface() {
        initButtons();
        initPanels();
        buildInterface();
    }

    // иницализация кнопок, добавление обработчика нажатия
    private void initButtons() {
        zero = new Button("0");
        zero.setActionCommand("0");
        zero.addActionListener(this);

        one = new Button("1");
        one.setActionCommand("1");
        one.addActionListener(this);

        two = new Button("2");
        two.setActionCommand("2");
        two.addActionListener(this);

        three = new Button("3");
        three.setActionCommand("3");
        three.addActionListener(this);

        four = new Button("4");
        four.setActionCommand("4");
        four.addActionListener(this);

        five = new Button("5");
        five.setActionCommand("5");
        five.addActionListener(this);

        six = new Button("6");
        six.setActionCommand("6");
        six.addActionListener(this);

        seven = new Button("7");
        seven.setActionCommand("7");
        seven.addActionListener(this);

        eight = new Button("8");
        eight.setActionCommand("8");
        eight.addActionListener(this);

        nine = new Button("9");
        nine.setActionCommand("9");
        nine.addActionListener(this);

        point = new Button(".");
        point.setActionCommand(".");
        point.addActionListener(this);

        plus = new Button("+");
        plus.setActionCommand("+");
        plus.addActionListener(this);

        minus = new Button("-");
        minus.setActionCommand("-");
        minus.addActionListener(this);

        divide = new Button("/");
        divide.setActionCommand("/");
        divide.addActionListener(this);

        multiply = new Button("*");
        multiply.setActionCommand("*");
        multiply.addActionListener(this);

        pow = new Button("^");
        pow.setActionCommand("^");
        pow.addActionListener(this);

        delete = new Button("del");
        delete.setActionCommand("del");
        delete.addActionListener(this);

        square = new Button("√");
        square.setActionCommand("√");
        square.addActionListener(this);

        bracketOpen = new Button("(");
        bracketOpen.setActionCommand("(");
        bracketOpen.addActionListener(this);

        bracketClose = new Button(")");
        bracketClose.setActionCommand(")");
        bracketClose.addActionListener(this);

        percent = new Button("%");
        percent.setActionCommand("%");
        percent.addActionListener(this);

        equal = new Button("=");
        equal.setActionCommand("=");
        equal.addActionListener(this);
    }

    // инициализация панелей
    private static void initPanels() {
        results = new JList(contentList);
        results.setOpaque(true);
        results.setBackground(Color.LIGHT_GRAY);

        process = new JLabel();
        process.setOpaque(true);
        process.setBackground(Color.WHITE);

        GridLayout numbersLayout = new GridLayout(4, 3, 2, 2);
        GridLayout operatorsLayout = new GridLayout(6, 2, 3, 3);
        GridLayout topLayout = new GridLayout(2, 1, 5, 0);
        GridLayout operationsLayout = new GridLayout(1, 2, 0, 5);
        GridLayout fieldLayout = new GridLayout(2, 1, 0, 0);

        numbers = new JPanel();
        numbers.setLayout(numbersLayout);
        numbers.add(one);
        numbers.add(two);
        numbers.add(three);
        numbers.add(four);
        numbers.add(five);
        numbers.add(six);
        numbers.add(seven);
        numbers.add(eight);
        numbers.add(nine);
        numbers.add(point);
        numbers.add(zero);

        operators = new JPanel();
        operators.setLayout(operatorsLayout);
        operators.add(delete);
        operators.add(plus);
        operators.add(minus);
        operators.add(divide);
        operators.add(multiply);
        operators.add(pow);
        operators.add(square);
        operators.add(bracketOpen);
        operators.add(bracketClose);
        operators.add(percent);
        operators.add(equal);

        topPanel = new JPanel();
        topPanel.setLayout(topLayout);
        topPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        topPanel.add(new JScrollPane(results));
        topPanel.add(process);

        operationsPanel = new JPanel();
        operationsPanel.setLayout(operationsLayout);
        operationsPanel.add(numbers);
        operationsPanel.add(operators);

        calculatorField = new JPanel();
        calculatorField.setLayout(fieldLayout);
        calculatorField.add(topPanel);
        calculatorField.add(operationsPanel);
    }

    // создание окна приложения, добавление в него панелей
    private static void buildInterface() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.getContentPane().add(calculatorField);
        frame.pack();
        frame.setVisible(true);
    }

    // обработчик событий для кнопок
    public void actionPerformed(ActionEvent e) {
        // значение нажатой кнопки
        String buttonText = e.getActionCommand();

        if (content.length() == 0) {
            // если вводится первый символ выражение, допустимо использование цифры, унарного минуса, открывающейся скобки или знака квадратного корня
            if (Operations.isNumeric(buttonText) || buttonText == "-") {
                currentSymbol.append(buttonText);
                content.append(buttonText);
            } else if (buttonText == "√" || buttonText == "(") {
                operandsAndOperators.add(buttonText);
                content.append(buttonText);
            }
        } else {
            if (buttonText == "=") {
                String result = "0"; // значение по умолчанию (нужно, если пользователь не ввел ни одного числа)

                // добавление последнего введенного, но еще не добавленного числа в массив операторов и операндов
                if (currentSymbol.length() != 0) {
                    operandsAndOperators.add(currentSymbol.toString());
                    currentSymbol = new StringBuilder();
                }

                // если последним был введен оператор, его нужно удалить
                while (content.length() > 0 && !Operations.isNumeric(content.substring(content.length() - 1)) && !content.substring(content.length() - 1).contains("%") && !content.substring(content.length() - 1).contains(")")) {
                    operandsAndOperators.remove(operandsAndOperators.size() - 1);
                    content.delete(content.length() - 1, content.length());
                }

                // обработка скобок и подсчет
                Operations.checkAndCalcBrackets(operandsAndOperators);
                Operations.checkAndCalcAll(operandsAndOperators);

                // если выражение было введено корректно, в массиве останется только одно значение - результат
                if (operandsAndOperators.size() > 0) {
                    result = operandsAndOperators.get(0);
                }

                // добавление выражения в "историю" - список предыдуих подсчетов
                if (content.length() != 0) {
                    content.append(buttonText + result);
                    contentList.addElement(content);
                    content = new StringBuilder();
                    operandsAndOperators = new ArrayList<>();
                }
            } else if (buttonText == "del") {
                // удаление последнего символа
                if (currentSymbol.length() != 0) {
                    // если последним было число и оно еще не добавлено в список операторов и операндов
                    currentSymbol.delete(currentSymbol.length() - 1, currentSymbol.length());
                } else if (operandsAndOperators.size() != 0) {
                    // если последним было число и оно добавлено в список, нужно сначала вернуть его в переменную currentSymbol, а после удалять
                    if (operandsAndOperators.get(operandsAndOperators.size() - 1).length() != 1) {
                        currentSymbol = new StringBuilder(operandsAndOperators.get(operandsAndOperators.size() - 1));
                        currentSymbol.delete(currentSymbol.length() - 1, currentSymbol.length());
                    }
                    operandsAndOperators.remove(operandsAndOperators.size() - 1);

                    // если удаленным символом был оператор, последнее число из массива нужно вернуть в переменную currentSymbol
                    if (operandsAndOperators.size() != 0 && Operations.isNumeric(operandsAndOperators.get(operandsAndOperators.size() - 1))) {
                        currentSymbol = new StringBuilder(operandsAndOperators.get(operandsAndOperators.size() - 1));
                        operandsAndOperators.remove(operandsAndOperators.size() - 1);
                    }
                }

                if (content.length() != 0) {
                    content.delete(content.length() - 1, content.length());
                }
            } else if (buttonText == "√") {
                String lastSymbol = content.substring(content.length() - 1);

                // если последним был введен унарный минус, для корректного подсчета перед корнем добавляется умножение на -1
                if (currentSymbol.toString().contains("-") && currentSymbol.length() == 1) {;
                    operandsAndOperators.add("-1");
                    operandsAndOperators.add("*");
                    operandsAndOperators.add(buttonText);
                    content.append(buttonText);
                    currentSymbol = new StringBuilder();
                } else if (OPERATORS.contains(lastSymbol) || lastSymbol.contains("(")) {
                    // проверка на допустимые символы перед знаком корня
                    content.append(buttonText);
                    operandsAndOperators.add(buttonText);
                }
            } else if (buttonText == "-" && currentSymbol.length() == 0 && OPERATORS_WITHOUT_SQUARE.contains(content.substring(content.length() - 1, content.length()))) {
                // обработка унарного минуса
                currentSymbol.append(buttonText);
                content.append(buttonText);
            } else if (buttonText == "%") {
                // знак процента может стоять только после числа
                if (currentSymbol.length() != 0) {
                    operandsAndOperators.add(currentSymbol.toString());
                    currentSymbol = new StringBuilder();
                    operandsAndOperators.add(buttonText);
                    content.append(buttonText);
                }
            } else if (buttonText == "(") {
                if (currentSymbol.length() != 0) {
                    operandsAndOperators.add(currentSymbol.toString());
                    currentSymbol = new StringBuilder();
                }
                // открывающаяся скобка не может стоять после числа
                if (!Operations.isNumeric(operandsAndOperators.get(operandsAndOperators.size() - 1))) {
                    operandsAndOperators.add(buttonText);
                    content.append(buttonText);
                }
            } else if (buttonText == ")") {
                if (currentSymbol.length() != 0) {
                    operandsAndOperators.add(currentSymbol.toString());
                    currentSymbol = new StringBuilder();
                }
                // закрывающаяся скобка не может стоять после оператора
                if (Operations.isNumeric(operandsAndOperators.get(operandsAndOperators.size() - 1)) || operandsAndOperators.get(operandsAndOperators.size() - 1) == ")" || operandsAndOperators.get(operandsAndOperators.size() - 1) == "%") {
                    if (content.toString().contains("(")) {
                        operandsAndOperators.add(buttonText);
                        content.append(buttonText);
                    }
                }
            } else {
                // обработка ввода числа или точки
                if (Operations.isNumeric(buttonText) || buttonText == ".") {
                    if (!content.substring(content.length() - 1).contains(")")) {
                        currentSymbol.append(buttonText);
                        content.append(buttonText);
                    }
                } else {
                    // обработка ввода остальных операторов
                    if (currentSymbol.length() != 0) {
                        operandsAndOperators.add(currentSymbol.toString());
                        currentSymbol = new StringBuilder();
                    }

                    // если вводится второй оператор (не унарный минус) подряд, то первый заменяется вторым
                    while (OPERATORS_WITHOUT_SQUARE.contains(content.substring(content.length() - 1))) {
                        content.delete(content.length() - 1, content.length());
                        operandsAndOperators.remove(operandsAndOperators.size() - 1);
                    }

                    if (Operations.isNumeric(operandsAndOperators.get(operandsAndOperators.size() - 1)) || operandsAndOperators.get(operandsAndOperators.size() - 1) == "%" || operandsAndOperators.get(operandsAndOperators.size() - 1) == ")") {
                        content.append(buttonText);
                        operandsAndOperators.add(buttonText);
                    }
                }
            }
        }
        process.setText(content.toString());
    }
}
