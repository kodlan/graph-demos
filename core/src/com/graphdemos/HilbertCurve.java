package com.graphdemos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.graphdemos.util.turtle.TurtleGraph;
import com.graphdemos.util.turtle.TurtleLine;
import java.util.concurrent.ArrayBlockingQueue;

public class HilbertCurve extends ApplicationAdapter {

  private final ArrayBlockingQueue<TurtleLine> linesToDraw = new ArrayBlockingQueue<>(10000);

  private ShapeRenderer shapeRenderer;

  private int height;

  @Override
  public void create() {
    shapeRenderer = new ShapeRenderer();

    int width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();

    int centerX = width / 2;
    int centerY = height / 2;

    System.out.println("width = " + Gdx.graphics.getWidth());
    System.out.println("height = " + Gdx.graphics.getHeight());
    System.out.println("centerX = " + centerX + ", centerY = " + centerY);

    Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    new HilbertCurveTurtleGraph();
  }

  @Override
  public void render() {
    super.render();

    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(Color.RED);

    while(!linesToDraw.isEmpty()) {
      TurtleLine line = linesToDraw.poll();
      shapeRenderer.rectLine(line.x0, line.y0, line.x1, line.y1, 1);
    }

    shapeRenderer.end();
  }
  @Override
  public void dispose() {
    super.dispose();
    shapeRenderer.dispose();
  }

  private class HilbertCurveTurtleGraph extends TurtleGraph {

    public HilbertCurveTurtleGraph() {
      super(10, height - 10, 10);
    }

    private void hilbert(int level, int angle, int length) {
      if (level == 0) {
        return;
      }

      right(angle);
      hilbert(level - 1, -angle, length);

      forward(length);
      right(-angle);
      hilbert(level - 1, angle, length);

      forward(length);
      hilbert(level - 1, angle, length);

      right(-angle);
      forward(length);
      hilbert(level - 1, -angle, length);

      right(angle);
    }

    @Override
    public void turtleDraw() {
      hilbert(6, 90, 10);
    }

    @Override
    public void turtleNotify() {
      linesToDraw.addAll(this.getLinesToDraw());
    }
  }
}
