import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ScannerException {
        System.out.println("Введите пример для решения:");
        Scanner scanner = new Scanner(System.in);
        String ourCase = scanner.nextLine();// считываю с консоли пример
        System.out.println("Результат суммы ваших чисел: " + calc(ourCase)); //вывод результат из метода calc
    }
    public static String calc(String input) throws IllegalArgumentException, ScannerException {
        String[] ourCaseArray = input.split(" "); //получаем массив из 3, если не 3 то исключение?
        scannerInputCheck(ourCaseArray);
        String numStrOne = ourCaseArray[0]; // задаем переменную для первого числа
        String numStrTwo = ourCaseArray[2]; // задаем переменную для второго числа
        String operation = ourCaseArray[1]; // задаем переменной operation знак операции из массива


        if ((existNumArab(numStrOne)== true) && (existNumArab(numStrTwo) == true)){ /* сравниваем входит ли 1 и 2 число
             в массив арабских чисел */
            int numOne = Integer.parseInt(numStrOne); //переводим в число в int
            int numTwo = Integer.parseInt(numStrTwo);
            return MathAndConvert.doMath(numOne,numTwo,operation); // считаем пример в методе doMath и возвращаем в main
        } else if ((existNumRomanian(numStrOne) == true) && (existNumRomanian(numStrTwo) == true)){ /* сравниваем входит
            ли 1 и 2 число в массив римских */
            int numOne = MathAndConvert.getConvRomToArab(numStrOne); // переводим из рим в арабское
            int numTwo = MathAndConvert.getConvRomToArab(numStrTwo);
            int resultBeforeConvertToRomanian = Integer.parseInt(MathAndConvert.doMath(numOne,numTwo,operation)); /* считаем пример в
            методе doMath и переводим его в int */
            if (resultBeforeConvertToRomanian <1) { // исключение если результат примера в римских числах меньше 1
                throw new IllegalArgumentException("Нет решения. Число < 1 в римском примере.");
            }
            return MathAndConvert.getResultInRomanian(resultBeforeConvertToRomanian); /* считаем пример в методе
            getResultInRomanian и возвращаем в main */
        } else if (((existNumArab(numStrOne)== true) && (existNumArab(numStrTwo) == false)) ||
                   ((existNumArab(numStrOne)== false) && (existNumArab(numStrTwo) == true))) {
            throw new ScannerException("Складывать можно только числа одного " +
                    "типа.\n Арабские с арабскими. От 1 до 10 включительно.");
        } else if (((existNumRomanian(numStrOne) == true) && (existNumRomanian(numStrTwo) == false)) ||
              ((existNumRomanian(numStrOne) == false) && (existNumRomanian(numStrTwo) == true))){
            throw new ScannerException("Складывать можно только числа одного " +
                    "типа.\n Римские с римскими. От I до X включительно.");
        } else if (((existNumRomanian(numStrOne) == true) && (existNumRomanian(numStrTwo) == false)) ||
                ((existNumRomanian(numStrOne) == false) && (existNumRomanian(numStrTwo) == true))){
            throw new ScannerException("Вы ввели неправильный пример. " +
                    "\n Пример должен быть типа a + b.");
        }
      return null; //  исключение и завершение работы НУЖНО ДОПИСАТЬ
    }

    static boolean existNumArab(String a) throws IllegalArgumentException { //проверка на принадлежность к арабским числам (попробовать из enum)
        String[] arabNumArray = {"1", "2", "3","4","5","6","7","8","9","10"};
        return Arrays.asList(arabNumArray).contains(a);
    }

    static boolean existNumRomanian(String a) throws IllegalArgumentException { //проверка на принадлежность к римским числам (попробовать из enum)
        String[] romanianNumArray = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        return Arrays.asList(romanianNumArray).contains(a);
    }

    static Object scannerInputCheck (String[] scannerInput) throws ScannerException {
        if (Arrays.asList(scannerInput).size() != 3) {
            throw new ScannerException("Пример должен быть с пробелами.");
        }
        return null;
    }
}

class MathAndConvert{ // математические действия
    static String doMath(int num1, int num2, String operation) throws IllegalArgumentException{ // осуществление операции
        int finalResult = 0;
        switch (operation){
            case "+" -> finalResult = num1 + num2;
            case "-" -> finalResult = num1 - num2;
            case "*" -> finalResult = num1 * num2;
            case "/" -> finalResult = num1 / num2;
            default -> {
                throw new IllegalArgumentException("Неверный знак операции. Возможные операции + , - , * , /");
            }
        }
        return String.valueOf(finalResult);
    }

    static int getConvRomToArab(String romanianNumb){ // конвертация римских в арабские
        int convResult = 0;
        switch (romanianNumb){
            case "I" -> convResult = 1;
            case "II" -> convResult = 2;
            case "III" -> convResult = 3;
            case "IV" -> convResult = 4;
            case "V" -> convResult = 5;
            case "VI" -> convResult = 6;
            case "VII" -> convResult = 7;
            case "VIII" -> convResult = 8;
            case "IX" -> convResult = 9;
            case "X" -> convResult = 10;
        }
        return convResult;
    }

    static String getResultInRomanian (int resultInArabian){  //метод перевода с римского на арабское значение
        List romanNumerals = RomanNumber.getReverseSorted(); // присваиваем переменной коллекцию
        int i = 0;
        StringBuilder romanResult = new StringBuilder(); // наш будующий результат

        while ((resultInArabian > 0) && (i < romanNumerals.size())) { // логика конвертации
            RomanNumber curRomanSymbol = (RomanNumber) romanNumerals.get(i);
            if (curRomanSymbol.getValue() <= resultInArabian) {
                romanResult.append(curRomanSymbol.name());
                resultInArabian -= curRomanSymbol.getValue();
            } else {
                i++;
            }
        } return romanResult.toString();
    }
}

