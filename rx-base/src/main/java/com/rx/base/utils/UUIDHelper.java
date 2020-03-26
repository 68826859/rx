package com.rx.base.utils;
import java.util.UUID;

public class UUIDHelper {

    /**
     * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String[] chars2 = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "-", "_" };

    /**
     * 生成8位UUID(通过模62操作，结果作为索引取出字符)
     * @return
     */
    public static String getUUID8() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     *  生成16位UUID(通过模62操作，结果作为索引取出字符)
     * @return
     */
    public static String getUUID16() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 16; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     *  生成22位UUID(通过模62操作，结果作为索引取出字符)
     * @return
     */
    public static String getUUID22() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 每3个十六进制字符转换成为2个字符
        for (int i = 0; i < 10; i++) {
            String str = uuid.substring(i * 3, i * 3 + 3);
            //转成十六进制
            int x = Integer.parseInt(str, 16);
            //除64得到前面6个二进制数的
            shortBuffer.append(chars2[x / 0x40]);
            // 对64求余得到后面6个二进制数1
            shortBuffer.append(chars2[x % 0x40]);
        }
        //加上后面两个没有改动的
        shortBuffer.append(uuid.charAt(30));
        shortBuffer.append(uuid.charAt(31));
        return shortBuffer.toString();
    }

    /**
     * 生成2位CRC校验位
     * @param plaintext
     * @return
     */
    public static String getCheckDigit( String plaintext ){
//        String str = SharUtil.shar1( plaintext );
//        return str.substring(3,4) +  str.substring(plaintext.length()-3, plaintext.length()-2);
        int crc2 = FindCRC(plaintext.getBytes());
        String crc16 = Integer.toHexString(crc2);//把10进制的结果转化为16进制
        //如果想要保证校验码必须为2位，可以先判断结果是否比16小，如果比16小，可以在16进制的结果前面加0
        if(crc2 < 16 ){
            crc16 = "0"+crc16;
        }
        return crc16;
    }


    /**
     * 字符串校验
     * @param value
     * @return  Boolean
     */
    public static boolean verify( String value ){
        boolean result = false;
        if( StringUtil.noNull(value) && value.length() >= 3 ){
            String checkDigit = value.substring(0,2);
            String plaintext = value.substring(2,value.length());
            if( StringUtil.equals( checkDigit, getCheckDigit(plaintext) ) ){
                result = true;
            }
        }
        return result;
    }

    /**
     * 计算CRC8校验值
     * @param data
     * @return
     */
    public static int FindCRC(byte[] data){
        int CRC=0;
        int genPoly = 0Xaa;
        for(int i=0;i<data.length; i++){
            CRC ^= data[i];
            for(int j=0;j<8;j++){
                if((CRC & 0x80) != 0){
                    CRC = (CRC << 1) ^ genPoly;
                }else{
                    CRC <<= 1;
                }
            }
        }
        CRC &= 0xff;//保证CRC余码输出为2字节。
        return CRC;
    }

}
