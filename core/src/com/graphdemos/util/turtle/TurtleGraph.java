package com.graphdemos.util.turtle;

import java.util.ArrayList;
import java.util.List;

public abstract class TurtleGraph {
  private static final int DELAY = 2;

  private float currentX;
  private float currentY;
  private int currentAngle = 90;

  private int currentDelay = DELAY;

  private final List<TurtleLine> linesToDraw = new ArrayList<>();

  private final TurtleGraphRenderer renderer;

  public TurtleGraph(int currentX, int currentY, TurtleGraphRenderer renderer) {
    this.currentX = currentX;
    this.currentY = currentY;
    this.renderer = renderer;
    init();
  }

  public TurtleGraph(int currentX, int currentY, int currentDelay, TurtleGraphRenderer renderer) {
    this(currentX, currentY, renderer);
    this.currentDelay = currentDelay;
    init();
  }

  private void init() {
    Thread turtleThread = new Thread() {
      @Override
      public void run() {
        super.run();
        TurtleGraph.this.turtleDraw();
      }
    };

    turtleThread.start();
  }

  public abstract void turtleDraw();

  /**
   * draw line forward
   */
  public void forward(int length) {
    float newX = currentX + length * (float) Math.sin(Math.toRadians(currentAngle));
    float newY = currentY + length * (float) Math.cos(Math.toRadians(currentAngle));

    linesToDraw.add(new TurtleLine(currentX, currentY, newX, newY));

    turtleNotifyAndSleep();

    currentX = newX;
    currentY = newY;
  }

  /**
   * rotate pointer left
   */
  public void left(int degrees) {
    currentAngle -= degrees;
  }

  /**
   * rotate pointer right
   */
  public void right(int degrees) {
    currentAngle += degrees;
  }

  public List<TurtleLine> getLinesToDraw() {
    return linesToDraw;
  }

  private void threadSleep() {
    try {
      Thread.sleep(currentDelay);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void turtleNotifyAndSleep() {
    if (this.linesToDraw.size() != 0) {
      TurtleGraph.this.renderer.addLinesToRender(this.linesToDraw);
      linesToDraw.clear();
      threadSleep();
    }
  }
}
