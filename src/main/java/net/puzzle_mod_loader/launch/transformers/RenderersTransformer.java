package net.puzzle_mod_loader.launch.transformers;

import net.puzzle_mod_loader.launch.ClassTransformer;
import org.objectweb.asm.*;

public class RenderersTransformer implements ClassTransformer {
    @Override
    public byte[] transform(byte[] bytes, String className) {
        if (className.equals("net.minecraft.client.renderer.GameRenderer")) {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(0);
            classReader.accept(new ClassVisitor(ASM7, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    final boolean uiRender = name.equals("render");
                    return new MethodVisitor(ASM7, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            if (opcode == INVOKEVIRTUAL && owner.equals("net/minecraft/client/gui/screens/Screen") && name.equals("render")) {
                                super.visitMethodInsn(INVOKESTATIC, "net/puzzle_mod_loader/core/ClientHooks", "clientGuiRender", "(Lnet/minecraft/client/gui/screens/Screen;IIF)V", false);
                            } else {
                                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                            }
                        }

                        @Override
                        public void visitInsn(int opcode) {
                            if (uiRender && opcode == RETURN) {
                                super.visitMethodInsn(INVOKESTATIC, "net/puzzle_mod_loader/core/ClientHooks", "postUIRender", "()V", false);
                            }
                            super.visitInsn(opcode);
                        }
                    };
                }
            }, 0);
            return classWriter.toByteArray();
        }
        if (className.equals("net.minecraft.client.renderer.LevelRenderer")) {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(0);
            classReader.accept(new ClassVisitor(ASM7, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    if (name.equals("renderLevel")) {
                        return new MethodVisitor(ASM7, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                            @Override
                            public void visitInsn(int opcode) {
                                if (opcode == RETURN) {
                                    super.visitMethodInsn(INVOKESTATIC, "net/puzzle_mod_loader/core/ClientHooks", "postWorld", "()V", false);
                                }
                                super.visitInsn(opcode);
                            }
                        };
                    }
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }, 0);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
