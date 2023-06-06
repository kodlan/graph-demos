package com.graphdemos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import java.util.ArrayList;
import java.util.List;

public class KochCurve extends ApplicationAdapter {

  private float currentX;
  private float currentY;
  private int currentAngle = 90;

  private static class Line {
    float x0, y0;
    float x1, y1;

    public Line(float x0, float y0, float x1, float y1) {
      this.x0 = x0;
      this.y0 = y0;
      this.x1 = x1;
      this.y1 = y1;
    }
  }

  private final List<Line> linesToDraw = new ArrayList<>();

  private ShapeRenderer shapeRenderer;

  @Override
  public void create() {
    shapeRenderer = new ShapeRenderer();

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    int centerX = width / 2;
    int centerY = height / 2;

    System.out.println("width = " + Gdx.graphics.getWidth());
    System.out.println("height = " + Gdx.graphics.getHeight());
    System.out.println("centerX = " + centerX + ", centerY = " + centerY);

    Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    currentX = centerX / 2;
    currentY = centerY;
    koch(5, 500);
  }

  int counter = 0;
  @Override
  public void render() {
    super.render();

    shapeRenderer.begin(ShapeType.Filled);

    shapeRenderer.setColor(Color.RED);

    // draw precalculated lines
    for (int i = 0; i < counter; i ++) {
      Line line = linesToDraw.get(i);
      shapeRenderer.rectLine(line.x0, line.y0, line.x1, line.y1, 1);
    }

    shapeRenderer.end();

    counter += 2;
    if (counter >= linesToDraw.size()) {
      counter = 0;
    }
  }

  private void koch(int level, int length) {
    if (level == 0) {
      forward(length);
      return;
    }

    koch(level - 1, length / 3);

    left(60);

    koch(level - 1, length / 3);

    right(120);

    koch(level - 1, length / 3);

    left(60);

    koch(level - 1, length / 3);
  }

  /**
   * draw line forward
   */
  private void forward(int length) {
    float newX = currentX + length * (float) Math.sin(Math.toRadians(currentAngle));
    float newY = currentY + length * (float) Math.cos(Math.toRadians(currentAngle));

    // shapeRenderer.rectLine(currentX, currentY, newX, newY, 2);
    linesToDraw.add(new Line(currentX, currentY, newX, newY));

    currentX = newX;
    currentY = newY;
  }

  /**
   * rotate pointer left
   */
  private void left(int degrees) {
    currentAngle -= degrees;
  }

  /**
   * rotate pointer right
   */
  private void right(int degrees) {
    currentAngle += degrees;
  }

  @Override
  public void dispose() {
    super.dispose();
    shapeRenderer.dispose();
  }
}
