package collect.jhjz.com.mytest.tool;

import android.util.Base64;

import java.security.MessageDigest;
import java.util.Date;

public class EncryptUtil {
	private static final String UTF8 = "utf-8";

	/**
	 * MD5加密
	 * @param src
	 * @return
	 * @throws Exception
	 * @time yellow 2016年2月16日 下午7:02:40
	 */
	public static String md5Digest(String src) throws Exception {
		// 定义数字签名方法, 可用：MD5, SHA-1
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes(UTF8));
		return byte2HexStr(b);
	}

	/**
	 * SHA加密
	 * @param mess
	 * @return
	 * @throws Exception
	 * @time yellow 2016年2月16日 下午7:02:12
	 */
	public static String shaDigest(String mess) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(mess.getBytes(UTF8));
		byte[] digest = md.digest();
		return byte2HexStr(digest);
	}

	/**
	 * BASE64编码,注意:可能在末尾会有"\n",在http中使用时需要去掉.
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String base64Encoder(String src) throws Exception {
		byte[] b = Base64.encode(src.getBytes(UTF8), Base64.DEFAULT);
		String str = new String(b);
		return str.replaceAll("\n", "");//必须替换"\n","\r\n",否则http错误.;
	}

	/**
	 * BASE64解码
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public static String base64Decoder(String dest) throws Exception {
		return new String(Base64.decode(dest, Base64.DEFAULT), UTF8);
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * @param b
	 * @return
	 */
	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 生成签名
	 * @param appKey
	 * @param appToken
	 * @param secretKey
	 * @return
	 * @throws Exception
	 * @time yellow 2016年2月16日 下午6:54:12
	 */
	public static String createSignature(String appKey, String appToken,
										 String secretKey) throws Exception {
		String src = appKey+appToken+secretKey+DateUtil.format(new Date(), DateUtil.PATTERN_DATE);//按天
		//String src = appKey+appToken+secretKey+DateUtil.format(new Date(), DateUtil.PIN_PATTERN_DATE_MINUTE);//按分钟
//		String src = appKey+appToken+secretKey+DateUtil.format(new Date(), DateUtil.PIN_PATTERN_DATE_HOUR);//按小时
		return md5Digest(src);
	}
}
