package com.rx.pub.file.utils;
	import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 图像处理类.
 *
 * @author nagsh
 *
 */



	public class ImageUtil {
	public static byte[] scaleImage(byte[] fileByte, int LIMIT_WIDTH, int LIMIT_HEIGHT) throws Exception {
	    if (fileByte == null){
	        return null;
        }
        ByteArrayInputStream inSteam = new ByteArrayInputStream(fileByte);
        BufferedImage image = ImageIO.read(inSteam);
        ImageIcon imageIcon = new ImageIcon(image);
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();
        if (LIMIT_WIDTH != 0 || LIMIT_HEIGHT != 0){
            if (LIMIT_WIDTH != 0 && width > LIMIT_WIDTH){
                width = LIMIT_WIDTH;
                height = imageIcon.getIconHeight() * LIMIT_WIDTH / imageIcon.getIconWidth();
            }
            if (LIMIT_HEIGHT != 0 && height > LIMIT_HEIGHT){
                height = LIMIT_HEIGHT;
                width = imageIcon.getIconWidth() * LIMIT_HEIGHT / imageIcon.getIconHeight();
            }
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            Image im = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g2D.drawImage(im, 0, 0, imageIcon.getImageObserver());
            ImageIO.write(bufferedImage, "jpg", byteOut);
            return byteOut.toByteArray();
        }
        return fileByte;
	}

    /**
     * 图片缩放.
     */
    public static byte[] zoom(BufferedImage bi, int LIMIT_WIDTH, int LIMIT_HEIGHT) throws Exception {
        double sx = 0.0;
        double sy = 0.0;
        // 计算x轴y轴缩放比例--如需等比例缩放，在调用之前确保参数width和height是等比例变化的
        ImageIcon imageIcon = new ImageIcon(bi);
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();

        sx = (double) width / bi.getWidth();
        sy = (double) height / bi.getHeight();

        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(sx, sy), null);
        Image zoomImage = op.filter(bi, null);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage) zoomImage, "png", byteOut); // 保存图片

        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteOut.toByteArray();
    }

    /**
     * 旋转
     *
     * @param degree 旋转角度
     */
    public static byte[] spin(BufferedImage bi, int degree) throws Exception {
        int sWidth = 0; // 旋转后的宽度
        int sHeight = 0; // 旋转后的高度
        int x; // 原点横坐标
        int y; // 原点纵坐标
        // 处理角度--确定旋转弧度
        degree = degree % 360;
        if (degree < 0)
            degree = 360 + degree;// 将角度转换到0-360度之间
        double theta = Math.toRadians(degree);// 将角度转为弧度

        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            sWidth = bi.getWidth();
            sHeight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sHeight = bi.getWidth();
            sWidth = bi.getHeight();
        } else {
            sWidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
            sHeight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
        }

        x = (sWidth / 2) - (bi.getWidth() / 2);// 确定原点坐标
        y = (sHeight / 2) - (bi.getHeight() / 2);

        BufferedImage spinImage = new BufferedImage(sWidth, sHeight,
                bi.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.white);
        gs.fillRect(0, 0, sWidth, sHeight);// 以给定颜色绘制旋转后图片的背景

        AffineTransform at = new AffineTransform();
        at.rotate(theta, sWidth / 2, sHeight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(bi, spinImage);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageIO.write(spinImage, "png", byteOut); // 保存图片
        return byteOut.toByteArray();
    }
    /**
     * 马赛克化.
     * @param size  马赛克尺寸，即每个矩形的长宽
     */
    public static byte[] mosaic(BufferedImage bi, int size) throws Exception {
        BufferedImage spinImage = new BufferedImage(bi.getWidth(),
                bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (bi.getWidth() < size || bi.getHeight() < size || size <= 0) { // 马赛克格尺寸太大或太小
            return null;
        }
        int xCount = 0; // 方向绘制个数
        int yCount = 0; // y方向绘制个数
        if (bi.getWidth() % size == 0) {
            xCount = bi.getWidth() / size;
        } else {
            xCount = bi.getWidth() / size + 1;
        }
        if (bi.getHeight() % size == 0) {
            yCount = bi.getHeight() / size;
        } else {
            yCount = bi.getHeight() / size + 1;
        }
        int x = 0;   //坐标
        int y = 0;
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics gs = spinImage.getGraphics();
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                //马赛克矩形格大小
                int mwidth = size;
                int mheight = size;
                if(i==xCount-1){   //横向最后一个比较特殊，可能不够一个size
                    mwidth = bi.getWidth()-x;
                }
                if(j == yCount-1){  //同理
                    mheight =bi.getHeight()-y;
                }
                // 矩形颜色取中心像素点RGB值
                int centerX = x;
                int centerY = y;
                if (mwidth % 2 == 0) {
                    centerX += mwidth / 2;
                } else {
                    centerX += (mwidth - 1) / 2;
                }
                if (mheight % 2 == 0) {
                    centerY += mheight / 2;
                } else {
                    centerY += (mheight - 1) / 2;
                }
                Color color = new Color(bi.getRGB(centerX, centerY));
                gs.setColor(color);
                gs.fillRect(x, y, mwidth, mheight);
                y = y + size;// 计算下一个矩形的y坐标
            }
            y = 0;// 还原y坐标
            x = x + size;// 计算x坐标
        }
        gs.dispose();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ImageIO.write(spinImage, "png", byteOut); // 保存图片
        return byteOut.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        ImageUtil imageDeal = new ImageUtil();
        // 测试缩放
        /* imageDeal.zoom(200, 300); */
        // 测试旋转
        /* imageDeal.spin(90); */
        //测试马赛克
        /*imageDeal.mosaic(4);*/
    }

}
