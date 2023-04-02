package com.graphdemos;

import static com.graphdemos.util.ColorUtil.HSBtoRGB;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DemoMain extends ApplicationAdapter {

  private ShapeRenderer shapeRenderer;

  private int width;
  private int height;

  private final Random rnd = new Random();

  private final Color currentColor = new Color();

  List<Spiral> spiralList = new ArrayList<>();

  @Override
  public void create() {
    shapeRenderer = new ShapeRenderer();

    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();

    int centerX = width / 2;
    int centerY = height / 2;

    System.out.println("width = " + Gdx.graphics.getWidth());
    System.out.println("height = " + Gdx.graphics.getHeight());
    System.out.println("centerX = " + centerX + ", centerY = " + centerY);

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    addSpiral();
    addSpiral();
    addSpiral();

    Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  @Override
  public void render() {
    super.render();

    shapeRenderer.begin(ShapeType.Line);
    renderSpirals();
    shapeRenderer.end();
  }

  private void addSpiral() {
    int boxStartSize = 30;
    spiralList.add(new Spiral(rnd.nextInt(width), rnd.nextInt(height), 5 + rnd.nextInt(boxStartSize)));
  }

  private void addSpiralIfNone() {
    if (spiralList.size() == 0) {
      addSpiral();
    }
  }

  private void renderSpirals() {
    spiralList.forEach(this::nextSpiralFrame);
    removeFinishedSpirals();
    addSpiralIfNone();
  }

  private void removeFinishedSpirals() {
    spiralList = spiralList.stream()
        .filter(this::isSpiralNotFinished)
        .collect(Collectors.toList());
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
      spiral.currentAngle += 5;
      spiral.currentBoxSize += 5;
  }

  private boolean isSpiralNotFinished(Spiral spiral) {
    return spiral.currentBoxSize < 360;
  }

  /**
   * Draw full spiral
   */
  private void drawSpiral(int centerX, int centerY, int boxStartSize) {
    float size = boxStartSize;
    Color color = new Color();

    for (int angle = 0; angle < 360; angle += 5, size += 5) {
      Color.argb8888ToColor(color, HSBtoRGB(angle / 360f, 1, 1));
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
