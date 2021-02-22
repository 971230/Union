/*     */ package com.ztesoft.crm.report.rule;
/*     */ 
/*     */ import com.ztesoft.crm.report.rule.statement.Break;
/*     */ import com.ztesoft.crm.report.rule.statement.Continue;
/*     */ import com.ztesoft.crm.report.rule.statement.For;
/*     */ import com.ztesoft.crm.report.rule.statement.ForEach;
/*     */ import com.ztesoft.crm.report.rule.statement.IfElse;
/*     */ import com.ztesoft.crm.report.rule.statement.Return;
/*     */ import com.ztesoft.crm.report.rule.statement.Switch;
/*     */ import com.ztesoft.crm.report.rule.statement.While;
/*     */ 
/*     */ public class RuleParser
/*     */ {
/*     */   private static final int LEFTBRACKET = 40;
/*     */   private static final int RIGHTBRACKET = 41;
/*     */   private static final int LEFTBRACE = 123;
/*     */   private static final int RIGHTBRACE = 125;
/*     */   private static final int SINGLEQUOTE = 39;
/*     */   private static final int SEMICOLON = 59;
/*     */   private static final int SPACE = 32;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  35 */     System.out.println(';');
/*     */   }
/*     */ 
/*     */   public Token parser(String exp) {
/*  39 */     if ((exp == null) || (exp.trim().length() == 0)) {
/*  40 */       return null;
/*     */     }
/*  42 */     CharStreamReader reader = new CharStreamReader(exp);
/*  43 */     StringBuffer token = new StringBuffer();
/*  44 */     Token tk = new Token();
/*  45 */     int quote = 0;
/*     */ 
/*  47 */     while (reader.unclosed()) {
/*  48 */       char chr = reader.readChar();
/*  49 */       if (chr == '\'') {
/*  50 */         ++quote;
/*     */       }
/*     */ 
/*  53 */       if (quote % 2 == 1) {
/*  54 */         token.append(chr);
/*     */       }
/*     */       else
/*     */       {
/*  58 */         switch (chr)
/*     */         {
/*     */         case '{':
/*  60 */           break;
/*     */         case '(':
/*  62 */           break;
/*     */         case ';':
/*  64 */           break;
/*     */         case ' ':
/*  66 */           token.append(" ");
/*  67 */           break;
/*     */         default:
/*  69 */           token.append(chr);
/*  70 */           break;
/*     */         }
/*     */ 
/*  73 */         String key = token.toString().trim().toLowerCase();
/*     */ 
/*  76 */         if ("if".equals(key)) {
/*  77 */           tk.add(parseIf(reader));
/*  78 */           token = new StringBuffer();
/*     */         }
/*  82 */         else if ("for".equals(key)) {
/*  83 */           tk.add(parseFor(reader));
/*  84 */           token = new StringBuffer();
/*     */         }
/*  88 */         else if ("foreach".equals(key)) {
/*  89 */           tk.add(parseForEach(reader));
/*  90 */           token = new StringBuffer();
/*     */         }
/*  93 */         else if ("while".equals(key)) {
/*  94 */           tk.add(parseWhile(reader));
/*  95 */           token = new StringBuffer();
/*     */         }
/*  99 */         else if ("switch".equals(key)) {
/* 100 */           tk.add(parseSwitch(reader));
/* 101 */           token = new StringBuffer();
/*     */         }
/* 105 */         else if ("return".equals(key)) {
/* 106 */           tk.add(parseReturn(reader));
/* 107 */           token = new StringBuffer();
/*     */         }
/* 110 */         else if ("continue".equals(key)) {
/* 111 */           tk.add(parseContinue(reader));
/* 112 */           token = new StringBuffer();
/*     */         }
/* 116 */         else if ("break".equals(key)) {
/* 117 */           tk.add(parseBreak(reader));
/* 118 */           token = new StringBuffer();
/*     */         }
/* 122 */         else if ("var".equals(key)) {
/* 123 */           tk.add(parseVar(reader));
/* 124 */           token = new StringBuffer();
/*     */         }
/* 128 */         else if ((chr == ';') && (key.length() > 0))
/*     */         {
/* 130 */           tk.add(parseEl(token.toString().trim()));
/* 131 */           token = new StringBuffer(); } else {
/* 132 */           token.append(chr);
/*     */         }
/*     */       }
/*     */     }
/* 136 */     if (token.length() > 0) {
/* 137 */       tk.add(parseEl(token.toString().trim()));
/*     */     }
/* 139 */     reader.close();
/* 140 */     reader = null;
/* 141 */     return tk;
/*     */   }
/*     */ 
/*     */   private Token parseVar(CharStreamReader reader) {
/* 145 */     String str = readString(reader, ';');
/*     */ 
/* 147 */     int inx = str.indexOf("=");
/*     */ 
/* 149 */     Variable var = new Variable();
/* 150 */     if (inx < 0) {
/* 151 */       var.setName(str.trim());
/*     */     } else {
/* 153 */       var.setName(str.substring(0, inx).trim());
/* 154 */       var.setExpression(new ExpressionParser()
/* 155 */         .parse(str.substring(inx + 1)));
/*     */     }
/* 157 */     return var;
/*     */   }
/*     */ 
/*     */   private Token read(CharStreamReader reader, int left, int right)
/*     */   {
/* 162 */     return new RuleParser().parser(readString(reader, left, right));
/*     */   }
/*     */ 
/*     */   private String readString(CharStreamReader reader, char end) {
/* 166 */     StringBuffer token = new StringBuffer();
/* 167 */     int brace = 0;
/* 168 */     int quote = 0;
/* 169 */     while (reader.unclosed()) {
/* 170 */       char chr = reader.readChar();
/* 171 */       if (chr == '\'') {
/* 172 */         ++quote;
/*     */       }
/*     */ 
/* 175 */       if (chr == '(') {
/* 176 */         ++brace;
/*     */       }
/* 178 */       if (chr == ')') {
/* 179 */         --brace;
/*     */       }
/*     */ 
/* 182 */       if ((chr == end) && (quote % 2 == 0) && (brace == 0)) {
/* 183 */         return token.toString();
/*     */       }
/*     */ 
/* 186 */       token.append(chr);
/*     */     }
/*     */ 
/* 190 */     return token.toString();
/*     */   }
/*     */ 
/*     */   private String readString(CharStreamReader reader, int left, int right) {
/* 194 */     StringBuffer token = new StringBuffer();
/* 195 */     int brace = 0;
/* 196 */     int quote = 0;
/* 197 */     while (reader.unclosed()) {
/* 198 */       char chr = reader.readChar();
/* 199 */       if (chr == '\'') {
/* 200 */         ++quote;
/*     */       }
/*     */ 
/* 203 */       if (quote % 2 == 1) {
/* 204 */         token.append(chr);
/*     */       }
/*     */       else
/*     */       {
/* 208 */         if (chr == left) {
/* 209 */           ++brace;
/*     */         }
/* 211 */         if (chr == right) {
/* 212 */           --brace;
/* 213 */           if (brace == -1) {
/* 214 */             return token.toString();
/*     */           }
/*     */         }
/* 217 */         token.append(chr);
/*     */       }
/*     */     }
/*     */ 
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */   private String readCondition(CharStreamReader reader) {
/* 225 */     StringBuffer token = new StringBuffer();
/*     */ 
/* 227 */     while (reader.unclosed()) {
/* 228 */       char chr = reader.readChar();
/*     */ 
/* 230 */       if (chr == '{') {
/* 231 */         String exp = token.toString();
/* 232 */         int i = exp.lastIndexOf(")");
/*     */ 
/* 234 */         exp = exp.substring(0, i);
/*     */ 
/* 236 */         return exp;
/*     */       }
/* 238 */       token.append(chr);
/*     */     }
/*     */ 
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   private Token parseFor(CharStreamReader reader) {
/* 245 */     For t = new For();
/*     */ 
/* 247 */     String exp = readCondition(reader);
/*     */ 
/* 252 */     String[] args = exp.split("[;]");
/*     */ 
/* 254 */     t.setDeclare(new RuleParser().parser(args[0]));
/*     */ 
/* 256 */     t.setCondition(new RuleParser().parser(args[1]));
/* 257 */     t.setStep(new RuleParser().parser(args[2]));
/*     */ 
/* 259 */     t.setBody(read(reader, 123, 125));
/*     */ 
/* 261 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseForEach(CharStreamReader reader) {
/* 265 */     ForEach t = new ForEach();
/*     */ 
/* 267 */     String exp = readCondition(reader);
/* 268 */     String[] args = exp.split("\\sin\\s");
/*     */ 
/* 270 */     t.setDeclare(args[0].replaceFirst("var", "").trim());
/* 271 */     t.setRange(new ExpressionParser().parse(args[1]));
/* 272 */     t.setBody(read(reader, 123, 125));
/* 273 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseWhile(CharStreamReader reader) {
/* 277 */     While t = new While();
/* 278 */     t.setCondition(new RuleParser().parser(readCondition(reader)));
/*     */ 
/* 280 */     t.setBody(read(reader, 123, 125));
/*     */ 
/* 282 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseSwitch(CharStreamReader reader) {
/* 286 */     Switch t = new Switch();
/*     */ 
/* 289 */     t.setCondition(new RuleParser().parser(readCondition(reader)));
/*     */ 
/* 291 */     String exp = readString(reader, 123, 
/* 292 */       125);
/* 293 */     if (exp != null) {
/* 294 */       String[] args = exp.split("[case|default]");
/* 295 */       for (int i = 0; i < args.length; ++i) {
/* 296 */         String s = args[i].trim();
/* 297 */         if (s.length() == 0)
/*     */           continue;
/* 299 */         int inx = s.indexOf(":");
/*     */ 
/* 301 */         if (s.startsWith(":"))
/* 302 */           t.addCase("default", 
/* 303 */             new RuleParser().parser(s.substring(1)));
/*     */         else {
/* 305 */           t.addCase(s.substring(0, inx), new RuleParser()
/* 306 */             .parser(s.substring(inx)));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 312 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseReturn(CharStreamReader reader) {
/* 316 */     StringBuffer token = new StringBuffer();
/* 317 */     int quote = 0;
/* 318 */     Return t = new Return();
/* 319 */     while (reader.unclosed()) {
/* 320 */       char chr = reader.readChar();
/* 321 */       if (chr == '\'') {
/* 322 */         ++quote;
/*     */       }
/*     */ 
/* 325 */       if ((chr == ';') && (quote % 2 == 0)) {
/* 326 */         t.setValue(new ExpressionParser().parse(token.toString()));
/*     */       }
/* 328 */       token.append(chr);
/*     */     }
/*     */ 
/* 331 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseContinue(CharStreamReader reader)
/*     */   {
/* 336 */     int quote = 0;
/* 337 */     Continue t = new Continue();
/* 338 */     while (reader.unclosed()) {
/* 339 */       char chr = reader.readChar();
/* 340 */       if (chr == '\'') {
/* 341 */         ++quote;
/*     */       }
/*     */ 
/* 344 */       if ((chr == ';') && (quote % 2 == 0)) {
/* 345 */         return t;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 350 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseBreak(CharStreamReader reader)
/*     */   {
/* 355 */     int quote = 0;
/* 356 */     Break t = new Break();
/* 357 */     while (reader.unclosed()) {
/* 358 */       char chr = reader.readChar();
/* 359 */       if (chr == '\'') {
/* 360 */         ++quote;
/*     */       }
/*     */ 
/* 363 */       if ((chr == ';') && (quote % 2 == 0)) {
/* 364 */         return t;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 369 */     return t;
/*     */   }
/*     */ 
/*     */   private Token parseIf(CharStreamReader reader) {
/* 373 */     IfElse ifelse = new IfElse();
/* 374 */     StringBuffer token = new StringBuffer();
/*     */ 
/* 376 */     ifelse
/* 377 */       .setCondition(new ExpressionParser()
/* 378 */       .parse(readCondition(reader)));
/*     */ 
/* 380 */     ifelse
/* 381 */       .setThen(read(reader, 123, 
/* 382 */       125));
/*     */ 
/* 385 */     reader.mark();
/* 386 */     token = new StringBuffer();
/* 387 */     while (reader.unclosed()) {
/* 388 */       char chr = reader.readChar();
/*     */ 
/* 390 */       if (chr == '{') {
/* 391 */         if ("else".equals(token.toString().trim().toLowerCase())) {
/* 392 */           ifelse.setOther(
/* 393 */             read(reader, 123, 
/* 393 */             125));
/* 394 */           return ifelse;
/*     */         }
/*     */ 
/* 397 */         reader.backup();
/*     */ 
/* 400 */         break;
/*     */       }
/* 402 */       token.append(chr);
/*     */     }
/* 404 */     if (!(token.toString().trim().toLowerCase().startsWith("else"))) {
/* 405 */       reader.backup();
/*     */     }
/* 407 */     token = null;
/*     */ 
/* 410 */     return ifelse;
/*     */   }
/*     */ 
/*     */   private Token parseEl(String str)
/*     */   {
/* 415 */     return new ExpressionParser().parse(str);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.RuleParser
 * JD-Core Version:    0.5.3
 */