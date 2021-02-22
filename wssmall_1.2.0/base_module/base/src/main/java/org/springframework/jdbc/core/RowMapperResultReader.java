/*    */ package org.springframework.jdbc.core;
/*    */ 
/*    */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class RowMapperResultReader
/*    */   implements ResultReader
/*    */ {
/*    */   private final List results;
/*    */   private final RowMapper rowMapper;
/* 67 */   private int rowNum = 0;
/*    */ 
/*    */   public RowMapperResultReader(RowMapper paramRowMapper)
/*    */   {
/* 74 */     this(paramRowMapper, 0);
/*    */   }
/*    */ 
/*    */   public RowMapperResultReader(RowMapper paramRowMapper, int paramInt)
/*    */   {
/* 86 */     this.results = (paramInt > 0 ? new ArrayList(paramInt) : new LinkedList());
/* 87 */     this.rowMapper = paramRowMapper;
/*    */   }
/*    */ 
/*    */   @Override
public void processRow(ResultSet paramResultSet) throws SQLException {
/* 91 */     this.results.add(this.rowMapper.mapRow(paramResultSet, this.rowNum++));
/*    */   }
/*    */ 
/*    */   @Override
public List getResults() {
/* 95 */     return this.results;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.jdbc.core.RowMapperResultReader
 * JD-Core Version:    0.6.0
 */