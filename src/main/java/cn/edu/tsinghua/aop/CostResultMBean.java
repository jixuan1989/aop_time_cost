package cn.edu.tsinghua.aop;

import java.util.List;

import cn.edu.tsinghua.aop.CostResult.IntPair;
import cn.edu.tsinghua.aop.CostResult.LongPair;

public interface CostResultMBean {
  public long getTotalTimeCost(String className, String methodName);
  public int getTimes(String className, String methodName);
  public List<LongPair> sortTimeCosts();
  public List<IntPair> sortTimes();
  public void startRecord();
  public void stopRecord(); 
  public boolean isRecord();
  
  public List<String> sortTimeCosts2String();
  public List<String> sortTimes2String();
}
