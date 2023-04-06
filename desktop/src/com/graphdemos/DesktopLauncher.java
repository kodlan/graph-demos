package com.graphdemos;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

  private static final String RESOLUTION_HORIZONTAL = "h";
  private static final int WIDTH = 1920;
  private static final int HEIGHT = 1080;

  private static final Map<String, Class> demos = Map.of(
      "BoxSpirals", BoxSpirals.class,
      "BoxSpiral", BoxSpiral.class
  );

  private static String currentResolution = "h";
  private static String currentDemo = "BoxSpiral";


  public static void main(String[] arg)
      throws InvocationTargetException, InstantiationException, IllegalAccessException {
    parseArgs(arg);

    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setForegroundFPS(60);
    config.setTitle("GraphDemos");
    config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 2);

    if (currentResolution.equals(RESOLUTION_HORIZONTAL)) {
      config.setWindowedMode(WIDTH, HEIGHT);
    } else {
      config.setWindowedMode(HEIGHT, WIDTH);
    }

    new Lwjgl3Application((ApplicationListener) demos.get(currentDemo).getDeclaredConstructors()[0].newInstance(), config);
  }

  private static void parseArgs(String[] arg) {
    Options options = new Options();

    Option resolution = new Option("r", "resolution", true, "Resolution (v for vertical, h for horizontal");
    resolution.setRequired(false);
    options.addOption(resolution);

    String allDemos = String.join(", ", demos.keySet());
    Option demoName = new Option("d", "demo", true, "Demo name: " + allDemos);
    demoName.setRequired(false);
    options.addOption(demoName);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, arg);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("graph-demos", options);

      System.exit(1);
    }

    currentResolution = cmd.getOptionValue("resolution", "h");
    currentDemo = cmd.getOptionValue("demo", "BoxSpiral");
  }
}
