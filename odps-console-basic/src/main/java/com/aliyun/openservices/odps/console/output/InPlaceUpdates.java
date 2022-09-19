package com.aliyun.openservices.odps.console.output;

/**
 * Created by onesuper on 16/5/23.
 */
import static org.fusesource.jansi.Ansi.ansi;

import java.io.PrintStream;

import org.fusesource.jansi.Ansi;

public class InPlaceUpdates {

  public static final int MIN_TERMINAL_WIDTH = 94;

  public static boolean isUnixTerminal() {

    String os = System.getProperty("os.name");
    if (os.startsWith("Windows")) {
      // we do not support Windows, we will revisit this if we really need it for windows.
      return false;
    }

    // invoke jansi.internal.CLibrary.isatty might cause jvm crash
    // https://aone.alibaba-inc.com/issue/ODPS-50635
    // so we use a safer but rouger way to judge tty
    // http://stackoverflow.com/questions/1403772/how-can-i-check-if-a-java-programs-input-output-streams-are-connected-to-a-term
    return System.console() != null;
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Erases the current line and prints the given line.
   * @param line - line to print
   */
  public static void reprintLine(PrintStream out, String line) {
    out.print(ansi().eraseLine(Ansi.Erase.ALL).a(line).a('\n').toString());
    out.flush();
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Erases the current line and prints the given multiline. Make sure the specified line is not
   * terminated by linebreak.
   * @param line - line to print
   */
  public static int reprintMultiLine(PrintStream out, String line) {
    String [] lines = line.split("\r\n|\r|\n");
    int numLines = lines.length;
    for (String str : lines) {
      out.print(ansi().eraseLine(Ansi.Erase.ALL).a(str).a('\n').toString());
    }

    out.flush();
    return numLines;
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Erases the current line and prints the given line with the specified color.
   * @param line - line to print
   * @param color - color for the line
   */
  public static void reprintLineWithColorAsBold(PrintStream out, String line, Ansi.Color color) {
    out.print(ansi().eraseLine(Ansi.Erase.ALL).fg(color).bold().a(line).a('\n').boldOff().reset()
                  .toString());
    out.flush();
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Repositions the cursor back to line 0.
   */
  public static void rePositionCursor(PrintStream out, int lines) {
    out.print(ansi().cursorUp(lines).toString());
    out.flush();
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Repositions the cursor to top left, and clear screen
   */
  public static void resetScreen(PrintStream out) {
    out.print(ansi().cursor(1, 1).toString());
    out.print(ansi().eraseScreen(Ansi.Erase.FORWARD).toString());
    out.flush();
  }

  public static void resetForward(PrintStream out) {
    out.print(ansi().eraseScreen(Ansi.Erase.FORWARD).toString());
    out.flush();
  }

  /**
   * NOTE: Use this method only if isUnixTerminal is true.
   * Clear the screen, and the repositions the cursor to top left
   */
  public static void clearScreen(PrintStream out) {
    out.print(ansi().eraseScreen(Ansi.Erase.ALL).toString());
    out.print(ansi().cursor(1, 1).toString());
    out.flush();
  }
}
