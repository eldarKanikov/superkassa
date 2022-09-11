import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//htt ps://team.sup erkassa.ru/back end_tz

public class Main {
    private static String[][] arrays;
    private static int sizeArray;
    private static int countArrays;

    public static void main(String[] args) {
        arrays = readFromFile("src/main/resources/input.json");
        sizeArray = arrays[0].length;
        countArrays = arrays.length;
        //Создадим массивы для будущих возможных результатов момбинаций
        for (int i = 1; i <= sizeArray; i++){
            String[][] resultCombo = new String[i][sizeArray];
            recursive(resultCombo, 0,0);
        }
    }

    private static void recursive(String[][] resultCombo, int indexOfResultCombo, int beginIndexOfArrays) {
        if (indexOfResultCombo == resultCombo.length) {
            return;
        }
        for(int i = beginIndexOfArrays; i < countArrays; i++) {
            resultCombo[indexOfResultCombo] = arrays[i];
            if (isFullyCombo(resultCombo)) {
                printArray(resultCombo);
            }
            //Суть в том что мы проверяем "полноценность" комбинации для всех сочетаний(без повторений)
            // массивов количеством от 1 до sizeArray
            recursive(resultCombo, indexOfResultCombo + 1, i + 1);
        }
    }

    private static boolean isFullyCombo(String[][] arraysForCheck) {
        String[] comboArray = new String[sizeArray];
        for (String[] array : arraysForCheck) {
            if (isEmptyArray(array)) {
                return false;
            }
            for (int i = 0; i < sizeArray; i++) {
                if (comboArray[i] != null && array[i] != null) {
                    return false;
                }
                if (comboArray[i] == null && array[i] != null) {
                    comboArray[i] = array[i];
                }
            }
        }
        for (String el : comboArray) {
            if (el == null) {
                return false;
            }
        }
        return true;
    }

    private static String[][] readFromFile(String path){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(path), String[][].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void printArray(String[][] arrays){
        System.out.println(Arrays.deepToString(arrays));
    }

    private static boolean isEmptyArray(String[] array){
        for (String s : array) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }
}
