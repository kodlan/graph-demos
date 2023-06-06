package com.graphdemos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import java.util.Map;

public class HilbertCurve extends ApplicationAdapter {

  private ShapeRenderer shapeRenderer;

  private float currentAngle = 0;
  private float currentX;
  private float currentY;

  private Map<Integer, Color> colorPerLevelMap = Map.of(
      1, Color.GREEN,
      2, Color.RED,
      3, Color.BLUE,
      4, Color.BROWN,
      5, Color.CORAL,
      6, Color.GOLD
  );

  @Override
  public void create() {
    shapeRenderer = new ShapeRenderer();

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    int centerX = width / 2;
    int centerY = height / 2;
    currentX = 40;
    currentY = 40;

    System.out.println("width = " + Gdx.graphics.getWidth());
    System.out.println("height = " + Gdx.graphics.getHeight());
    System.out.println("centerX = " + centerX + ", centerY = " + centerY);

    Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  @Override
  public void render() {
    super.render();

    shapeRenderer.begin(ShapeType.Filled);

    shapeRenderer.setColor(Color.RED);

    hilbert(6, 90, 10);

    shapeRenderer.end();
  }

  private void turn(float angle) {
    this.currentAngle += angle;
  }

  private void forward(float length) {
    float newX = currentX + length * (float) Math.sin(Math.toRadians(currentAngle));
    float newY = currentY + length * (float) Math.cos(Math.toRadians(currentAngle));

    shapeRenderer.rectLine(currentX, currentY, newX, newY, 2);

    currentX = newX;
    currentY = newY;
  }

  private void hilbert(int level, float angle, float length) {
    if (level == 0) {
      return;
    }

    shapeRenderer.setColor(colorPerLevelMap.get(level));

    turn(angle);
    hilbert(level - 1, -angle, length);

    forward(length);
    turn(-angle);
    hilbert(level - 1, angle, length);

    forward(length);
    hilbert(level - 1, angle, length);

    turn(-angle);
    forward(length);
    hilbert(level - 1, -angle, length);

    turn(angle);
  }

  @Override
  public void dispose() {
    super.dispose();
    shapeRenderer.dispose();
  }
}
