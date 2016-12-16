package mocha.sound;

import java.util.Map.Entry;
import java.util.TreeMap;

public class DoubleMap extends TreeMap<Long, Double> implements SoundConstants{

  double sampleRate;
  long index;
  double keyFloor;
  double keyCeiling;
  double valueFloor;
  double valueCeiling;
  boolean updated;

public DoubleMap(double initialValue) {
  this(SAMPLE_RATE, initialValue);
}

public DoubleMap(double sampleRate, double initialValue) {
  this.sampleRate = sampleRate;
  put(0l, initialValue);
  index = 0;
  updated = false;
}

public void putSecondValue(double second, double value) {
  put((long) (second * sampleRate), value);
  updated = false;
}

  public double next() {
    return getValue(index++);
  }

private double getValue(long index) {
  if(updated && keyFloor == index){
    return valueFloor;
  }
  if(updated && keyCeiling == index){
    return valueCeiling;
  }
  if(!updated || index < keyFloor || index > keyCeiling){
    Entry<Long, Double> floor = floorEntry(index);
    Entry<Long, Double> ceiling = ceilingEntry(index);
    if (floor == null && ceiling == null) {
      throw new IllegalStateException("map should contain at least one entry");
    }
    if (floor == null) {
      floor = ceiling;
    } else if (ceiling == null) {
      ceiling = floor;
    }
    keyFloor = floor.getKey();
    keyCeiling = ceiling.getKey();
    valueFloor = floor.getValue();
    valueCeiling = ceiling.getValue();
    updated = true;
  }
  if(keyCeiling == keyFloor){
    return valueFloor;
  }

  return valueFloor + (valueCeiling - valueFloor) * (index - keyFloor) / (keyCeiling - keyFloor);

}
public DoubleMap createSubMap(double second){
  long endIndex = (long) (second * sampleRate);
  double endValue = getValue(endIndex);
  DoubleMap subMap = new DoubleMap(sampleRate, get(0l));
  for(long key:keySet()){
    if(key >= endIndex){
      break;
    }
    subMap.put(key, get(key));
  }
  subMap.put(endIndex, endValue);
  return subMap;
}
public void multiply(double ratio){
  for(long key:keySet()){
    put(key, get(key) * ratio);
  }
}
  
  public static void main(String[] args){
    DoubleMap map = new DoubleMap(0);
    map.putSecondValue(1, 1);
    map.putSecondValue(2, 0);
    DoubleMap subMap = map.createSubMap(1.5);
    subMap.multiply(2);
    for(long key:subMap.keySet()){
      System.out.println(key + ":" + subMap.get(key));
    }
    
  }
}
