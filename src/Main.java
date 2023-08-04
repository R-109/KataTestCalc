import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String inData;  //переменная вводимого значения
        String result;  //строка результата
        inData = getData();
        result = TestCalc(inData);
        System.out.println(result);
    }
    static String getData() { // метод получения значения из строки
        Scanner scan = new Scanner(System.in); // создание объекта для снятия данных со строки
        System.out.println("Введите строку для вычисления формата [операнд 1] пробел [операция ( +, -, *, /)] пробел [операнд 2]");
        System.out.println("Значение операндов не может выходить из диапазона -10 - +10. Римские числа 1 - 10");
        System.out.println("Допускается запись обоих операндов арабскими или римскими цифрами");
        String data = scan.nextLine();  // запрос данных со строки в переменную
        return data;
    }
    // методы арифметических действий
    static double add(int oper1in, int oper2in) {
        return oper1in + oper2in;
    }

    static double sub(int oper1in, int oper2in) {
        return oper1in - oper2in;
    }

    static double mul(int oper1in, int oper2in) {
        return oper1in * oper2in;
    }

    static double div(int oper1in, int oper2in) {
        if (oper2in != 0) {
            return oper1in / oper2in;
        }
        else {
            System.out.println("На 0 делить нельзя");
            return Double.NaN;
        }
    }
    // метод непосредственного вычисления
    static double calculation(int oper1in, int oper2in, char operation) {
        switch (operation) {
            case '+':
                return add(oper1in, oper2in);
            case '-':
                return sub(oper1in, oper2in);
            case '*':
                return mul(oper1in, oper2in);
            case '/':
                return div(oper1in, oper2in);
            default:
                System.out.println("Неверная операция или формат записи");
                return Double.NaN;
        }
    }
    // метод преобразования арабских чисел в римские
    static String IntToRoman(int num) {
        String[] keys = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int val[] = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < 13){

            while(num >= val[ind])
            {
                var d = num / val[ind];
                num = num % val[ind];
                for(int i=0; i<d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }

        return ret.toString();
    }
    // методы преобразования римских чисел в арабские
    static int letterToNumber(char letter){  //
        switch (letter) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new NumberFormatException("Invalid format");
        }
    }

    static int roman2Decimal(String roman){  //метод преооразования римский чисел в арабские
        if (roman.length() == 0)
            return 0;
        int integerValue = 0;
        int prevNumber = letterToNumber(roman.charAt(0));
        for (int i = 1; i < roman.length(); i++) {
            char ch = roman.charAt(i);
            int number = letterToNumber(ch);
            if (number <= prevNumber)
                integerValue += prevNumber;
            else
                integerValue -= prevNumber;
            prevNumber = number;
        }
        integerValue += prevNumber;
        return integerValue;
    }
    static int detectBugInp (String dataInp) {  //метод проверки правильности ввода
        int bugIn; /* возвращаемая ошибка
                    0 - операнды арабские в границах диапазона, символ операции корректен
                    1 - операнды римские валидные в границах диапазона символ операции корректен
                    2 - операнды вне диапазона
                    3 - символ операции некорректен
                    4 - ошибка в структуре записи выражения
                    5 - пустая строка
                    6 - введены одновременно римские и арабские числа*/
        int date1 = 1; //первый операнд
        int date2 = 1; // второй операнд
        char dateOp; // символ операции
        int i;
        boolean j;
        char [] operation1 = {'+', '-', '/', '*'}; // все возможные операции
        String dataInpV;
        String regex1 = "^(\\-)?[0-9]*$"; // шаблон для проверки подстроки на арабские цифры
        String regex2 = "^[+\\-*/]$"; // шаблон для проверки строки на корректность ввода операции
        String regex3 = "^M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$";
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Pattern pattern3 = Pattern.compile(regex3);
        String[] data = dataInp.split(" "); //разборка стрки по пробелам
        if (dataInp.length() == 0) { //проверка на пустую строку
            bugIn = 5;
            return bugIn;
        }
        i = 0;
        for (String word : data) {//  проверка на стркутуру строки, анализируемых блоков не больше трех
            // System.out.println( word);
            i++;
        }
        if (i == 0) {  //проверка на строку состоящую из одних пробелов
            bugIn = 5;
            return bugIn;
        }
        //System.out.println("Количество подстрок = " + " " + i);


        if (i != 3 | data[0].length() > 4 | data[2].length() > 4 | data[1].length() > 1 | dataInp.length() > 11) {
            // проверка на количество подстрок после разбиения приемлемо только три
            // длину операндов - не более 4 (римская 8 это 4 символа)
            // длину строки операции - не более 1
            // длина строки не более 11 символов (4 пробел операция пробел 4)
            bugIn = 4;  // возврат ощибки нарушения структуры
            return bugIn;
        }


        Matcher matcher1 = pattern1.matcher(data[0]); //проверка на соответствие только арабским числам первого операнда
        Matcher matcher2 = pattern1.matcher(data[2]); //проверка на соответствие только арабским числам второго операнда
        Matcher matcher3 = pattern2.matcher(data[1]); // проверка на валидность символа операции
        Matcher matcher4 = pattern3.matcher(data[0]); // проверка на соответствие римским числам первого операнда
        Matcher matcher5 = pattern3.matcher(data[2]); // проверка на соответствие римским числам второго операнда
            i = 0;
            j = false;
            //  проверка на допустимость операции и отработка исключения
            try {
                while ( j != true ){
                    if (data[1].charAt(0) == operation1[i]) j =true;
                    i++;
                }
            }
            catch (ArrayIndexOutOfBoundsException exc){
                bugIn = 3;
                return bugIn;
            }

        if (matcher1.matches() & matcher3.matches() & matcher2.matches()) {
            date1 = Integer.parseInt(data[0]); // преобразование первой подстроки в первый операнд
            date2 = Integer.parseInt(data[2]);// преобразование третьей подстроки во втрой операнд
            if (date1 >= -10 & date1 <= 10 & date2 >= -10 & date2 <= 10) {
                System.out.println("Операнды арабские, значения в диапазое,  операция корректна");
                bugIn = 0;
                return bugIn;
            } else {
                bugIn = 2;
                return bugIn;
            }
        }


        //System.out.println(matcher4.matches() + " " + matcher5.matches() + " " + matcher3.matches());
        if (matcher4.matches() & matcher5.matches() & matcher3.matches()) {
            date1 = roman2Decimal(data[0]); // преобразование первого римского числа в первый операнд
            date2 = roman2Decimal(data[2]); // преобразование второго римского числа во втрой операнд
            if (date1 >= -10 & date1 <= 10 & date2 >= -10 & date2 <= 10) {
                System.out.println(" Операнды римские, в разрешенном диапазоне, операwия корректна");
                bugIn = 1;
                return bugIn;
            } else {
                bugIn = 2;
                return bugIn;
            }
        }
        // проверка на принадлежность обоих чисел к одной системе
        if ((matcher1.matches() & matcher5.matches())
                | (matcher2.matches() & matcher4.matches())){
            bugIn = 6;
            return bugIn;
        }
        bugIn = 9;
        return bugIn;
    }
    static String TestCalc(String input) {
        char operation;  //  переменная для операции
        int rez;       //  переменная с результатом
        int oper1in;  // переменная для преобразованного 1 операнда
        int oper2in;  // переменная для преобразованного 2 операнда
        int bug;
        String result = "";
        String[] operN = input.split(" ");
        bug = detectBugInp(input);  // вызов метода проверки полученных данных
        //System.out.println(bug);
        switch (bug) {
            case 0:  //работаем с арабскими числами в разрещенном  диапазоне
                operation = operN[1].charAt(0); // вынимаем из строки операцию
                oper1in = Integer.parseInt(operN[0]); // преобразование первой подстроки в первый операнд
                oper2in = Integer.parseInt(operN[2]); // преобразование третьей подстроки во втрой операнд
                rez = (int) calculation(oper1in, oper2in, operation);  // вызов метода  вычисления
                return Integer.toString(rez); //печать результата
            case 1: // работаем с римскими числами в разрешенном диапазоне
                operation = operN[1].charAt(0); // вынимаем из строки операцию
                oper1in = roman2Decimal(operN[0]);
                oper2in = roman2Decimal(operN[2]);
                rez = (int) calculation(oper1in, oper2in, operation);  // вызов метода  вычисления
                if (rez <= 0) {
                    return "Римские числа не могут быть меньше нуля";
                } else {
                    //System.out.println(rez);
                    result = IntToRoman(rez);
                    return (result); //печать результата
                }
            case 2:
                return "Введенные значения вне диапазона";
            case 3:
                return "Символ операции некорректен";
            case 4:
                return "Ошибка в структуре выражения";
            case 5:
                return "Пустая строка";
            case 6:
                return "Числа могут быть или только римские или только арабские ";
        }
        return result;
    }
    }