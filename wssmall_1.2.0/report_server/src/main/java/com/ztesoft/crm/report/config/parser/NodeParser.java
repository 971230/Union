package com.ztesoft.crm.report.config.parser;

import com.ztesoft.crm.report.config.elements.Element;
import org.w3c.dom.Node;

public abstract interface NodeParser
{
  public abstract Element parse(Node paramNode);
}

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.parser.NodeParser
 * JD-Core Version:    0.5.3
 */