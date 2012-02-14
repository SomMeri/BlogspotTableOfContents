package org.meri.tableofcontents;

import java.util.ArrayList;
import java.util.List;

public class Header {

  private static final String LINK = "<a href=\"#!!!\">@@@</a>";
  private static final String ANCHOR = "<a name=\"###\"></a>";

  private final String originalHeader;
  private final String text;
  private final String name;
  private final String fullName;
  private Header parent;
  private List<Header> childs = new ArrayList<Header>();

  public Header() {
    originalHeader = null;
    text = null;
    name = null;
    fullName = null;
  }
  
  public Header(Header parent, String header) {
    this.parent = parent;
    originalHeader = header;
    text = extractText(header);
    name = generateName(text);
    parent.addChild(this);
    fullName = composefullName();
  }

  private static String generateName(String text) {
    String result = text.replaceAll(" ", "").replaceAll("/", "").replaceAll("-", "");
    return result;
  }

  private static String extractText(String text) {
    String trimmed = text;
    trimmed.trim();
    return trimmed.substring(4, trimmed.length() - 5);
  }

  public String getFullName() {
    return fullName;
  }

  public String getName() {
    return name;
  }

  public String getHeader() {
    return originalHeader;
  }

  public String getHeaderLine() {
    return ANCHOR.replace("###", fullName) + originalHeader;
  }

  public CharSequence getText() {
    return text;
  }

  public boolean is6() {
    return originalHeader.contains("<h6>");
  }

  public boolean is5() {
    return originalHeader.contains("<h5>");
  }

  public boolean is4() {
    return originalHeader.contains("<h4>");
  }

  public void addChild(Header header) {
    if (childs.isEmpty()) {
      childs.add(header);
      header.setParent(this);
      return;
    }

    Header lastChild = childs.get(childs.size() - 1);
    if (isBigger(lastChild, header)) {
      lastChild.addChild(header);
    } else {
      childs.add(header);
      header.setParent(this);
    }
  }

  private void setParent(Header parent) {
    this.parent = parent;
  }

  private boolean isBigger(Header lastChild, Header header) {
    
    if (header.is4())
      return false;

    if (lastChild.is4())
      return true;

    if (lastChild.is5())
      return header.is6();

    return false;
  }

  public String composefullName() {
    if (parent == null)
      return name==null? "" : name;

    return parent.composefullName() + name;
  }

  public String createTOC() {
    String result = "";

    result += createTOCEntry() + "\n";
    if (!childs.isEmpty()) {
      result += "<ul>\n";
      for (Header kid : childs) {
        result += kid.createTOC();
      }
      result +="</ul>\n";
    }
    return result;
  }

  private String createTOCEntry() {
    if (!isRoot())
      return "<li>" + createLink() + "</li>";
    return "";
  }

  private String createLink() {
      return LINK.replace("!!!", getFullName()).replace("@@@", getText());
  }

  private boolean isRoot() {
    return originalHeader==null;
  }

  @Override
  public String toString() {
    return "Header [name=" + name + ", fullName=" + fullName + "]";
  }

  
}
