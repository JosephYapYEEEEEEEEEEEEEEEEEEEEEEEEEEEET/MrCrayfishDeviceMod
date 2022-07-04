package com.mrcrayfish.device.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.Stack;

/**
 * @author MrCrayfish
 */
public class GLHelper {
    public static Stack<Scissor> scissorStack = new Stack<>();

    public static void pushScissor(int x, int y, int width, int height) {
        if (scissorStack.size() > 0) {
            Scissor scissor = scissorStack.peek();
            x = Math.max(scissor.x, x);
            y = Math.max(scissor.y, y);
            width = x + width > scissor.x + scissor.width ? scissor.x + scissor.width - x : width;
            height = y + height > scissor.y + scissor.height ? scissor.y + scissor.height - y : height;
        }

        Minecraft mc = Minecraft.getInstance();
        ScaledResolution resolution = new ScaledResolution(mc);
        int scale = resolution.getScaleFactor();
        GL11.glScissor(x * scale, mc.getWindow().getHeight() - y * scale - height * scale, Math.max(0, width * scale), Math.max(0, height * scale));
        scissorStack.push(new Scissor(x, y, width, height));
    }

    public static void popScissor() {
        if (!scissorStack.isEmpty()) {
            scissorStack.pop();
        }
        restoreScissor();
    }

    private static void restoreScissor() {
        if (!scissorStack.isEmpty()) {
            Scissor scissor = scissorStack.peek();
            Minecraft mc = Minecraft.getInstance();
            ScaledResolution resolution = new ScaledResolution(mc);
            int scale = resolution.getScaleFactor();
            RenderSystem.enableScissor(scissor.x * scale, mc.getWindow().getHeight() - scissor.y * scale - scissor.height * scale, Math.max(0, scissor.width * scale), Math.max(0, scissor.height * scale));
        }
    }

    public static boolean isScissorStackEmpty() {
        return scissorStack.isEmpty();
    }

    /**
     * Do not call! Used for core only.
     */
    public static void clearScissorStack() {
        scissorStack.clear();
    }

    public static Color getPixel(int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        ScaledResolution resolution = new ScaledResolution(mc);
        int scale = resolution.getScaleFactor();
        ByteBuffer buffer = BufferUtils.createByteBuffer(3);
        RenderSystem.readPixels(x * scale, mc.getWindow().getHeight() - y * scale - scale, 1, 1, GL11.GL_RGB, GL11.GL_FLOAT, buffer);
        return new Color(buffer.get(0), buffer.get(1), buffer.get(2));
    }

    public static class Scissor {
        public int x;
        public int y;
        public int width;
        public int height;

        Scissor(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
