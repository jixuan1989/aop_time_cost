package cn.edu.tsinghua.aop;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by Xiangdong Huang on 2018/1/9.
 */
public class CostClassVisitor extends ClassVisitor {
  
  public CostClassVisitor(ClassVisitor classVisitor) {
    super(Opcodes.ASM5, classVisitor);
  }
  
  @Override
  public void visit(int arg0, int arg1, String className, String arg3, String superClassName, String[] arg5) {
    // TODO Auto-generated method stub
    super.visit(arg0, arg1, className, arg3, superClassName, arg5);
    this.className=className;
  }
  String className;
  @Override
  public MethodVisitor visitMethod(int access, final String methodName, String desc, String signature,
      String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
      mv = new AdviceAdapter(Opcodes.ASM5, mv, access, methodName, desc) {
      
      private boolean inject = false;

      
      @Override
      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Type.getDescriptor(Cost.class).equals(desc)) {
          inject = true;
        }
        return super.visitAnnotation(desc, visible);
      }
      @Override
      protected void onMethodEnter() {
        if (inject) {
//          mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//          mv.visitVarInsn(LSTORE, 1);
          
          mv.visitInsn(LCONST_0);
          mv.visitVarInsn(LSTORE, 1);
          mv.visitMethodInsn(INVOKESTATIC, "cn/edu/tsinghua/aop/CostResult", "getInstance", "()Lcn/edu/tsinghua/aop/CostResult;", false);
          mv.visitMethodInsn(INVOKEVIRTUAL, "cn/edu/tsinghua/aop/CostResult", "isRecord", "()Z", false);
          Label l2 = new Label();
          mv.visitJumpInsn(IFEQ, l2);
          mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
          mv.visitVarInsn(LSTORE, 1);
          mv.visitLabel(l2);
          mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.LONG}, 0, null);
        }
      }

      @Override
      protected void onMethodExit(int opcode) {
        if (inject) {
//          mv.visitMethodInsn(INVOKESTATIC, "cn/edu/tsinghua/aop/CostResult", "getInstance", "()Lcn/edu/tsinghua/aop/CostResult;", false);
//          mv.visitLdcInsn(className);
//          mv.visitLdcInsn(methodName);
//          mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//          mv.visitVarInsn(LLOAD, 1);
//          mv.visitInsn(LSUB);
//          mv.visitMethodInsn(INVOKEVIRTUAL, "cn/edu/tsinghua/aop/CostResult", "addTimeCost", "(Ljava/lang/String;Ljava/lang/String;J)V", false);
          mv.visitMethodInsn(INVOKESTATIC, "cn/edu/tsinghua/aop/CostResult", "getInstance", "()Lcn/edu/tsinghua/aop/CostResult;", false);
          mv.visitMethodInsn(INVOKEVIRTUAL, "cn/edu/tsinghua/aop/CostResult", "isRecord", "()Z", false);
          Label l6 = new Label();
          mv.visitJumpInsn(IFEQ, l6);
          mv.visitMethodInsn(INVOKESTATIC, "cn/edu/tsinghua/aop/CostResult", "getInstance", "()Lcn/edu/tsinghua/aop/CostResult;", false);
          mv.visitLdcInsn(className);
          mv.visitLdcInsn(methodName);
          mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
          mv.visitVarInsn(LLOAD, 1);
          mv.visitInsn(LSUB);
          mv.visitMethodInsn(INVOKEVIRTUAL, "cn/edu/tsinghua/aop/CostResult", "addTimeCost", "(Ljava/lang/String;Ljava/lang/String;J)V", false);
          mv.visitLabel(l6);
          mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {Opcodes.DOUBLE}, 0, null);
        }
      }
    };
    return mv;
  }
}
