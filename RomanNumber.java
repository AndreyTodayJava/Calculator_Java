import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum RomanNumber {
    I(1), IV(4), V(5), IX(9), X(10), XL(40),
    L(50), XC(90), C(100); // больше 100 нет смысл добавлять в нашем ТЗ

    private int arabNum;

    RomanNumber(int arabNum){
        this.arabNum = arabNum;
    }

    public int getValue(){
        return arabNum;
    }

    public static List getReverseSorted(){ //метод передачи
        return Arrays.stream(values()) // создаем стрим из массива
                .sorted(Comparator.comparing(RomanNumber::getValue).reversed()) // переворачиваем его задом наперед
                .collect(Collectors.toList()); // создаем коллекцию простого списка
    }
}
