package com.graphdemos.util.turtle;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class TurtleGraphRenderer {

  private final ArrayBlockingQueue<TurtleLine> linesToDraw = new ArrayBlockingQueue<>(10000);

  public void addLinesToRender(List<TurtleLine> newLines) {
    this.linesToDraw.addAll(newLines);
  }

  public void renderLines(LineRenderer lineRenderer) {
    while(!linesToDraw.isEmpty()) {
      TurtleLine line = linesToDraw.poll();
      lineRenderer.render(line);
    }
  }

  @FunctionalInterface
  public interface LineRenderer {
    void render(TurtleLine line);
  }
}
