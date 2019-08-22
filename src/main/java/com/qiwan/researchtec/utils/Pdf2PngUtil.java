package com.qiwan.researchtec.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * <br>类 名: Pdf2JpgUtils
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年5月27日 下午2:23:11
 * <br>版 本: v1.0.0
 */
public class Pdf2PngUtil {
	
	public static void toPng(String pdfFilePath, String jpgFilePath) {
        File pdfFile = new File(pdfFilePath);
        int pdfdpi = 200;
        try {
        	PDDocument document = PDDocument.load(pdfFile);
            PDFRenderer renderer = new PDFRenderer(document);
            int size = document.getNumberOfPages();
            for (int i = 0; i < size; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, pdfdpi, ImageType.RGB);
                File jpgFile = new File(jpgFilePath);
                ImageIO.write(image, "png", jpgFile);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
