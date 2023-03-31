package com.graphdemos.util;

public class ColorUtil {

  // Stolen from java.awt.Color
  public static int HSBtoRGB(float var0, float var1, float var2) {
    int var3 = 0;
    int var4 = 0;
    int var5 = 0;
    if (var1 == 0.0F) {
      var3 = var4 = var5 = (int) (var2 * 255.0F + 0.5F);
    } else {
      float var6 = (var0 - (float) Math.floor((double) var0)) * 6.0F;
      float var7 = var6 - (float) Math.floor((double) var6);
      float var8 = var2 * (1.0F - var1);
      float var9 = var2 * (1.0F - var1 * var7);
      float var10 = var2 * (1.0F - var1 * (1.0F - var7));
      switch ((int) var6) {
        case 0:
          var3 = (int) (var2 * 255.0F + 0.5F);
          var4 = (int) (var10 * 255.0F + 0.5F);
          var5 = (int) (var8 * 255.0F + 0.5F);
          break;
        case 1:
          var3 = (int) (var9 * 255.0F + 0.5F);
          var4 = (int) (var2 * 255.0F + 0.5F);
          var5 = (int) (var8 * 255.0F + 0.5F);
          break;
        case 2:
          var3 = (int) (var8 * 255.0F + 0.5F);
          var4 = (int) (var2 * 255.0F + 0.5F);
          var5 = (int) (var10 * 255.0F + 0.5F);
          break;
        case 3:
          var3 = (int) (var8 * 255.0F + 0.5F);
          var4 = (int) (var9 * 255.0F + 0.5F);
          var5 = (int) (var2 * 255.0F + 0.5F);
          break;
        case 4:
          var3 = (int) (var10 * 255.0F + 0.5F);
          var4 = (int) (var8 * 255.0F + 0.5F);
          var5 = (int) (var2 * 255.0F + 0.5F);
          break;
        case 5:
          var3 = (int) (var2 * 255.0F + 0.5F);
          var4 = (int) (var8 * 255.0F + 0.5F);
          var5 = (int) (var9 * 255.0F + 0.5F);
      }
    }

    return -16777216 | var3 << 16 | var4 << 8 | var5 << 0;
  }

}
