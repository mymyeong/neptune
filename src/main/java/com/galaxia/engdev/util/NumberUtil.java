package com.galaxia.engdev.util;

import java.util.Random;

public class NumberUtil {
	// private static final String numberCheckRegEx = "\\d+";
	private static final String numberCheckRegEx = "(?:\\-?\\d*\\.?)?\\d+";

	/**
	 * double을 0이 앞에 붙은 String으로 변환하는 method
	 *
	 * @param value  String으로 변환하고자하는 integer
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toZeroString(double value, int length) {
		StringBuffer sb = new StringBuffer();

		int i = 0;
		if (value < 0) {
			sb.append("-");
			i++;
		}

		for (; i < length; i++)
			sb.append("0");

		String s = Double.toString(Math.abs(value));
		sb.replace(length - s.length(), length, s);
		return sb.toString();
	}

	/**
	 * long 을 0이 앞에 붙은 String으로 변환하는 method
	 *
	 * @param value  String으로 변환하고자하는 integer
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toZeroString(long value, int length) {
		StringBuffer sb = new StringBuffer();

		int i = 0;
		if (value < 0) {
			sb.append("-");
			i++;
		}

		for (; i < length; i++)
			sb.append("0");

		String s = Long.toString(Math.abs(value));
		sb.replace(length - s.length(), length, s);
		return sb.toString();
	}

	/**
	 * integer를 0이 앞에 붙은 String으로 변환하는 method
	 *
	 * @param value  String으로 변환하고자하는 integer
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toZeroString(int value, int length) {
		StringBuffer sb = new StringBuffer();

		int i = 0;
		if (value < 0) {
			sb.append("-");
			i++;
		}

		for (; i < length; i++)
			sb.append("0");

		String s = Integer.toString(Math.abs(value));
		sb.replace(length - s.length(), length, s);
		return sb.toString();
	}

	/**
	 * String을 0이 앞에 붙은 String으로 변환하는 method
	 *
	 * @param s      변환하고자하는 String
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toZeroString(String s, int length) {
		byte[] dest = new byte[length];
		int i;

		for (i = 0; i < dest.length; i++)
			dest[i] = 0x30;

		if (s == null)
			s = "";

		byte[] src = s.getBytes();
		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, length - src.length, src.length);

		return new String(dest);
	}

	/**
	 * String을 0이 뒤에 붙은 String으로 변환하는 method
	 *
	 * @param s      변환하고자하는 String
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toPrependZeroString(String s, int length) {
		byte[] dest = new byte[length];
		int i;

		for (i = 0; i < dest.length; i++)
			dest[i] = 0x30;

		if (s == null)
			s = "";

		byte[] src = s.getBytes();
		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, 0, src.length);

		return new String(dest);
	}

	/**
	 * integer를 space가 앞에 붙은 String으로 변환하는 method
	 *
	 * @param value  String으로 변환하고자하는 integer
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toAppendSpaceString(int value, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++)
			sb.append(" ");

		String s = Integer.toString(value);
		sb.replace(length - s.length(), length, s);
		return sb.toString();
	}

	/**
	 * String을 space가 앞에 붙은 String으로 변환하는 method
	 *
	 * @param s      변환하고자하는 String
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toAppendSpaceString(String s, int length) {
		byte[] dest = new byte[length];
		int i;

		for (i = 0; i < dest.length; i++)
			dest[i] = 0x20;

		if (s == null)
			s = "";

		byte[] src = s.getBytes();

		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, length - src.length, src.length);

		return new String(dest);
	}

	/**
	 * integer를 space가 뒤에 붙은 String으로 변환하는 method
	 *
	 * @param value  String으로 변환하고자하는 integer
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toPrependSpaceString(int value, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++)
			sb.append(" ");

		String s = Integer.toString(value);
		sb.replace(0, s.length(), s);
		return sb.toString();
	}

	/**
	 * String을 space가 뒤에 붙은 String으로 변환하는 method
	 *
	 * @param s      변환하고자하는 String
	 * @param length 변환된 String의 총 길이
	 * @return 변환된 String
	 */
	public static String toPrependSpaceString(String s, int length) {
		byte[] dest = new byte[length];
		int i;

		for (i = 0; i < dest.length; i++)
			dest[i] = 0x20;

		if (s == null)
			s = "";

		byte[] src = s.getBytes();
		if (src.length > length)
			System.arraycopy(src, 0, dest, 0, length);
		else
			System.arraycopy(src, 0, dest, 0, src.length);

		return new String(dest);
	}

	// /**
	// * byte array의 일부를 copy하여 String으로 return하는 method.<br>
	// * 한글의 경우 byte로 계산하면 2byte이지만 <br>
	// * String은 길이가 1이므로 substring을 사용하면 둘이 서로 length가 달라지게 된다.<br>
	// * 따라서 byte[]로 받은 자료를 substring으로 잘라내지 말고 copyArray를 사용하여 잘라내야 한다.
	// */
	// public static String copyArray(byte[] src, int index, int length) {
	// byte[] b = new byte[length];
	// System.arraycopy(src, index, b, 0, b.length);
	//
	// return new String(b);
	// }
	//
	// public static String copyArray(byte[] src, int index, int length, String charset) throws Exception {
	// byte[] b = new byte[length];
	// System.arraycopy(src, index, b, 0, b.length);
	//
	// return new String(b, charset);
	// }
	//
	// public static String copyArray(byte[] src, int index) {
	// int length = src.length - index;
	//
	// return copyArray(src, index, length);
	// }

	public static byte[] copyFixByteArray(byte[] src, int length) {
		byte[] b = new byte[length];
		System.arraycopy(src, 0, b, 0, src.length);
		return b;
	}

	public static byte[] copyByteArray(byte[] src, int index, int length) {
		byte[] b = new byte[length];
		System.arraycopy(src, index, b, 0, b.length);
		return b;
	}

	public static byte[] copyByteArray(byte[] src, int index) {
		int length = src.length - index;
		return copyByteArray(src, index, length);
	}

	public static String copyCString(byte[] src, int index, int length) {
		byte[] b = new byte[length];
		int i;

		for (i = 0; i < b.length; i++) {
			b[i] = src[index + i];
			if (b[i] == (byte) 0x00)
				break;
		}

		byte[] t = new byte[i + 1];
		System.arraycopy(b, 0, t, 0, t.length);

		return new String(t);
	}

	/**
	 * 숫자 인지 체크 ( 숫자이면 True 리턴)
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		// char c;
		//
		// if (str.equals(""))
		// return false;
		//
		// for (int i = 0; i < str.length(); i++) {
		// c = str.charAt(i);
		// if (c < 48 || c > 59) {
		// return false;
		// }
		// }
		// return true;
		return str.matches(numberCheckRegEx);
	}

	/**
	 * String이 Integer 형인지 검사
	 *
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * length 만큼 9를 채운 숫자 리턴<br>
	 * ex) length=4 &gt; return 9999
	 *
	 * @param length
	 * @return
	 */
	public static int getMaxInt(int length) {
		int temp = 0;

		for (int i = 0; i < length; i++) {
			temp += (Math.pow(10, i) * 9);
		}

		return temp;
	}

	/**
	 * 랜덤 번호 생성
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumber(int min, int max) {
		int range = max - min + 1;

		int ranNum = new Random().nextInt(range) + min;

		return ranNum;
	}
}
