package com.graphdemos;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HilbertCurve extends ApplicationAdapter {

  private ShapeRenderer shapeRenderer;

  private int centerX;
  private int centerY;

  @Override
  public void create() {
    shapeRenderer = new ShapeRenderer();

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    centerX = width / 2;
    centerY = height / 2;

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

    hilbert(6, 90, 20);

    shapeRenderer.end();
  }

  private float currentAngle = 0;
  private float currentX = centerX;
  private float currentY = centerY;

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
