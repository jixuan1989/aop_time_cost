package cn.edu.tsinghua.aop.demo;

import cn.edu.tsinghua.aop.Cost;

/**
 * Created by bazhang on 2017/3/1.
 */
public class Any {

  
  @Cost
  public long show() {
    long time=System.currentTimeMillis();
    double a=0;
    for(int i=0;i<10000000;i++) {
      a+=Math.random();
    }
    return System.currentTimeMillis()-time;
  }
  //@Cost
  public long show2() {
    long time =System.currentTimeMillis();
    double a=0;
    for(int i=0;i<20000000;i++) {
      a+=Math.random();
    }
    return System.currentTimeMillis()-time;
  }
}
