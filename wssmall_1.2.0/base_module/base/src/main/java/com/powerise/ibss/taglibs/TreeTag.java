package com.powerise.ibss.taglibs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TreeTag
    extends TagSupport {

  private String markAction;
  private String treeAction;
  private String markParam;
  private String nodeText;
  private String hiddenFields;
  private String nodeId;
  private String rootId = "0";
  private String rootText;
  private String parentNodeId;
  private ArrayList allTreeNodes;
  private ArrayList markTreeNodes;
  private static Logger logger = Logger.getLogger(TreeTag.class);
  StringBuffer results = new StringBuffer();

  class TreeNode {
    public String nodeID;
    public String nodeText;
    public String parentID;
    public String markStr;
    public String info;
  }

  private boolean hasParent(TreeNode tn) {
    boolean bFlag = false;
    for (int i = 0; i < allTreeNodes.size(); i++) {
      if ( ( (TreeNode) allTreeNodes.get(i)).nodeID.equals(tn.parentID)) {
        bFlag = true;
        break;
      }
    }
    return bFlag;
  }

  private TreeNode getParent(TreeNode tn) {
    TreeNode objNodeParent = null;
    for (int i = 0; i < allTreeNodes.size(); i++) {
      if ( ( (TreeNode) allTreeNodes.get(i)).nodeID.equals(tn.parentID)) {
        objNodeParent = (TreeNode) allTreeNodes.get(i);
        break;
      }
    }
    return objNodeParent;
  }

  private void markNode(TreeNode tn) {
    tn.markStr = "Right";
    while (hasParent(tn)) {
      if (!getParent(tn).markStr.equals("Right"))
        getParent(tn).markStr = "Star";
      tn = getParent(tn);
    }
    return;
  }

  private void markTree() {
    for (int i = 0; i < markTreeNodes.size(); i++) {
      for (int j = 0; j < allTreeNodes.size(); j++) {
        if ( ( (TreeNode) allTreeNodes.get(j)).nodeID.equals( ( (TreeNode)
            markTreeNodes.get(i)).nodeID)) {
          markNode( ( (TreeNode) allTreeNodes.get(j)));
          break;
        }
      }
    }
  }

  private ArrayList getChilds(TreeNode tn) {
    ArrayList arrChildTree = new ArrayList();
    for (int i = 0; i < allTreeNodes.size(); i++) {
      if ( ( (TreeNode) allTreeNodes.get(i)).parentID.equals(tn.nodeID)) {
        arrChildTree.add(allTreeNodes.get(i));
      }
    }
    return arrChildTree;
  }

  private boolean hasChild(TreeNode tn) {
    boolean bFlag = false;
    for (int i = 0; i < allTreeNodes.size(); i++) {
      if ( ( (TreeNode) allTreeNodes.get(i)).parentID.equals(tn.nodeID)) {
        bFlag = true;
        break;
      }
    }
    return bFlag;
  }

  private ArrayList getRoots() {
    ArrayList arrRootTree = new ArrayList();
    for (int i = 0; i < allTreeNodes.size(); i++) {
      if ( ( (TreeNode) allTreeNodes.get(i)).parentID.equals(rootId)) {
        arrRootTree.add(allTreeNodes.get(i));
      }
    }
    return arrRootTree;
  }

  private void buildTree(ArrayList al, String tnstr) {
    for (int i = 0; i < al.size(); i++) {
      String dgstr = tnstr + Integer.toString(i);
      results.append("var " + dgstr + " = new WebFXTreeItem('" +
                     ( (TreeNode) al.get(i)).nodeText + "','" +
                     ( (TreeNode) al.get(i)).info + "');\n");

      if ( ( (TreeNode) al.get(i)).markStr.equals("Right")) {
        if (hasChild( ( (TreeNode) al.get(i)))) {
          results.append(dgstr + ".icon = webFXTreeConfig.folderIconRight;\n");
          results.append(dgstr +
                         ".openIcon = webFXTreeConfig.openFolderIconRight;\n");
        }
        else {
          results.append(dgstr + ".icon = webFXTreeConfig.fileIconRight;\n");
        }
      }
      if ( ( (TreeNode) al.get(i)).markStr.equals("Star")) {
        results.append(dgstr + ".icon = webFXTreeConfig.folderIconStar;\n");
        results.append(dgstr +
                       ".openIcon = webFXTreeConfig.openFolderIconStar;\n");
      }

      results.append(tnstr + ".add(" + dgstr + ");\n");
      if (hasChild( (TreeNode) al.get(i))) {
        ArrayList arrChildTree = getChilds( (TreeNode) al.get(i));
        buildTree(arrChildTree, dgstr);
      }
    }
  }

  private ArrayList execAction(String action, String param) {

    ArrayList retAL = new ArrayList();

    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
        HttpServletRequest) pageContext.getRequest();

    DynamicDict dict = new DynamicDict(1);
    try {
      HashMap para = new HashMap();
      if (null != param) {
        StringTokenizer st = new StringTokenizer(param, ",");
        while (st.hasMoreTokens()) {
          String sName = st.nextToken();
          String sValue = st.nextToken();
          String sCond = request.getParameter(sValue);
          if (sCond != null && !sCond.equals(""))
            para.put(sName, sCond);
        }
      }

      dict.m_ActionId = action;
      dict.flag = 1;
      dict.setValueByName("parameter", para);
      dict = ActionDispatch.dispatch(dict);
      if (dict.flag < 0) {
        //异常信息
        logger.info(dict.flag + "    错误信息：" + dict.msg + "异常信息：" +
                           dict.exception);
        try {
          pageContext.getOut().print("错误信息：" + dict.msg + "异常信息：" + dict.exception);
        }
        catch (IOException ioe) {
          logger.info("Error in IterateResultSetTag : " + ioe);
        }
      }

      Object obj = dict.getValueByName(action, false);

      if (obj.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) {
        ArrayList al = (ArrayList) obj;
        HashMap hm = new HashMap();
        for (int i = 0; i < al.size(); i++) {
          hm = (HashMap) al.get(i);
          TreeNode tn = new TreeNode();
          tn.nodeID = (String) hm.get(nodeId);
          tn.nodeText = (String) hm.get(nodeText);
          tn.parentID = (String) hm.get(parentNodeId);
          tn.markStr = "";
          tn.info = "";

          if (null!=hiddenFields) {
          StringTokenizer st = new StringTokenizer(hiddenFields, ",");
          while (st.hasMoreTokens()) {
            String column = st.nextToken();
            String str = (String) hm.get(column) == null ? "" : (String) hm.get(column);
            tn.info+=","+str;
          }
          tn.info=tn.info.substring(1);
        }

          retAL.add(tn);
        }
      }

    }
    catch (Exception e) {
    }
    finally {
      dict.destroy();
    }
    return retAL;
  }

  @Override
