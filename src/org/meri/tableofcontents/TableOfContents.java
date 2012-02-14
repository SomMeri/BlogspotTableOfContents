package org.meri.tableofcontents;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TableOfContents {
  private static final String FILE = "src/org/meri/tableofcontents/JPA 16.txt";
  private static final String CONTENT = "src/org/meri/tableofcontents/JPA 16 Links.txt";
  private static final String TOC = "src/org/meri/tableofcontents/TOC 16.txt";
  private static String resultContent = "";
  private static Header root = new Header();

  public static void main(String[] args) {
    ReadFile file = new ReadFile(FILE);
    String line = file.readLine();
    while (line != null) {
      if (isHeader(line)) {
        handleHeader(file, line);
      } else {
        printLineAsIs(line);
      }
      line = file.readLine();
    }

    System.out.println(resultContent);
    createFile(CONTENT, resultContent);
    String toc = root.createTOC();
    System.out.println(toc);
    createFile(TOC, toc);
  }

  private static void handleHeader(ReadFile file, String line) {
    Header header = new Header(root, readHeader(file, line));

    printHeaderLine(header);
  }

  private static void printHeaderLine(Header header) {
    resultContent += header.getHeaderLine() + "\n";
  }

  private static void printLineAsIs(String line) {
    resultContent += line + "\n";
  }

  private static String readHeader(ReadFile file, String line) {
    if (hasEnd(line)) {
      return line;
    }
    String nextLine = file.readLine();
    if (!hasEnd(nextLine))
      throw new IllegalStateException("Should be the end.");

    return line + nextLine;
  }

  private static boolean hasEnd(String line) {
    return line.contains("</h4>") || line.contains("</h5>") || line.contains("</h6>");
  }

  private static boolean isHeader(String line) {
    return line.contains("<h4>") || line.contains("<h5>") || line.contains("<h6>");
  }

  private static void createFile(String name, String content) {
    File myFoo = new File(name);
    FileWriter fooWriter;
    try {
      fooWriter = new FileWriter(myFoo, false);
      fooWriter.write(content);
      fooWriter.close();
    } catch (IOException e) {
      throw new RuntimeException();
    }

  }
}
