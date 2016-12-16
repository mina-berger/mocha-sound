
import java.util.ArrayDeque;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author minaberger
 */
public class ArrayOptimizeTest {
  public static void main(String[] args){
long count = 1000000000;
long time;

time = System.currentTimeMillis();
byCase1(count);
long time1 = System.currentTimeMillis() - time;

time = System.currentTimeMillis();
byCase2(count);
long time2 = System.currentTimeMillis() - time;

time = System.currentTimeMillis();
byCase3(count);
long time3 = System.currentTimeMillis() - time;

System.out.println("case1=" + time1);
System.out.println("case2=" + time2);
System.out.println("case3=" + time3);
  }
public static void byCase1(long count){
  ArrayList<Double> buffer = new ArrayList<>();
  for(long i = 0;i < count;i++){
    buffer.add(0d);
    buffer.add(1d);
    while(!buffer.isEmpty()){
      double value = buffer.remove(0);
    }
  }
}
public static void byCase2(long count){
  ArrayDeque<Double> buffer = new ArrayDeque<>();
  for(long i = 0;i < count;i++){
    buffer.add(0d);
    buffer.add(1d);
    while(!buffer.isEmpty()){
      double value = buffer.remove();
    }
  }
}  
public static void byCase3(long count){
  double[] buffer = new double[2];
  int index;
  for(long i = 0;i < count;i++){
    buffer[0] = 0d;
    buffer[1] = 1d;
    index = 0;
    while(index < buffer.length){
      double value = buffer[index];
      index++;
    }
  }
}
}