public int doStartTag() throws JspException {

    try {
      allTreeNodes = execAction(treeAction, null);
      if (markAction != null)
        markTreeNodes = execAction(markAction, markParam);

      results.append("<script>\n");
      results.append("var tree = new WebFXTree('"+rootText+"');\n");
      results.append("tree.setBehavior('classic');\n");
      if (markAction != null)
        markTree();
      buildTree(getRoots(), "tree");
      results.append("tree.setBehavior('classic');\n");
      results.append("document.write(tree);\n");
      results.append("</script>\n");
      pageContext.getOut().print(results.toString());
      results.setLength(0);
    }
    catch (Exception e) {
      throw new JspException("生成MarkTree时，出现异常！" + e.getMessage());
    }

    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setNodeText(String nodeText) {
    this.nodeText = nodeText;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public void setRootId(String rootId) {
    this.rootId = rootId;
  }

  public void setHiddenFields(String hiddenFields) {
    this.hiddenFields = hiddenFields;
  }

  public void setRootText(String rootText) {
    this.rootText = rootText;
  }

  public void setParentNodeId(String parentNodeId) {
    this.parentNodeId = parentNodeId;
  }

  public void setMarkAction(String markAction) {
    this.markAction = markAction;
  }

  public void setTreeAction(String treeAction) {
    this.treeAction = treeAction;
  }

  public void setMarkParam(String markParam) {
    this.markParam = markParam;
  }

}
