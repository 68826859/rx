package com.rx.common.utils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
*@Description: 二维码生成
*@Param: 
*@return: 
*@Author: jw.Lin
*@date: 2018/9/30
*/
public class QrcodeHandler {

    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

    private static final int SIZE = 350;// 二维码大小


    /**
     * 生成普通二维码
     *
     * @param content
     * @return
     */
    public static InputStream createQRcode(String content) {
        return getLogoQRCode(content, null, null);
    }

    /**
     * 生成带图标的二维码
     *
     * @param content
     *            二维码内容
     * @param icon
     *            图标
     * @return
     */
    public static InputStream createQRcodeWithIcon(String content, File icon) {
        return getLogoQRCode(content, null, icon);
    }

    /**
     * 解析二维码
     *
     * @param qrCodeFile
     *            二维码图片
     * @throws Exception
     */
    public static String decodeQRcode(File qrCodeFile) throws Exception {
        BufferedImage image = ImageIO.read(qrCodeFile);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
        return result.getText();
    }

    /**
     * 生成带logo的二维码图片
     *
     */
    public static InputStream getLogoQRCode(String qrUrl, String productName, File icon) {
        // 用户logo的路径
        String content = qrUrl;
        try {
            QrcodeHandler qh = new QrcodeHandler();
            BufferedImage bim = qh.getQR_CODEBufferedImage(content,
                    BarcodeFormat.QR_CODE, SIZE, SIZE, qh.getDecodeHintType());
            return qh.addLogo_QRCode(bim, icon, new LogoConfig(), productName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给二维码图片添加Logo
     *
     */
    private InputStream addLogo_QRCode(BufferedImage bim, File logoPic,
                                       LogoConfig logoConfig, String productName) {
        try {
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();
            if (logoPic != null) {
                /**
                 * 读取二维码图片，并构建绘图对象
                 */

                /**
                 * 读取Logo图片
                 */
                BufferedImage logo = ImageIO.read(logoPic);
                /**
                 * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
                 */
                int widthLogo = logo.getWidth(null) > image.getWidth() * 3 / 12 ? (image
                        .getWidth() * 3 / 12) : logo.getWidth(null), heightLogo = logo
                        .getHeight(null) > image.getHeight() * 3 / 12 ? (image
                        .getHeight() * 3 / 12) : logo.getWidth(null);

                /**
                 * logo放在中心
                 */
                int x = (image.getWidth() - widthLogo) / 2;
                int y = (image.getHeight() - heightLogo) / 2;
                /**
                 * logo放在右下角 int x = (image.getWidth() - widthLogo); int y =
                 * (image.getHeight() - heightLogo);
                 */

                // 开始绘制图片
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.drawImage(logo, x, y, widthLogo, heightLogo, null);
                g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
                g.setStroke(new BasicStroke(logoConfig.getBorder()));
                g.setColor(logoConfig.getBorderColor());
                g.drawRect(x, y, widthLogo, heightLogo);
                g.dispose();

                // 把商品名称添加上去，商品名称不要太长哦，这里最多支持两行。太长就会自动截取啦
                if (productName != null && !productName.equals("")) {
                    // 新的图片，把带logo的二维码下面加上文字
                    BufferedImage outImage = new BufferedImage(400, 445,
                            BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics2D outg = outImage.createGraphics();
                    // 画二维码到新的面板
                    outg.drawImage(image, 0, 0, image.getWidth(),
                            image.getHeight(), null);
                    // 画文字到新的面板
                    outg.setColor(Color.BLACK);
                    outg.setFont(new Font("宋体", Font.BOLD, 30)); // 字体、字型、字号
                    int strWidth = outg.getFontMetrics().stringWidth(
                            productName);
                    if (strWidth > 399) {
                        // //长度过长就截取前面部分
                        // outg.drawString(productName, 0, image.getHeight() +
                        // (outImage.getHeight() - image.getHeight())/2 + 5 );
                        // //画文字
                        // 长度过长就换行
                        String productName1 = productName.substring(0,
                                productName.length() / 2);
                        String productName2 = productName.substring(
                                productName.length() / 2, productName.length());
                        int strWidth1 = outg.getFontMetrics().stringWidth(
                                productName1);
                        int strWidth2 = outg.getFontMetrics().stringWidth(
                                productName2);
                        outg.drawString(
                                productName1,
                                200 - strWidth1 / 2,
                                image.getHeight()
                                        + (outImage.getHeight() - image
                                        .getHeight()) / 2 + 12);
                        BufferedImage outImage2 = new BufferedImage(400, 485,
                                BufferedImage.TYPE_4BYTE_ABGR);
                        Graphics2D outg2 = outImage2.createGraphics();
                        outg2.drawImage(outImage, 0, 0, outImage.getWidth(),
                                outImage.getHeight(), null);
                        outg2.setColor(Color.BLACK);
                        outg2.setFont(new Font("宋体", Font.BOLD, 30)); // 字体、字型、字号
                        outg2.drawString(
                                productName2,
                                200 - strWidth2 / 2,
                                outImage.getHeight()
                                        + (outImage2.getHeight() - outImage
                                        .getHeight()) / 2 + 5);
                        outg2.dispose();
                        outImage2.flush();
                        outImage = outImage2;
                    } else {
                        outg.drawString(
                                productName,
                                200 - strWidth / 2,
                                image.getHeight()
                                        + (outImage.getHeight() - image
                                        .getHeight()) / 2 + 12); // 画文字
                    }
                    outg.dispose();
                    outImage.flush();
                    image = outImage;
                }
                logo.flush();
                image.flush();
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(imageToBytes(image, "png"));
            return bais;

//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//			ImageIO.write(image, "png", baos);
//
//			return baos;

            // ImageIO.write(image, "png", new File("/Users/mo/Desktop/" + new
            // Date().getTime() + ".png"));

            // String imageBase64QRCode =
            // Base64.encodeBase64URLSafeString(baos.toByteArray());

            // baos.close();
            // return imageBase64QRCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建初始化二维码
     *
     * @param bm
     * @return
     */
    @SuppressWarnings("unused")
    private BufferedImage fileToBufferedImage(BitMatrix bm) {
        BufferedImage image = null;
        try {
            int w = bm.getWidth(), h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 生成二维码bufferedImage图片
     *
     * @param content
     *            编码内容
     * @param barcodeFormat
     *            编码类型
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param hints
     *            设置参数
     * @return
     */
    private BufferedImage getQR_CODEBufferedImage(String content,
                                                  BarcodeFormat barcodeFormat, int width, int height,
                                                  Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width,
                    height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    private Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        // hints.put(EncodeHintType.MAX_SIZE, 350);
        // hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }

    /**
     * 转换Image数据为byte数组
     *
     * @param image
     *            Image对象
     * @param format
     *            image格式字符串.如"gif","png"
     * @return byte数组
     */
    private static byte[] imageToBytes(Image image, String format) {
        BufferedImage bImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bImage.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 转换byte数组为Image
     *
     * @param bytes
     * @return Image
     */
    @SuppressWarnings("unused")
    private static Image bytesToImage(byte[] bytes) {
        Image image = Toolkit.getDefaultToolkit().createImage(bytes);
        try {
            MediaTracker mt = new MediaTracker(new Label());
            mt.addImage(image, 0);
            mt.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return image;
    }
}

class LogoConfig {
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 3;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }

    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }

}
