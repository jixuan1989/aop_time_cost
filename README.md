# aop_time_cost
using ASM to calculate functions' time costs.


#How to Use

Firstly, you need add dependiencies:

```xml
	<dependencies>
		<!-- https://mvnrepository.com/artifact/org.ow2.asm/asm -->
		<dependency>
			<groupId>org.ow2.asm</groupId>
			<artifactId>asm</artifactId>
			<version>5.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.ow2.asm/asm-commons -->
		<dependency>
			<groupId>org.ow2.asm</groupId>
			<artifactId>asm-commons</artifactId>
			<version>5.1</version>
		</dependency>
	</dependencies>
```

Then, compile `cn.edu.tsinghua.aop` package and `src/main/resources/META-INF/MANIFEST.MF` as a jar (or add the jar if I publish it in official Maven Repo). I suppose the jar name is `aopTimeCost.jar`, and the jar is on `/Users/yourname/Desktop/aopTimeCost.jar`.

Thirdly, if you are using Eclipse, then set the `Run Configuration` for your Main class:
`VM arguments:` `-javaagent:/Users/hxd/Desktop/aopTimeCost.jar`.

If you are using Idea, see you tomorrow. (The configuration should be similar, I think.)

Now you can enjoy the powerful of this tool:

For example, you have a class like this:
```java
public class Test {
  public void doanything() {
    System.out.print("Hello World.");
  }
}
```

And you want to know how many times that the function `doanything` is called and the total time cost of the fucntion, just modify your code like this:

```java
public class Test {
  @Cost
  public void doanything() {
    System.out.print("Hello World.");
  }
}
```

It is almost done! By default, I disable the statistics information. To enable the information, There are two options:

1. using the code to enable the statistics:

```java
CostResult.getInstance().startRecord();
```
and using the code to disable the staticstics again:

```java
CostResult.getInstance().stopRecord();
```

2. using JMX (e.g., jconsole), the MBean is `performance.CostResult`, you can click `startRecord()` to enable it and click `stopRecord()` to disable the information.

Now it is time to see the result:

If you want to know the statistics information, just open jconsole and find `performance.CostResult` MBean, these functions are useful:

* `getTimes(className, methodName)`, return the times that the method is called. Notice that, suppose your class is `cn.edu.tsinghua.hxd.Test`, the the className is `cn/edu/tsinghua/hxd/Test`.

* `getTotalTimeCost(className, methodName)`, return the total time cost that the method spends. Notice that, suppose your class is `cn.edu.tsinghua.hxd.Test`, the the className is `cn/edu/tsinghua/hxd/Test`.

* `sortTimes2String()` return the called times of all the functions that you are interested. The result is sorted.

*  `sortTimeCosts2String()` return the total time costs for each functions that you are interested. The result is sorted.

*  `sortTimes`, similar with `sortTimes2String()` while the result is `List<IntPair>`, you can only call this function by coding rather than jconsole.

*  `sortTimeCosts2String()` , similar with `sortTimeCosts2String()` while the result is `List<LongPair>`, you can only call this function by coding rather than jconsole.

* `startRecord()`, enable the record.
* `stopRecord()`,  disable the record and clear the previous results. 





