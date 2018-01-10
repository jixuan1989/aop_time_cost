import cn.edu.tsinghua.aop.CostResult;

public class Test {
  public void doanything() {
    long startTime=0;
    CostResult.getInstance();
    if(CostResult.getInstance().isRecord()) {
      startTime=System.currentTimeMillis();
    }
  }
}
