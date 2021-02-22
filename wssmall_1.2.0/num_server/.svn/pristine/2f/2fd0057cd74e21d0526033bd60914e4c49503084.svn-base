package com.ztesoft.net.mall.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.net.mall.core.model.Numero;

public class Regx {

	public boolean regx(String numero, String regx) {
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(numero);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKKK$（K为8） >>> 加五类 <<<
	 * @return
	 */
	public boolean regx51(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(8)\\1{7}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAAAAAB$ >>> 加四类 <<<
	 * @return
	 */
	public boolean regx50(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[3] != chars[4] | chars[3] != chars[5] ) {
			return false;
		} else if(chars[3] != chars[6] | chars[3] != chars[7]){
			return false;
		}else if (chars[3] != chars[8] |chars[3] != chars[9] ) {
			return false;
		} else if (chars[3] != chars[9] |chars[0] == chars[10]) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABCDEFGH$ >>> 加四类 <<<
	 * @return
	 */ 
	public boolean regx49(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[10] - chars[9] != 1 | chars[9] - chars[8] != 1) {
			return false;
		} else if (chars[8] - chars[7] != 1 | chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1 | chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] != 1 ) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKK$（K为8） >>> 加四类 <<<
	 * @return
	 */
	public boolean regx48(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(8)\\1{5}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKKK$（K为0、3、6、9） >>> 加四类 <<<
	 * @return
	 */
	public boolean regx47(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(0|3|6|9)\\1{7}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKABCD >>> 加三类 <<<
	 * @return
	 */
	public boolean regx46(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkabcd(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkkkabcd(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[0] == chars[4] | chars[0] == chars[5]
				| chars[0] == chars[6] | chars[0] == chars[7]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] == 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXKKXXX >>> 加三类 <<<
	 * @return
	 */
	public boolean regx45(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxkkxxx(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkxkkxxx(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[3] | chars[0] != chars[4]) {
			return false;
		} else if (chars[2] == chars[3] | chars[2] != chars[5]
				| chars[2] != chars[6] | chars[2] != chars[7]) {
			return false;
		} else if (chars[0] == chars[2]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXXKKXX >>> 加三类 <<<
	 * @return
	 */
	public boolean regx44(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxxkkxx(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkxxkkxx(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[4] | chars[0] != chars[5]) {
			return false;
		} else if (chars[2] == chars[3] | chars[2] == chars[6]
				| chars[2] == chars[7]) {
			return false;
		} else if (chars[0] == chars[2]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXFKKXF >>> 加三类 <<<
	 * @return
	 */
	public boolean regx43(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxfkkxf(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkxfkkxf(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[4] | chars[0] != chars[5]) {
			return false;
		} else if (chars[0] == chars[2] | chars[0] == chars[3]) {
			return false;
		} else if (chars[2] != chars[6]) {
			return false;
		} else if (chars[3] != chars[7]) {
			return false;
		} else if (chars[2] == chars[3]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc 0A0B0C0D >>> 加三类 <<<
	 * @return
	 */
	public boolean regx42(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (oaobocod(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean oaobocod(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[2] | chars[0] != chars[4] | chars[0] != chars[6]) {
			return false;
		} else if (chars[0] != '0' | chars[0] == chars[1]) {
			return false;
		} else if (chars[7] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[3] != 1) {
			return false;
		} else if (chars[3] - chars[1] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAABCDE$ >>> 加三类 <<<
	 * @return
	 */
	public boolean regx41(Numero numero) {
		String no = numero.getDn_no();

		no = no.substring(3, no.length());
		char[] chars = no.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[7] - chars[6] != 1 | chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1 | chars[4] - chars[3] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	
	}

	/**
	 * 
	 * @param numero
	 * @desc ABCDEFG$ >>> 加三类 <<<
	 * @return
	 */
	public boolean regx40(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(4, no.length());
		char[] chars = no.toCharArray();
		if (chars[6] - chars[5] != 1 | chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] != 1 | chars[3] - chars[2] != 1) {
			return false;
		} else if (chars[2] - chars[1] != 1 | chars[1] - chars[0] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKK$（K为8）） >>> 加三类 <<<
	 * @return
	 */
	public boolean regx39(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(8)\\1{5}.*$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKXXXX$（K、X不含4、7） >>> 加三类 <<<
	 * @return
	 */
	public boolean regx38(Numero numero) {
		String no = numero.getDn_no();
		if (kkkkxxxx(no)) {		
			numero.setIs_lucky("1");
			return true;
		}
		return false;
	}

	private boolean kkkkxxxx(String numero) {
		numero = numero.substring(3, numero.length());
		int i = numero.indexOf("4");
		int i2 = numero.indexOf("7");
		if (i != -1 | i2 != -1) {
			return false;
		}
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[4] != chars[5] | chars[4] != chars[6]
				| chars[4] != chars[7]) {
			return false;
		} else if (chars[0] == chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKKK$（K为1、2、4、5、7） >>> 加三类 <<<
	 * @return
	 */
	public boolean regx37(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(1|2|4|5|7)\\1{7}.*$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKKX >>> 加二类 <<<
	 * @return
	 */
	public boolean regx36(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkkkkx(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkkkkkkx(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[4]) {
			return false;
		} else if (chars[0] != chars[5] | chars[0] != chars[6]) {
			return false;
		} else if (chars[0] == chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AA0K0K0K >>> 加二类 <<<
	 * @return
	 */
	public boolean regx35(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aa0k0k0k(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aa0k0k0k(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1]) {
			return false;
		} else if (chars[3] != chars[5] | chars[3] != chars[7]) {
			return false;
		} else if (chars[2] != chars[4] | chars[2] != chars[6]) {
			return false;
		} else if (chars[2] != '0') {
			return false;
		} else if (chars[0] == chars[2] | chars[0] == chars[7]
				| chars[7] == chars[6]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAABABAB >>> 加二类 <<<
	 * @return
	 */
	public boolean regx34(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aaababab(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aaababab(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[4] | chars[0] != chars[6]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AABBCCDD >>> 加二类 <<<
	 * @return
	 */
	public boolean regx33(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aabbccdd(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aabbccdd(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[2] != chars[3]) {
			return false;
		} else if (chars[4] != chars[5]) {
			return false;
		} else if (chars[6] != chars[7]) {
			return false;
		} else if (chars[7] - chars[6] != 1 | chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1 | chars[2] - chars[1] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAABCBC >>> 加二类 <<<
	 * @return
	 */
	public boolean regx32(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aaaabcbc(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aaaabcbc(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[4] != chars[6]) {
			return false;
		} else if (chars[5] != chars[7]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAABBCC >>> 加二类 <<<
	 * @return
	 */
	public boolean regx31(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aaaabbcc(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aaaabbcc(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[5] != chars[4]) {
			return false;
		} else if (chars[7] != chars[6]) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAABCCC >>> 加二类 <<<
	 * @return
	 */
	public boolean regx30(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (aaaabccc(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean aaaabccc(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[5] != chars[6] | chars[5] != chars[7]) {
			return false;
		} else if (chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABABABAB >>> 加二类 <<<
	 * @return
	 */
	public boolean regx29(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (abababab(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean abababab(String subNumero) {
		char[] chars = subNumero.toCharArray();
		if (chars[0] != chars[2] | chars[0] != chars[4] | chars[0] != chars[6]) {
			return false;
		} else if (chars[1] != chars[3] | chars[1] != chars[5]
				| chars[1] != chars[7]) {
			return false;
		} else if (chars[1] - chars[0] != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAACDEF$ >>> 加二类 <<<
	 * @return
	 */
	public boolean regx28(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAAABCD$ >>> 加二类 <<<
	 * @return
	 */
	public boolean regx27(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[4]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		} else if (chars[5] - chars[4] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKXXX$（K、X不含4、7）>>> 加二类 <<<
	 * @return
	 */
	public boolean regx26(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(3, no.length());
		int i = no.indexOf("4");
		int i2 = no.indexOf("7");
		if (i != -1 | i2 != -1) {
			return false;
		}
		char[] chars = no.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[4]) {
			return false;
		} else if (chars[0] == chars[7]) {
			return false;
		} else if (chars[5] != chars[6] | chars[5] != chars[7]) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKK$（K不为8） >>> 加二类 <<<
	 * @return
	 */
	public boolean regx25(Numero numero) {
		String no = numero.getDn_no();
		if (regx(no, "^.*(1|2|3|4|5|6|7|9|0)\\1{6}$")) {		
			numero.setIs_lucky("1");
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKXXFF >>> 加一类 <<<
	 * @return
	 */
	public boolean regx24(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkxxff(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkkkxxff(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[0] == chars[4]) {
			return false;
		} else if (chars[0] == chars[6]) {
			return false;
		} else if (chars[4] == chars[6]) {
			return false;
		} else if (chars[4] != chars[5]) {
			return false;
		} else if (chars[6] != chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKXFYK（码内不含4、7） >>> 加一类 <<<
	 * @return
	 */
	public boolean regx23(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkxfyk(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkkkxfyk(String numero) {
		int i = numero.indexOf("4");
		int i2 = numero.indexOf("7");
		if (i != -1 | i2 != -1) {
			return false;
		}
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[7]) {
			return false;
		} else if (chars[0] == chars[4]) {
			return false;
		} else if (chars[0] == chars[5]) {
			return false;
		} else if (chars[3] != chars[7]) {
			return false;
		} else if (chars[4] == chars[5] | chars[4] == chars[6]) {
			return false;
		} else if (chars[5] == chars[6]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKXXYK（码内不含4、7） >>> 加一类 <<<
	 * @return
	 */
	public boolean regx22(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkxxyk(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkkkxxyk(String numero) {
		int i = numero.indexOf("4");
		int i2 = numero.indexOf("7");
		if (i != -1|i2 != -1) {
			return false;
		}
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[7]) {
			return false;
		} else if (chars[0] == chars[6] | chars[0] == chars[4]) {
			return false;
		} else if (chars[5] == chars[6]) {
			return false;
		} else if (chars[4] != chars[5]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXKYYYK（码内不含4、7） >>> 加一类 <<<
	 * @return
	 */
	public boolean regx21(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxkyyyk(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkxkyyyk(String numero) {
		int i = numero.indexOf("4");
		int i2 = numero.indexOf("7");
		if (i != -1 | i2 != -1) {
			return false;
		}
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[3] | chars[0] != chars[7]) {
			return false;
		} else if (chars[0] == chars[2]) {
			return false;
		} else if (chars[0] == chars[4]) {
			return false;
		} else if (chars[2] == chars[4]) {
			return false;
		} else if (chars[4] != chars[5] | chars[4] != chars[6]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KK0A0B0C >>> 加一类 <<<
	 * @return
	 */
	public boolean regx20(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kk0a0b0c(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kk0a0b0c(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] == '0') {
			return false;
		} else if (chars[2] != chars[4] | chars[2] != chars[6]) {
			return false;
		} else if (chars[2] != '0') {
			return false;
		} else if (chars[0] == chars[3] | chars[0] == chars[5]
				| chars[0] == chars[7]) {
			return false;
		} else if (chars[7] - chars[5] != 1 | chars[5] - chars[3] != 1) {
			return false;
		} 
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXKKXFF >>> 加一类 <<<
	 * @return
	 */
	public boolean regx19(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxkkxff(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkxkkxff(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[3] | chars[0] != chars[4]) {
			return false;
		} else if (chars[2] != chars[5]) {
			return false;
		} else if (chars[6] != chars[7]) {
			return false;
		} else if (chars[0] == chars[2] | chars[0] == chars[7]) {
			return false;
		} else if (chars[3] == chars[6]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXXXXXF >>> 加一类 <<<
	 * @return
	 */
	public boolean regx18(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxxxxxf(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkxxxxxf(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] == chars[7] | chars[0] == chars[6]) {
			return false;
		} else if (chars[6] == chars[7]) {
			return false;
		} else if (chars[2] != chars[3] | chars[2] != chars[4]) {
			return false;
		} else if (chars[2] != chars[5] | chars[2] != chars[6]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKXXXF >>> 加一类 <<<
	 * @return
	 */
	public boolean regx17(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkkxxxf(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkkkxxxf(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2] | chars[0] != chars[3]) {
			return false;
		} else if (chars[4] != chars[5] | chars[4] != chars[6]) {
			return false;
		} else if (chars[0] == chars[7] | chars[4] == chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXFXFXF >>> 加一类 <<<
	 * @return
	 */
	public boolean regx16(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkxfxfxf(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean kkxfxfxf(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[1] | chars[0] == chars[6] | chars[0] == chars[7]) {
			return false;
		} else if (chars[2] != chars[4] | chars[2] != chars[6]) {
			return false;
		} else if (chars[3] != chars[5] | chars[3] != chars[7]) {
			return false;
		} else if (chars[6] == chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKXKKKX >>> 加一类 <<<
	 * @return
	 */
	public boolean regx15(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (kkkxkkkx(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}
		}
		return false;
	}

	private boolean kkkxkkkx(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] == chars[3] | chars[3] != chars[7]) {
			return false;
		} else if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[4] | chars[0] != chars[5]
				| chars[0] != chars[6]) {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param numero
	 * @desc 0K0K0K0K >>> 加一类 <<<
	 * @return
	 */
	public boolean regx14(Numero numero) {
		String no = numero.getDn_no();
		for (int i = 0; i < 4; i++) {
			if (okokokok(no.substring(i, 8 + i))) {		
				numero.setIs_lucky("1");
				return true;
			}

		}
		return false;
	}

	private boolean okokokok(String numero) {
		char[] chars = numero.toCharArray();
		if (chars[0] != chars[2] | chars[0] != chars[4] | chars[0] != chars[6]) {
			return false;
		} else if (chars[0] != '0' | chars[0] == chars[1]) {
			return false;
		} else if (chars[1] != chars[3] | chars[1] != chars[5]
				| chars[1] != chars[7]) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc AAAAAABC$ >>> 加一类 <<<
	 * @return
	 */
	public boolean regx13(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(3, no.length());
		char[] chars = no.toCharArray();
		if (chars[0] != chars[1] | chars[0] != chars[2]) {
			return false;
		} else if (chars[0] != chars[3] | chars[0] != chars[4]
				| chars[0] != chars[5]) {
			return false;
		} else if (chars[7] - chars[6] != 1) {
			return false;
		} else if (chars[6] - chars[5] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABCDEF$ >>> 加一类 <<<
	 * @return
	 */
	public boolean regx12(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(5, no.length());
		char[] chars = no.toCharArray();
		if (chars[5] - chars[4] != 1) {
			return false;
		} else if (chars[4] - chars[3] != 1) {
			return false;
		} else if (chars[3] - chars[2] != 1) {
			return false;
		} else if (chars[2] - chars[1] != 1) {
			return false;
		} else if (chars[1] - chars[0] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKKXX$（K、X不含4、7） >>> 加一类 <<<
	 * @return
	 */
	public boolean regx11(Numero numero) {
		String no = numero.getDn_no();
		int i = no.indexOf("4");
		int i2 = no.indexOf("7");
		no = no.substring(3, no.length());
		char[] chars = no.toCharArray();
		if (i != -1 || i2 != -1) {
			return false;
		} else if (chars[0] != chars[1] | chars[0] != chars[2]
				| chars[0] != chars[3] | chars[0] != chars[4]
				| chars[0] != chars[5]) {
			return false;
		} else if (chars[6] != chars[7]) {
			return false;
		} else if (chars[6] == chars[5]) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKKK$（K不为8） >>> 加一类 <<<
	 * @return
	 */
	public boolean regx10(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(0|1|2|3|4|5|6|7|9)\\1{5}")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKKK$ >>> 一类 <<<
	 * @return
	 */
	public boolean regx9(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(\\d)\\1{4}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABCDE$ >>> 二类 <<<
	 * @return
	 */
	public boolean regx8(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(6, no.length());
		char[] chars = no.toCharArray();
		if (chars[4] - chars[3] != 1 | chars[4] - chars[3] != 1) {
			return false;
		} else if (chars[3] - chars[2] != 1 | chars[2] - chars[1] != 1) {
			return false;
		} else if (chars[1] - chars[0] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKKK$ >>> 二类 <<<
	 * @return
	 */
	public boolean regx7(Numero numero) {
		String no = numero.getDn_no();
		if (!regx(no, "^.*(\\d)\\1{3}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABCD$ >>> 三类 <<<
	 * @return
	 */
	public boolean regx6(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[10] - chars[9] != 1) {
			return false;
		} else if (chars[9] - chars[8] != 1) {
			return false;
		} else if (chars[8] - chars[7] != 1) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKK$ >>> 三类 <<<
	 * @return
	 */
	public boolean regx5(Numero numero) {
		String no = numero.getDn_no();

		if (!regx(no, "^.*(\\d)\\1{2}$")) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KXKX$ >>> 四类 <<<
	 * @return
	 */
	public boolean regx4(Numero numero) {
		String no = numero.getDn_no();

		no = no.substring(7, no.length());
		char[] chars = no.toCharArray();
		if (chars[0] != chars[2]) {
			return false;
		} else if (chars[1] != chars[3]) {
			return false;
		} else if (chars[0] == chars[1]) {
			return false;
		}		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KKXX$ >>> 四类 <<<
	 * @return
	 */
	public boolean regx3(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		if (chars[10] == chars[8]) {
			return false;
		} else if (!regx(no, "^.*(\\d)\\1(\\d)\\2$")) {
			return false;
		}
		
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc ABC$ >>> 五类 <<<
	 * @return
	 */
	public boolean regx2(Numero numero) {
		String no = numero.getDn_no();
		no = no.substring(8, no.length());
		char[] chars = no.toCharArray();
		if (chars[2] - chars[1] != 1 | chars[1] - chars[0] != 1) {
			return false;
		}
		numero.setIs_lucky("1");
		return true;
	}

	/**
	 * 
	 * @param numero
	 * @desc KK$ >>> 五类 <<<
	 * @return
	 */
	public boolean regx1(Numero numero) {
		String no = numero.getDn_no();
		char[] chars = no.toCharArray();
		
		if(chars[10]!=chars[9]){
			return false;
		}
		
		numero.setIs_lucky("1");
		return true;
	}

}
