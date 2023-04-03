package com.graphdemos;

import static com.graphdemos.util.ColorUtil.HSBtoRGB;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class BoxSpiral extends ApplicationAdapter {

  private static final int ANGLE_STEP = 5;
  private static final int BOX_SIZE_STEP = 5;
  private static final int BOX_START_SIZE = 30;
  private static final float MAX_ROTATION_ANGLE = 720f;

  private ShapeRenderer shapeRenderer;

  private final Color currentColor = new Color();

  private Spiral spiral;

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

    spiral = new Spiral(centerX, centerY, BOX_START_SIZE);
  }

  @Override
  public void render() {
    super.render();

    shapeRenderer.begin(ShapeType.Line);
    nextSpiralFrame(spiral);
    shapeRenderer.end();
  }

  private void nextSpiralFrame(Spiral spiral) {
    drawSpiralFrame(spiral);
    cycleSpiral(spiral);
  }

  /**
   * Draw only one rotation of the given spiral
   */
  private void drawSpiralFrame(Spiral spiral) {
    Color.argb8888ToColor(currentColor, HSBtoRGB(spiral.currentAngle / 360f, 1, 1));
    shapeRenderer.setColor(currentColor);
    shapeRenderer.rect(spiral.centerX - spiral.currentBoxSize, spiral.centerY - spiral.currentBoxSize,
        spiral.currentBoxSize, spiral.currentBoxSize, spiral.currentBoxSize * 2, spiral.currentBoxSize * 2,
        1f, 1f, spiral.currentAngle);
  }

  private void cycleSpiral(Spiral spiral) {
    spiral.currentAngle += ANGLE_STEP;
    spiral.currentBoxSize += BOX_SIZE_STEP;
  }

  /**
   * Draw full spiral
   */
  private void drawSpiral(int centerX, int centerY, int boxStartSize) {
    float size = boxStartSize;
    Color color = new Color();

    for (int angle = 0; angle < MAX_ROTATION_ANGLE; angle += ANGLE_STEP, size += BOX_SIZE_STEP) {
      Color.argb8888ToColor(color, HSBtoRGB(angle / MAX_ROTATION_ANGLE, 1, 1));
      shapeRenderer.setColor(color);
      shapeRenderer.rect(centerX - size, centerY - size, size, size, size * 2, size * 2, 1f, 1f, angle);
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    shapeRenderer.dispose();
  }

  private static class Spiral {

    int centerX;
    int centerY;
    int currentBoxSize;
    int currentAngle;

    public Spiral(int centerX, int centerY, int initBoxSize) {
      this.centerX = centerX;
      this.centerY = centerY;
      this.currentBoxSize = initBoxSize;
      this.currentAngle = 0;
    }
  }
}
