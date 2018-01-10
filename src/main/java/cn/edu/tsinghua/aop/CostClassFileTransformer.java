package cn.edu.tsinghua.aop;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.security.ProtectionDomain;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Xiangdong Huang on 2018/1/9.
 */
public class CostClassFileTransformer implements ClassFileTransformer {

  public static void premain(String args, Instrumentation inst) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
    inst.addTransformer(new CostClassFileTransformer());
    // 首先建立一个MBeanServer,MBeanServer用来管理我们的MBean,通常是通过MBeanServer来获取我们MBean的信息，间接
    // 调用MBean的方法，然后生产我们的资源的一个对象。
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    String domainName = "performance";
    //为MBean（下面的new Hello()）创建ObjectName实例
    ObjectName helloName = new ObjectName(domainName+":name=CostResult");
    // 将new Hello()这个对象注册到MBeanServer上去
    mbs.registerMBean(CostResult.getInstance(),helloName);
  }


  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

    ClassReader reader = new ClassReader(classfileBuffer);
    ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);//
    reader.accept(new CostClassVisitor(writer), 8);
    return writer.toByteArray();
  }
}
