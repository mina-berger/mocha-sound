/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mocha.sound.effect;

import mocha.sound.DoubleMap;
import mocha.sound.Modulator;
import mocha.sound.SoundReadable;

public class LowPassFilterModule implements SoundReadable{
  IIRLowPassFilter filter;
  SoundReadable source;
  DoubleMap cutoff;
  DoubleMap quality;
  Modulator cutoffModulator;
  Modulator qualityModulator;
  public LowPassFilterModule(SoundReadable source, DoubleMap cutoff, DoubleMap quality){
    this.filter = new IIRLowPassFilter(cutoff.getInitial(), quality.getInitial());
    this.source = source;
    if(source.getChannel() != 1){
      throw new IllegalArgumentException("monoral only");
    }
    this.cutoff = cutoff;
    this.quality = quality;
    cutoffModulator = null;
    qualityModulator = null;
  }

  public void setCutoffModulator(Modulator cutoffModulator) {
    this.cutoffModulator = cutoffModulator;
  }

  public void setQualityModulator(Modulator qualityModulator) {
    this.qualityModulator = qualityModulator;
  }

  @Override
  public long length() {
    return source.length();
  }

  @Override
  public int getChannel() {
    return source.getChannel();
  }

  @Override
  public double read() {
    double nextCutoff  = cutoffModulator  == null?cutoff .next():cutoffModulator.modulate(cutoff .next());
    double nextQuality = qualityModulator == null?quality.next():qualityModulator.modulate(quality.next());
    filter.set(nextCutoff, nextQuality);
    
    return filter.process(source.read());
  }
  
}
