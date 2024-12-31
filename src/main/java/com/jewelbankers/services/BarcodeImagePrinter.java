package com.jewelbankers.services;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class BarcodeImagePrinter {

    // Method to generate a barcode image in JPG format
    public static byte[] generateBarcodeImage(String barcodeText) throws Exception {
        int width = 300; // Width of the barcode
        int height = 100; // Height of the barcode
        String format = "JPG"; // Image format

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // Error correction level
        hints.put(EncodeHintType.MARGIN, 1); // Set margin

        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height, hints);

        // Create an empty image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE); // Background color
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK); // Barcode color
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }

        // Convert image to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

