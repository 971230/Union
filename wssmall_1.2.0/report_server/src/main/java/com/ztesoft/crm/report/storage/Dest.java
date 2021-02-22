/*    */ package com.ztesoft.crm.report.storage;
/*    */ 
/*    */ import com.ztesoft.crm.report.config.elements.Element;
/*    */ 
/*    */ public class Dest extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 4916233060598249990L;
/*    */   private String dataSource;
/*    */   private String className;
/*    */   private String ip;
/* 29 */   private int port = 21;
/*    */   private String userName;
/*    */   private String password;
/*    */   private String dir;
/*    */ 
/*    */   public String getDataSource()
/*    */   {
/* 35 */     return this.dataSource; }
/*    */ 
/*    */   public void setDataSource(String dataSource) {
/* 38 */     this.dataSource = dataSource; }
/*    */ 
/*    */   public String getClassName() {
/* 41 */     return this.className; }
/*    */ 
/*    */   public void setClassName(String className) {
/* 44 */     this.className = className; }
/*    */ 
/*    */   public String getIp() {
/* 47 */     return this.ip; }
/*    */ 
/*    */   public void setIp(String ip) {
/* 50 */     this.ip = ip; }
/*    */ 
/*    */   public int getPort() {
/* 53 */     return this.port; }
/*    */ 
/*    */   public void setPort(int port) {
/* 56 */     this.port = port; }
/*    */ 
/*    */   public String getUserName() {
/* 59 */     return this.userName; }
/*    */ 
/*    */   public void setUserName(String userName) {
/* 62 */     this.userName = userName; }
/*    */ 
/*    */   public String getPassword() {
/* 65 */     return this.password; }
/*    */ 
/*    */   public void setPassword(String password) {
/* 68 */     this.password = password; }
/*    */ 
/*    */   public String getDir() {
/* 71 */     return this.dir; }
/*    */ 
/*    */   public void setDir(String dir) {
/* 74 */     this.dir = dir;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.storage.Dest
 * JD-Core Version:    0.5.3
 */