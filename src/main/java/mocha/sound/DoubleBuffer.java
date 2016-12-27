package mocha.sound;

public class DoubleBuffer {

  double[] values;
  int length;
  int index;  

public DoubleBuffer(int length) {
  values = new double[length];
  this.length = length;
  index = length - 1;
}

public int length() {
  return length;
}

void next() {
  index = ++index % length;
}

public double current() {
  return values[index];
}
public double get(int shift) {
  if (shift >= length || shift < 0) {
    return 0;
  }
  int get_index = (index + length - shift) % length;        
  return values[get_index];
}  

public double put(double value) {
  next();
  double old = values[index];
  values[index] = value;
  return old;
}

public static void main(String[] args) {
  DoubleBuffer buf = new DoubleBuffer(5);
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println("put 1");
  System.out.println("old=" + buf.put(1));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("put 2");
  System.out.println("old=" + buf.put(2));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("put 3");
  System.out.println("old=" + buf.put(3));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("put 4");
  System.out.println("old=" + buf.put(4));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("put 5");
  System.out.println("old=" + buf.put(5));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("put 6");
  System.out.println("old=" + buf.put(6));
  System.out.println("index=" + buf.index + ":value=" + buf.values[buf.index]);
  System.out.println();
  System.out.println("confirmation");
  System.out.println(buf.current());
  System.out.println("[-1]=" + buf.get(-1));
  System.out.println("[0]=" + buf.get(0));
  System.out.println("[1]=" + buf.get(1));
  System.out.println("[2]=" + buf.get(2));
  System.out.println("[3]=" + buf.get(3));
  System.out.println("[4]=" + buf.get(4));
  System.out.println("[5]=" + buf.get(5));
  System.out.println("[6]=" + buf.get(6));
  System.out.println();
}

}
