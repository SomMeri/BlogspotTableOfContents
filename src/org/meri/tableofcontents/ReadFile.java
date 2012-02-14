package org.meri.tableofcontents;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {

  private final String file;
  private BufferedReader br;

  public ReadFile(String file) {
    super();
    this.file = file;
  }

  public String readLine() {
    BufferedReader br = getReader();
    try {
      return br.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private BufferedReader getReader() {
    if (br != null)
      return br;

    try {
      // Open the file that is the first
      // command line parameter
      FileInputStream fstream = new FileInputStream(file);
      // Get the object of DataInputStream
      DataInputStream in = new DataInputStream(fstream);
      br = new BufferedReader(new InputStreamReader(in));

      return br;
    } catch (Exception e) {// Catch exception if any
      throw new RuntimeException(e);
    }
  }
}
