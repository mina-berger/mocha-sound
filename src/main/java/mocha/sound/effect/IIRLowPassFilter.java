/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mocha.sound.effect;

import mocha.sound.DoubleBuffer;
import static java.lang.Math.PI;
import mocha.sound.SoundConstants;

/**
 *
 * @author minaberger
 */
public class IIRLowPassFilter implements SoundConstants {

  public static final double DEFAULT_QUALITY = 1.0 / Math.sqrt(2.0);
  DoubleBuffer buffer_b;
  DoubleBuffer buffer_a;

  double[] coef_b;
  double[] coef_a;

  double cutoff;
  double quality;

  public IIRLowPassFilter(double cutoff) {
    this(cutoff, DEFAULT_QUALITY);
  }

  public IIRLowPassFilter(double cutoff, double quality) {
    this.cutoff = cutoff;
    this.quality = quality;
    buffer_b = new DoubleBuffer(2);
    buffer_a = new DoubleBuffer(2);
    coef_b = new double[3];
    coef_a = new double[3];
    update();
  }

  public void set(double cutoff, double quality) {
    if (this.cutoff == cutoff && this.quality == quality) {
      return;
    }
    this.cutoff = cutoff;
    this.quality = quality;
    update();
  }

  public void setQuality(double quality) {
    if (this.quality == quality) {
      return;
    }
    this.quality = quality;
    update();
  }

  public void setCutoff(double cutoff) {
    if (this.cutoff == cutoff) {
      return;
    }
    this.cutoff = cutoff;
    update();
  }

  private void update() {
    double fc = Math.tan(PI * cutoff / SAMPLE_RATE) / (2.0 * PI);
    double denom = 1.0 + 2.0 * PI * fc / quality + 4.0 * PI * PI * fc * fc;
    coef_b[0] = 4.0 * PI * PI * fc * fc / denom;
    coef_b[1] = 8.0 * PI * PI * fc * fc / denom;
    coef_b[2] = 4.0 * PI * PI * fc * fc / denom;
    coef_a[0] = 0;
    coef_a[1] = (8.0 * PI * PI * fc * fc - 2.0) / denom;
    coef_a[2] = (1.0 - 2.0 * PI * fc / quality + 4.0 * PI * PI * fc * fc) / denom;
  }

  public double process(double read_value) {
    double return_value = 0;
    return_value += read_value * coef_b[0];
    for (int i = 1; i < 3; i++) {
      return_value += buffer_b.get(i - 1) * coef_b[i];
      return_value -= buffer_a.get(i - 1) * coef_a[i];
    }
    buffer_b.put(read_value);
    buffer_a.put(return_value);
    return return_value;
  }
}
