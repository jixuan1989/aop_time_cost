package cn.edu.tsinghua.aop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CostResult implements CostResultMBean{
  private static CostResult instance=new CostResult();
  private static boolean record=false;
  
  
  private ConcurrentHashMap<String, ConcurrentHashMap<String, Long> > costs=new ConcurrentHashMap<>();
  private ConcurrentHashMap<String, ConcurrentHashMap<String, Integer> > times=new ConcurrentHashMap<>();
  private CostResult() {}
  public static CostResult getInstance() {return instance;}

  public void addTimeCost(String className, String methodName, long value) {
    ConcurrentHashMap<String, Long> methods=costs.computeIfAbsent(className, name-> new ConcurrentHashMap<>());
    methods.compute(methodName, (k, v) -> v==null? value: v+value);
    
    ConcurrentHashMap<String, Integer> methods2=times.computeIfAbsent(className, name-> new ConcurrentHashMap<>());
    methods2.compute(methodName, (k, v) -> v==null? 0: v+1);
    
  }
  @Override
  public long getTotalTimeCost(String className, String methodName) {
    if(!costs.containsKey(className)) {
      return 0L;
    }
    return costs.get(className).getOrDefault(methodName, 0L);
  }
  
  @Override
  public int getTimes(String className, String methodName) {
    if(!costs.containsKey(className)) {
      return 0;
    }
    return times.get(className).getOrDefault(methodName, 0);
  }
  
  
  
  
  @Override
  public List<LongPair> sortTimeCosts(){
    List<LongPair> result=new ArrayList<>();
    costs.forEach((classname, methods)->{
      methods.forEach((method, value)->{result.add(new LongPair(classname+"#"+method, value));});
    });
    Collections.sort(result);
    return result;
  }
  
  @Override
  public List<String> sortTimeCosts2String() {
    List<String> result=new ArrayList<>();
    costs.forEach((classname, methods)->{
      methods.forEach((method, value)->{result.add(classname+"#"+method +", " + value);});
    });
    Collections.sort(result);
    return result;
  }
  
  @Override
  public List<IntPair> sortTimes() {
    List<IntPair> result=new ArrayList<>();
    times.forEach((classname, methods)->{
      methods.forEach((method, value)->{result.add(new IntPair(classname+"#"+method, value));});
    });
    Collections.sort(result);
    return result;
  }

  @Override
  public List<String> sortTimes2String() {
    List<String> result=new ArrayList<>();
    times.forEach((classname, methods)->{
      methods.forEach((method, value)->{result.add(classname+"#"+method +", " + value);});
    });
    Collections.sort(result);
    return result;
  }
  
  
  @Override
  public void startRecord() {
    record=true;
  }
  @Override
  public void stopRecord() {
    record=false;
    costs.clear();
    times.clear();
  }
  @Override
  public boolean isRecord() {
    return record;
  }
  
  static class LongPair implements Serializable, Comparable<LongPair>{
    String key;
    long value;
    LongPair(String key, long value){
      this.key=key;
      this.value=value;
    }
    public String toString() {
      return key+", " + value;
    }
    @Override
    public int compareTo(LongPair o) {
      return Long.compare(this.value,o.value);
    }
  }
  static class IntPair implements Serializable, Comparable<IntPair>{
    String key;
    int value;
    IntPair(String key, int value){
      this.key=key;
      this.value=value;
    }
    public String toString() {
      return key+", " + value;
    }
    @Override
    public int compareTo(IntPair o) {
      return this.value-o.value;
    }
  }

}
