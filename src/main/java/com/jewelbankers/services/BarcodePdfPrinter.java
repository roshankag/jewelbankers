package com.jewelbankers.services;

import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

@Service
public class BarcodePdfPrinter {

    // Method to generate a PDF containing the barcode
    public static void generateBarcodePDF(byte[] barcodeImageData, String outputFilePath) throws Exception {
        PdfWriter writer = new PdfWriter(outputFilePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add barcode image to the document
        // You can directly create an image from the byte array
        Image barcodeImage = new Image(ImageDataFactory.create(barcodeImageData));
        document.add(new Paragraph("Barcode:").setFontSize(16));
        document.add(barcodeImage);

        // Close the document
        document.close();
    }
}
