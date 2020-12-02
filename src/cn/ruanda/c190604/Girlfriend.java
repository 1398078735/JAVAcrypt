package cn.ruanda.c190604;

import java.util.Arrays;

public class Girlfriend {
    int[] array = {9, 8, 10, 23, 89, 76, 45, 7, 23, 41};


    public static void main(String[] args) {
        Girlfriend girlfriend = new Girlfriend();
        girlfriend.maopao(girlfriend.array);
        System.out.println();
        girlfriend.shengxu(girlfriend.array);
        System.out.println();
        girlfriend.xuanze(girlfriend.array);


    }

    public int[] maopao(int[] array) {
        int i = 0;
        int j = 0;
        for (i = 0; i < array.length - 1; i++) {
            for (j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int c = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = c;
                }
            }
        }
        for (i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        return null;
    }

    public int[] shengxu(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        return null;
    }

    public int[] xuanze(int[] array) {
        int max = 0;
        int tmp = 0;
        for (int i =0;i < array.length-1;i++){
            max = i;
            for (int j= i+1;j < array.length;j++) {
                if (array[j] < array[max]) {
                    max = j;
                }
            }
                if (max != i){
                    tmp = array[i];
                    array[i] = array[max];
                    array[max] = tmp;
                }
            }
            for (int n =0;n<array.length;n++){
                System.out.print(array[n]+",");
            }
        return null;
    }
}