package com.galaxia.engdev.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class StringUtil {

	public static final String SPACE = "";

	protected static final byte NULL_BYTE = 0x00;
	protected static final byte SPACE_BYTE = 0x20;

	protected static final int ONE_BYTE_MIN = 0x0000;
	protected static final int ONE_BYTE_MAX = 0x007F;

	protected static final int TWO_BYTE_MIN = 0x0800;
	protected static final int TWO_BYTE_MAX = 0x07FF;

	protected static final int THREE_BYTE_MIN = 0x0800;
	protected static final int THREE_BYTE_MAX = 0xFFFF;

	protected static final int SURROGATE_MIN = 0x10000;
	protected static final int SURROGATE_MAX = 0x10FFFF;

	/**
	 * IPv4 RegEx
	 */
	public static final String ipAddressV4Regex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

	/** 주민번호 만들기 */
	public static String makeJuminNO(String str) {

		String temp = null;
		int len = (str.trim()).length();

		if (len != 13)
			return str;
		temp = str.substring(0, 6) + "-" + str.substring(6, 13);

		return temp;
	}

	/** number formating, 숫자에 ','를 첨가한다. */
	public static String addComma(String str) {

		String temp = null;

		if (str == null)
			temp = "0";
		else {
			double change = Double.valueOf(str.trim()).doubleValue();
			DecimalFormat decimal = new DecimalFormat("###,###,###,###");
			temp = decimal.format(change);
		}

		return temp;
	}

	/** 환율 number formating, 숫자에 ','를 첨가한다. */
	public static String eRateFormat(String str) {

		String temp = null;

		if (str == null)
			temp = "0";
		else {
			double change = Double.valueOf(str.trim()).doubleValue();
			DecimalFormat decimal = new DecimalFormat("###,###,###.##");
			temp = decimal.format(change);
		}

		return temp;
	}

	/** ','를 삭제한다. */
	public static String delComma(String str) {

		if (str == null)
			return "";

		StringBuffer dest = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			switch (c) {
			case ',':
				break;
			default:
				dest.append((char) c);
				break;
			}
		}
		return dest.toString();
	}

	/** '-'를 삭제한다. */
	public static String delDash(String s) {
		if (s == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '-')
				continue;
			sb.append(c);
		}
		return sb.toString();
	}

	/** 문자열에서 '.'를 삭제한다. (예 : yyyy.mm.dd 에서 ) */
	public static String delDot(String str) {

		if (str == null)
			return "";

		StringBuffer dest = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			switch (c) {
			case '.':
				break;
			default:
				dest.append((char) c);
				break;
			}
		}
		return dest.toString();
	}

	public static String makerLoanRate(String str, int fLen, int tLen) {
		int sIdx = str.indexOf(".");
		String preRate = "";
		String backRate = "";
		String loanRate = "";

		if (sIdx != -1) {
			preRate = str.substring(0, sIdx);
			backRate = str.substring(sIdx + 1, str.length());
		} else {
			preRate = str;
		}
		preRate = StringUtil.fixlength(1, fLen, preRate, '0');
		backRate = StringUtil.fixlength(0, tLen, backRate, '0');
		loanRate = preRate + backRate;

		return loanRate;
	}

	/** 년월일 사이에 '.'를 첨가한다. */
	public static String date(String str) {

		String temp = null;
		if (str == null || (str.trim()).length() == 0)
			return "";
		int len = str.length();

		if (len != 8)
			return str;
		if ((str.equals("00000000")) || (str.equals("	   0")))
			return "";
		temp = str.substring(0, 4) + "." + str.substring(4, 6) + "." + str.substring(6, 8);

		return temp;
	}

	/** 년월일 사이에 '-'를 첨가한다. */
	public static String dateDash(String str) {
		if (str == null || (str.trim()).length() == 0)
			return "";
		String temp = null;
		int len = str.length();

		if (len != 8)
			return str;
		if ((str.equals("00000000")) || (str.equals("	   0")))
			return "";
		temp = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);

		return temp;
	}

	/** 년월일 한글로 표시한다 */
	/* by hjun 2000.12.06 */
	public static String dateHan(String str) {

		String temp = null;
		int len = str.length();

		if (len != 8)
			return str;
		if ((str.equals("00000000")) || (str.equals("	   0")))
			return "";
		temp = str.substring(0, 4) + "년 " + Integer.parseInt(str.substring(4, 6)) + "월 " + Integer.parseInt(str.substring(6, 8)) + "일";

		return temp;
	}

	/** 년월 한글로 표시한다 */
	public static String dateHanYM(String str) {

		String temp = null;
		int len = str.length();

		if (len != 6)
			return str;
		if ((str.equals("000000")) || (str.equals("	 0")))
			return "";
		temp = str.substring(0, 4) + "년 " + Integer.parseInt(str.substring(4, 6)) + "월";

		return temp;
	}

	public static String doubleTrim(String str) {
		if (str == null)
			return "";
		return doubleTrim(str.trim().getBytes());
	}

	public static String doubleTrim(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return "";

		int s, e;

		for (s = 0; s < bytes.length; s++) {
			if (bytes[s] == (byte) (-95) && s + 1 < bytes.length && bytes[s + 1] == (byte) (-95))
				s++;
			else if (bytes[s] == (byte) (32))
				continue;
			else
				break;
		}

		for (e = bytes.length - 1; e >= 0; e--) {
			if (bytes[e] == (byte) (-95) && e > 0 && bytes[e - 1] == (byte) (-95))
				e--;
			else if (bytes[e] == (byte) (32))
				continue;
			else
				break;
		}

		if (e < s)
			return "";
		byte[] newBytes = new byte[e - s + 1];
		System.arraycopy(bytes, s, newBytes, 0, e - s + 1);

		return new String(newBytes);
	}

	/** 가변적인 String을 고정된 length의 byte[]로 변환한다. */
	/** 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다. */
	/** 구분 '0':left align '1':right align */
	public static byte[] fixlengthbyte(int kind, int out_len, String str, char fillCh) {
		byte[] temp = new byte[out_len];
		int i;
		for (i = 0; i < out_len; i++) {
			// temp[i] = Byte.parseByte(fillCh) ;
			temp[i] = (byte) fillCh;
		} // out_len 만큼 temp에 space를 채운다.

		if (!(str == null || str.trim().equals("") || str.equals(null))) {
			byte[] input = str.getBytes();
			int j;
			int in_len = input.length;

			// 입력된 길이보다 해당 String이 긴 경우
			if (in_len > out_len) {
				int notHanPos = out_len - 1;
				while ((input[notHanPos]) > 128 || (input[notHanPos]) < 0) {
					notHanPos--;
					if (notHanPos < 0)
						break;
				}
				if (notHanPos < 0 || notHanPos % 2 == 1)
					in_len = out_len;
				else
					in_len = out_len - 1;
			}

			if (kind == 1)
				for (i = (out_len - in_len), j = 0; i < out_len; i++, j++) {
					temp[i] = input[j];
				}
			else
				for (i = 0; i < in_len; i++) {
					temp[i] = input[i];
				}
		}

		return temp;
	}

	/** 가변적인 String을 고정된 length의 String으로 변환한다. */
	/** 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다. */
	/** 구분 '0':left align '1':right align */
	public static String fixlength(int kind, int out_len, String str, char fillCh) {
		return new String(fixlengthbyte(kind, out_len, str, fillCh), 0, out_len);
	}

	/**
	 * 가변적인 String을 고정된 length의 String으로 변환한다. 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다. 구분 '0':left align '1':right align
	 */
	public static String fixlength(int kind, int out_len, String str) {
		return fixlength(kind, out_len, str, ' ');
	}

	/**
	 * 가변적인 String을 고정된 length의 String으로 변환한다. 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다. 구분 '0':left align
	 */
	public static String fixlength(int out_len, String str) {
		return fixlength(0, out_len, str, ' ');
	}

	public static byte[] stringToFillNullByte(String s, int length) {
		return stringToFillBytes(s, length, NULL_BYTE);
	}

	public static byte[] stringToFillSpaceByte(String s, int length) {
		return stringToFillBytes(s, length, SPACE_BYTE);
	}

	public static byte[] stringToFillBytes(String s, int length, byte fillByte) {
		byte[] dest = new byte[length];

		Arrays.fill(dest, fillByte);

		if (s == null)
			s = "";

		byte[] src = s.getBytes();
		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, 0, src.length);

		return dest;
	}

	public static byte[] stringToBytes(String s, int length) {
		byte[] dest = new byte[length];

		if (s == null)
			s = "";

		byte[] src = s.getBytes();
		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, 0, src.length);

		return dest;
	}

	/**
	 * 지정 byte수만큼 유니코드 문자 cut
	 *
	 * @param str         문자열
	 * @param maxByteSize 최대 바이트
	 * @param trail       문자열 자르고 뒤에 붙이 trail 문자열
	 * @param charsetName 케릭터 set Name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String cutOffString(String str, int maxByteSize, String trail, String charsetName) throws UnsupportedEncodingException {
		// 널일 경우에는 그냥 리턴
		if (str == null)
			return null;

		if (str.length() == 0)
			return str;

		byte strByte[] = str.getBytes(charsetName);

		if (strByte.length <= maxByteSize)
			return str;

		// 마지막 줄임말
		int trailByteSize = 0;

		// 줄임말의 바이트 수 계산
		if (trail != null)
			trailByteSize = trail.getBytes(charsetName).length;

		// 실질적으로 포함되는 최대 바이트 수는 trailByte를 뺀 것이다.
		maxByteSize = maxByteSize - trailByteSize;

		// 마지막 바이트 위치
		int endPos = 0;
		// 현재까지 조사한 바이트 수
		int currByte = 0;

		for (int i = 0; i < str.length(); i++) {
			// 순차적으로 문자들을 가져옴.
			char ch = str.charAt(i);

			// 이 문자가 몇 바이트로 구성된 UTF-8 코드인지를 검사하여 currByte에 누적 시킨다.
			currByte = currByte + availibleByteNum(ch);

			// 현재까지 조사된 바이트가 maxSize를 넘는다면 이전 단계 까지 누적된 바이트 까지를 유효한 바이트로 간주한다.
			if (currByte > maxByteSize) {
				endPos = currByte - availibleByteNum(ch);
				break;
			}
		}

		// 원래 문자열을 바이트로 가져와서 유효한 바이트 까지 배열 복사를 한다.
		byte newStrByte[] = new byte[endPos];

		System.arraycopy(strByte, 0, newStrByte, 0, endPos);

		String newStr = new String(newStrByte, charsetName);
		// System.out.println(newStr.getBytes(charsetName).length + " " + newStr);
		newStr += trail;

		return newStr;
	}

	public static int availibleByteNum(char c) {
		int digit = c;

		if (ONE_BYTE_MIN <= digit && digit <= ONE_BYTE_MAX)
			return 1;
		else if (TWO_BYTE_MIN <= digit && digit <= TWO_BYTE_MAX)
			return 2;
		else if (THREE_BYTE_MIN <= digit && digit <= THREE_BYTE_MAX)
			return 3;
		else if (SURROGATE_MIN <= digit && digit <= SURROGATE_MAX)
			return 4;

		return -1;
	}

	/**
	 * 가변적인 String을 고정된 length의 String으로 변환한다. 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다. 구분 '0':left align
	 */
	public static String fixlength(int out_len, String str, char fillCh) {
		return fixlength(0, out_len, str, fillCh);
	}

	/**
	 * Long.parseLong 을 실행
	 *
	 * @param def parsing 이 실패할 때 넘어올 값
	 * @param str parsing 할 문자열
	 * @return parsing 된 결과값
	 */
	public static long validLong(long def, String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * Integer.parseInt 를 실행
	 *
	 * @param def parsing 이 실패할 때 넘어올 값
	 * @param str parsing 할 문자열
	 * @return parsing 된 결과값
	 */
	public static int validInt(int def, String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * Double.parseDouble 를 실행
	 *
	 * @param def parsing이 실패할 때 넘어올 값
	 * @param str parsing할 문자
	 * @return parsing된 결과값
	 */
	public static double validDouble(double def, String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 문자열이 empty 이거나 null 이면 def 로 변경함
	 *
	 * @param def
	 * @param str
	 * @return 변경된 문자열
	 */
	public static String validString(String def, String str) {
		String rValue = def;
		if (!("".equals(str) || null == str))
			rValue = str;
		return rValue;
	}

	public static String makeNumber(String str, int len) {
		str = str.trim();
		int numLen = str.length();
		String strTmp = "";
		for (int i = numLen; i < len; i++) {
			strTmp += "0";
		}
		return strTmp + str;
	}

	public static String makeNumber(int n, int len) {
		String str = String.valueOf(n);
		str = str.trim();
		int numLen = str.length();
		String strTmp = "";
		for (int i = numLen; i < len; i++) {
			strTmp += "0";
		}
		return strTmp + str;
	}

	/** 일정한 structure의 record가 여러개 반복되는 String을 tokenize */
	// for Apartment two multi parsing..
	// by Hjun 2000.11.03
	public static String[][] multi_parsing_APT(int offset, int[] delim, String str) {

		int i, j;
		byte[] input = str.getBytes();
		String temp;

		// Orient 에서 전해주는 전문중 반복횟수
		// int count = Integer.parseInt(str.substring(offset,offset +2));
		int count = Integer.parseInt((new String(input, offset, 2)).trim());
		// 일반항목수 (2), rec size (2) 만큼 건너띈다..
		offset += 4;
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				offset += delim[j];
			}
		}

		return output;
	}

	/** 일정한 structure의 record가 여러개 반복되는 String을 tokenize */
	public static String[][] multi_parsing(int offset, int[] delim, String str) {

		int i, j;
		byte[] input = str.getBytes();

		String temp;

		// 현재 host에서 내려오는 전문중 반복횟수
		int count = Integer.parseInt(str.substring(50, 53));
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				offset += delim[j];
			}
		}

		return output;
	}

	public static String[][] multi_parsing_customs(int offset, int[] delim, String str) {

		int i, j;
		byte[] input = str.getBytes();

		String temp;

		// 현재 host에서 내려오는 전문중 반복횟수
		int count = Integer.parseInt(str.substring(116, 118));
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				offset += delim[j];
			}
		}

		return output;
	}

	/** 반복회수를 받아들여 파싱함 */
	public static String[][] multi_parsing(int banbok, int offset, int[] delim, String str) {
		int i, j;
		byte[] input = str.getBytes();

		String temp;

		String[][] output = new String[banbok][delim.length];

		for (i = 0; i < banbok; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				offset += delim[j];
			}
		}

		return output;
	}

	/** 일정한 structure의 record가 여러개 반복되는 String을 tokenize */
	public static String[][] multi_parsing_long(int offset, int[] delim, String str) {

		int i, j;
		// 건수를 가져오기위한...
		int delimLen = 0;
		for (i = 0; i < delim.length; i++) {
			delimLen += delim[i];
		}

		byte[] input = str.getBytes();

		String temp;

		// 건수를 계산...
		int count = (input.length - offset) / delimLen;

		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				offset += delim[j];
			}
		}

		return output;
	}

	/** 하나의 긴 String을 주어진 integer array의 순서대로 tokenize */
	public static String[] parsing(int[] delim, String str) {

		int i, offset = 0;

		// 한글문제로 인하여 byte로 변환한 후 처리
		byte[] input = str.getBytes();
		String[] output = new String[delim.length];
		String temp;

		for (i = 0; i < delim.length; i++) {
			temp = new String(input, offset, delim[i]);
			output[i] = temp;
			offset += delim[i];
		}
		return output;
	}

	/**
	 * delimiter로 분리된 String을 분석하여 String의 array로 만들어 return한다. data에 몇개의 element가 있는지 모를때 사용한다.
	 */
	public static String[] parse(String data, String delimiter) throws Exception {
		int index = 0;
		int i;
		int numberOfElement;
		String message[];
		String s;

		s = data;

		for (i = 0;; i++) {
			index = s.indexOf(delimiter);
			if (index == -1)
				break;

			s = s.substring(index + 1);
		}

		numberOfElement = i + 1;

		message = new String[numberOfElement];
		s = data;
		for (i = 0;; i++) {
			index = s.indexOf(delimiter);
			if (index == -1)
				break;

			message[i] = s.substring(0, index).trim();
			s = s.substring(index + 1);
		}

		message[i] = s.trim();

		return message;
	}

	/** number formating, 숫자에 ','를 첨가한다. */
	// 실제 double 을 문자열로 바꾼다..
	// by Hjun 2000.10.18
	public static String numberDouble(double d) {

		String temp = null;

		if (d == 0)
			temp = "0";
		else {
			DecimalFormat decimal = new DecimalFormat("###,###,###.###");
			temp = decimal.format(d);
		}

		return temp;
	}

	/** number formating, 숫자를 자리수에 강제로 맞춤 */
	public static String formatFixNumber(int number, int length) {
		String preStr = (number < 0) ? "-" : "";
		String posStr = String.valueOf(Math.abs(number));

		int zeroFillCount = length - preStr.length() - posStr.length();
		for (int count = 0; count < zeroFillCount; count++)
			preStr += "0";

		return preStr + posStr;
	}

	/** number formating, 숫자에 ','를 첨가한다. */
	public static String number(String str) {

		String temp = null;

		if (str == null || (str.trim()).length() == 0)
			temp = "0";
		else {
			double change = Double.valueOf(StringUtil.delComma(str.trim())).doubleValue();
			DecimalFormat decimal = new DecimalFormat("###,###,###.###");
			temp = decimal.format(change);
		}

		return temp;
	}

	/** 주민등록번호 또는 사업자번호에 '-'를 첨가한다. */
	public static String regnNo(String str) {

		String temp = null;
		int len = str.length();

		if ((len != 13) && (len != 10))
			return str;

		// 사업자번호
		if (len == 10)
			temp = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5, 10);

		// 주민등록번호
		if (len == 13)
			temp = str.substring(0, 6) + "-" + str.substring(6, 13);

		return temp;
	}

	/**
	 * 입력 string을 offset에서 부터 n byte 추출. String method인 substring을 쓸 경우 한글을 한 character로 인식하므로 byte로 변환한 다음 처리한다
	 */
	public static String substr(String str, int offset, int len) {
		String output = "";

		try {
			byte[] input = str.getBytes();
			if (offset >= input.length)
				return output;
			if (offset + len > input.length)
				len = input.length - offset;
			output = new String(input, offset, len);
		} catch (Exception e) {
		}

		return output;
	}

	/** 시분초 사이에 ':'를 첨가한다. */
	public static String time(String str) {

		String temp = null;
		// Hjun edit.. 2000.11.1
		if (str == null || (str.trim()).length() == 0)
			return "";
		int len = str.length();

		if (len != 6)
			return str;

		temp = str.substring(0, 2) + ":" + str.substring(2, 4) + ":" + str.substring(4, 6);

		return temp;
	}

	/** 시분 한글로 표시한다 */
	/* by hjun 2000.12.06 */
	public static String timeHanHM(String str) {
		if (str == null || (str.trim()).length() == 0)
			return "";
		String temp = null;
		int len = str.length();

		if (len > 6)
			return str;

		temp = Integer.parseInt(str.substring(0, 2)) + "시 " + Integer.parseInt(str.substring(2, 4)) + "분";

		return temp;
	}

	public static String encryptUrl(String url) {

		if (url == null)
			return "";

		String out = "";
		char ch;

		for (int i = 0; i < url.length(); i++) {
			ch = url.charAt(i);
			if (ch == ' ')
				out += "%20";
			else if (ch == '%')
				out += "%25";
			else if (ch == '&')
				out += "%26";
			else if (ch == '+')
				out += "%2B";
			else if (ch == '=')
				out += "%3D";
			else if (ch == '?')
				out += "%3F";
			else
				out += ch;
		}
		return out;
	}

	// 사용자 정보에서 조회에서 중간에 간격을 공백한칸으로 유지
	public static String trimMid(String txt) {
		int p = 0;
		for (;;) {
			p = txt.indexOf("  ");
			if (p < 1)
				return txt.trim();
			txt = txt.substring(0, p) + txt.substring(p + 1);
		}
	}

	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	/**
	 * 배열 속에 해당 문자열이 있는지 확인
	 *
	 * @param strArray 문자열
	 * @param strValue 찾는 값
	 * @return true : 있음. false : 없음
	 */
	public static boolean isInArray(String[] strArray, String strValue) {
		return -1 != getIndexInArray(strArray, strValue);
	}

	/**
	 * 배열 속에 해당 문자열이 있는 위치를 찾음
	 *
	 * @param strArray 문자열
	 * @param strValue 찾는 값
	 * @return -1 : 찾을 수 없음. 그 외 : 배열 속 위치
	 */
	public static int getIndexInArray(String[] strArray, String strValue) {
		for (int index = 0; index < strArray.length; index++)
			if (strArray[index].equals(strValue))
				return index;

		return -1;
	}

	/**
	 * 원문 msg 에서 지정된 tag 에 싸여진 값을 지정한 값으로 변경한다.<br>
	 * ex) replace("&lt;A&gt;a&lt;/A&gt;&quot;,&quot;A&quot;,'*'); -&gt; &quot;&lt;A&gt;*&lt;/A&gt;"
	 *
	 * @param raw     원문 msg
	 * @param tag     변경하고자하는 tag
	 * @param replace 변경되는 문자
	 */
	public static String replaceTag(String raw, String tag, char replace) {
		String warnText = raw;
		String startTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";
		int keywordStart = warnText.indexOf(startTag);
		int keywordEnd = warnText.indexOf(endTag);

		if (keywordStart != keywordEnd && keywordStart >= 0 && keywordEnd > 0) {
			StringBuffer sb_warn = new StringBuffer(1024);
			sb_warn.append(warnText.substring(0, keywordStart + String.valueOf(startTag).length()));
			for (int i = 0, n = keywordEnd - keywordStart - String.valueOf(startTag).length(); i < n; i++) {
				sb_warn.append(replace);
			}
			sb_warn.append(warnText.substring(keywordEnd));
			warnText = sb_warn.toString();
		}
		return warnText;
	}

	/**
	 * Setter 이름 가져옴
	 *
	 * @param name
	 * @return
	 * @author mjhan
	 */
	public static String getSetterName(String name) {
		name = name.trim();
		return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * getter 이름 가져옴
	 *
	 * @param name
	 * @return
	 * @author mjhan
	 */
	public static String getGetterName(String name) {
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static String toUpperSnake(String str) {
		return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
	}

	public static String toCamelCase(String str) {
		String[] parts = str.split("_");
		StringBuffer sb = new StringBuffer();

		for (String part : parts) {
			sb.append(toProperCase(part));
		}

		return sb.toString();
	}

	public static String toProperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	/**
	 * IPv4 주소 포멧 확인
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isIpAddressV4(String ip) {
		return ip.matches(ipAddressV4Regex);
	}

	/**
	 * length 길이만큼 9로 채워진 문자열 반환
	 *
	 * @param length
	 * @return
	 */
	public static String make9String(int length) {
		char[] temp = new char[length];

		Arrays.fill(temp, '9');

		return new String(temp);
	}

	/**
	 * 문자열내 특수문자 제거
	 *
	 * @param str
	 * @return
	 */
	public static String replaceSpChar(String str) {
		String match = "[^\uAc00-\uD7A3xfe0-9a-zA-Z\\s]";

		if (str != null) {
			return str.replaceAll(match, " ");
		} else {
			return str;
		}
	}

	/**
	 * 문자열 Null 체크 & 공백체크
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty());
	}

	/**
	 * Object to String<br>
	 * obj == null return null;
	 *
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return String.valueOf(obj);
		}

	}

}
