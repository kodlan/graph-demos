package com.graphdemos.util.turtle;

public class TurtleLine {
  public float x0, y0;
  public float x1, y1;

  public TurtleLine(float x0, float y0, float x1, float y1) {
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
  }

  @Override
  public String toString() {
    return "TurtleLine{" +
        "x0=" + x0 +
        ", y0=" + y0 +
        ", x1=" + x1 +
        ", y1=" + y1 +
        '}';
  }
}
