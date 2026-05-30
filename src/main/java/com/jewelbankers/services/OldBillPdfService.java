package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Settings;

@Service
public class OldBillPdfService {

    @Autowired
    SettingsUtillity settingsUtillity;

    @Autowired
    SettingsService settingsService;

    private static final String TEMPLATE_PATH = "template/customercopy_new_edited.pdf";
    private static final String OUTPUT_PATH = "bills";

    public ByteArrayInputStream generateOldBillPdf(Bill bill, Map<String, String> settingsMap)
            throws IOException, DocumentException {
        // Ensure the output directory exists
        File dir = new File(OUTPUT_PATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Set output file path
        String outputFileName = "old_bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;

        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream fos = null;

        try {
            // Ensure essential settings are not null
            String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
            String shopOwner = settingsMap.getOrDefault("SHOP_OWNER", shopName); // fallback to shop name
            String shopLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " "
                    + settingsMap.getOrDefault("SHOP_STREET", "");
            String shopLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " "
                    + settingsMap.getOrDefault("SHOP_CITY", "");
            String shopLine3 = settingsMap.getOrDefault("SHOP_STATE", "") + " - "
                    + settingsMap.getOrDefault("SHOP_PINCODE", "");

            // Read the template PDF using robust loading
            java.io.InputStream templateStream = null;
            try {
                // 1. Try class loader (standard classpath lookup)
                templateStream = OldBillPdfService.class.getClassLoader().getResourceAsStream(TEMPLATE_PATH);
            } catch (Exception e) {
                // Ignore and try filesystem
            }

            if (templateStream == null) {
                // 2. Try direct file path (if running from Backend/)
                File file = new File(TEMPLATE_PATH);
                if (file.exists()) {
                    templateStream = new java.io.FileInputStream(file);
                } else {
                    // 3. Try with Backend/ prefix (if running from root folder)
                    File backendFile = new File("Backend/" + TEMPLATE_PATH);
                    if (backendFile.exists()) {
                        templateStream = new java.io.FileInputStream(backendFile);
                    }
                }
            }

            if (templateStream == null) {
                throw new java.io.IOException("Template file not found as resource or file: " + TEMPLATE_PATH);
            }

            try {
                reader = new PdfReader(templateStream);
            } finally {
                try {
                    templateStream.close();
                } catch (Exception e) {
                    // Ignore close exception
                }
            }

            // Create a new PDF with the content filled in memory
            ByteArrayOutputStream singlePageBaos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, singlePageBaos);

            // Set the page size to A5
            Rectangle a5Size = PageSize.A5;
            stamper.getWriter().setPageSize(a5Size); // Force the page size to A5

            // Get the PDF content
            PdfContentByte content = stamper.getOverContent(1);

            // ── Font definitions ──────────────────────────────────────────────────
            // Times Roman family: classic, professional, ideal for financial documents
            BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.EMBEDDED);
            BaseFont bfBold = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
            BaseFont bfOblique = BaseFont.createFont(BaseFont.TIMES_ITALIC, BaseFont.WINANSI, BaseFont.EMBEDDED);

            // Keep legacy aliases used below
            Font boldFont = new Font(bfBold, 14, Font.BOLD);
            Font regularFont = new Font(bf, 12, Font.NORMAL);

            // ── Shop Name – centred, large bold ──────────────────────────────────
            content.beginText();
            content.setFontAndSize(bfBold, 17);
            content.setColorFill(BaseColor.BLACK);
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 745, 0);
            content.endText();

            // ── Shop Address – centred, regular ──────────────────────────────────
            content.beginText();
            content.setFontAndSize(bf, 13); // increased from 11
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 730, 0);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 714, 0); // adjusted Y spacing
            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 698, 0); // adjusted Y spacing
            content.endText();

            // ── BILL NO value only (template already prints "BILL NO :" label) ──
            // Print only the bill serial+number value, positioned after template's label
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate billDate = bill.getBillDate();
            String billDateFormatted = billDate != null ? billDate.format(outputFormatter) : "N/A";

            content.beginText();
            content.setFontAndSize(bfBold, 18); // increased from 14
            content.setColorFill(BaseColor.BLACK); // black – Bill No value
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    bill.getBillSerial() + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"),
                    80, 665, 0); // moved left to X=80 to follow the pre-printed "Bill No :" label (ends at X=75.6)
            content.endText();

            // ── DATE value only – pushed far enough right past template's "Date :" label
            content.beginText();
            content.setFontAndSize(bfBold, 18); // increased from 15
            content.setColorFill(BaseColor.BLACK); // black – Date value
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted, 478, 665, 0); // moved right to X=478 to follow "Date :" label (ends at X=471.6)
            content.endText();

            // Check if the customer has a photo and retrieve it as byte array
            byte[] customerPhoto = bill.getCustomer() != null ? bill.getCustomer().getPhoto() : null;

            if (customerPhoto != null) {
                try {
                    Image photo = Image.getInstance(customerPhoto);
                    photo.setAbsolutePosition(470, 527); // Adjust coordinates (x, y) as needed
                    photo.scaleToFit(90, 1000); // Scale the image to fit within 80x80 size
                    content.addImage(photo);
                } catch (Exception e) {
                    System.out.println("Failed to add photo: " + e.getMessage());
                }
            }

            // ── Customer Name (value only – template prints the label) ────────────
            String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null
                    ? bill.getCustomer().getCustomerName()
                    : "Customer Name";

            content.beginText();
            content.setFontAndSize(bfBold, 14); // bigger
            content.setColorFill(BaseColor.BLACK); // black – Customer Name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 30, 610, 0);
            content.endText();

            // ── Address (value only – template prints "ADDRESS :" label) ──────────
            String rawAddress = bill.getCustomer() != null && bill.getCustomer().getAddress() != null
                    ? bill.getCustomer().getAddress()
                    : "Address Line 1, Address Line 2";
            String[] addressParts = rawAddress.split(",");
            int half = (addressParts.length + 1) / 2;
            StringBuilder addrLine1 = new StringBuilder();
            StringBuilder addrLine2 = new StringBuilder();
            for (int i = 0; i < addressParts.length; i++) {
                if (i < half) {
                    if (addrLine1.length() > 0)
                        addrLine1.append(",");
                    addrLine1.append(addressParts[i].trim());
                } else {
                    if (addrLine2.length() > 0)
                        addrLine2.append(",");
                    addrLine2.append(addressParts[i].trim());
                }
            }

            content.beginText();
            content.setFontAndSize(bf, 12); // bigger
            content.setColorFill(BaseColor.BLACK); // black – Address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addrLine1.toString(), 30, 593, 0); // down from 607
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addrLine2.toString(), 30, 578, 0); // down from 593
            content.endText();

            // ── MOBILE NO (template may not have this label – we print it) ────────
            String phoneNo = bill.getCustomer() != null && bill.getCustomer().getPhoneno() != null
                    ? bill.getCustomer().getPhoneno().toString()
                    : "";
            content.beginText();
            content.setFontAndSize(bfBold, 13); // bigger
            content.setColorFill(BaseColor.BLACK); // black – Mobile No
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "MOBILE NO: " + phoneNo, 30, 560, 0); // down from 575
            content.endText();

            // ── Article Description value / NO column ────────────────────────────
            if (bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
                String desc = bill.getBillDetails().get(0).getProductDescription() != null
                        ? bill.getBillDetails().get(0).getProductDescription()
                        : "N/A";

                // Ensure robust wrapping by adding spaces after commas and cleaning whitespace
                String processedDesc = desc.replace("\r", " ").replace("\n", " ").replace(",", ", ").replaceAll("\\s+", " ").trim();

                // ── Word-wrap description within the Articles Description column ──
                float descX = 30; // left edge of description column
                float descMaxWidth = 345; // reduced from 380 to guarantee no overlap with 'No' column
                float descFontSize = 11;
                float descLineHeight = 15;
                float descY = 480; // starting Y position

                // Split text into lines that fit within maxWidth
                java.util.List<String> wrappedLines = new java.util.ArrayList<>();
                String[] words = processedDesc.split(" ");
                StringBuilder currentLine = new StringBuilder();
                for (String word : words) {
                    if (word.isEmpty()) continue;
                    String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
                    float testWidth = bf.getWidthPoint(testLine, descFontSize);
                    if (testWidth <= descMaxWidth) {
                        currentLine = new StringBuilder(testLine);
                    } else {
                        if (currentLine.length() > 0)
                            wrappedLines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    }
                }
                if (currentLine.length() > 0)
                    wrappedLines.add(currentLine.toString());

                content.beginText();
                content.setFontAndSize(bf, descFontSize);
                content.setColorFill(BaseColor.BLACK); // black – Article description
                for (String line : wrappedLines) {
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, line, descX, descY, 0);
                    descY -= descLineHeight;
                }
                // Quantity (NO) – right-aligned under "No" column header
                content.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                        String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 415, 480, 0);
                content.endText();
            }

            content.beginText();
            content.setFontAndSize(bfBold, 17); // increased from 13 – Gross Wt
            content.setColorFill(BaseColor.BLACK); // black – Gross Wt value
            content.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                    String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A") + " grms", 558, 478, 0);
            content.endText();

            // ── Net Wt value (right column, under "Net Wt:" label) ───────────────
            content.beginText();
            content.setFontAndSize(bfBold, 17); // increased from 13 – Net Wt
            content.setColorFill(BaseColor.BLACK); // black – Net Wt value
            content.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                    String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A") + " grms", 558, 378, 0);
            content.endText();

            // ── Present Value (right column, under "Present Value:" row) ─────────
            content.beginText();
            content.setFontAndSize(bfBold, 17); // increased from 13 – Present Value
            content.setColorFill(BaseColor.BLACK); // black – Present Value
            content.showTextAligned(PdfContentByte.ALIGN_RIGHT,
                    "Rs : " + String.valueOf(bill.getPresentValue() != null ? bill.getPresentValue() : "N/A") + "/-",
                    558, 330, 0);
            content.endText();

            // ── Old Bill Serial No – printed above Loan Amount row ───────────────
            String oldBillSerialNo = bill.getOldbillserialno();
            if (oldBillSerialNo != null && !oldBillSerialNo.trim().isEmpty()) {
                String formattedOldBillNo;
                if (oldBillSerialNo.length() > 1) {
                    formattedOldBillNo = oldBillSerialNo.substring(0, 1) + " " + oldBillSerialNo.substring(1);
                } else {
                    formattedOldBillNo = oldBillSerialNo;
                }
                content.beginText();
                content.setFontAndSize(bfBold, 13);
                content.setColorFill(BaseColor.BLACK); // black – Old Bill No
                content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                        formattedOldBillNo, 25, 375, 0);
                content.endText();
            }

            // ── AMOUNT value – positioned to follow the new bilingual "Loan Amount :" label
            content.beginText();
            content.setFontAndSize(bfBold, 24); // increased from 20 – Loan Amount
            content.setColorFill(BaseColor.BLACK); // black – Loan Amount value
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    "Rs : " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A") + "/-", 275, 352, 0);
            content.endText();

            // ── Amount In Words – italic, below the amount box ───────────────────
            content.beginText();
            content.setFontAndSize(bfOblique, 16); // increased from 13 – Amount In Words
            content.setColorFill(BaseColor.BLACK); // black – Amount In Words
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getAmountInWords() != null ? bill.getAmountInWords() : "N/A"), 30, 330, 0);
            content.endText();

            // Fetch settings from the database
            List<Settings> settingsList = settingsService.getSettings();

            // Convert the settings list to a map
            settingsMap = settingsUtillity.convertListToMap(settingsList);

            // Fetch pledge rules using the utility method
            String pledgeRules = settingsUtillity.getPledgeRules(settingsMap);

            if (pledgeRules == null) {
                pledgeRules = "The final due date for pledged items is only 1 year and 7 days: Interest must be paid once every three months without fail. The last date to redeem the pledged items: Reason for borrowing: My monthly income:";
            }

            // Compute due date for use in pledge rules
            LocalDate billDate1 = bill.getBillDate();
            LocalDate dueDate = billDate1.plusYears(1).plusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            String dueDateFormatted = dueDate.format(formatter);

            // ── Pledge Rules – blue font, matching reference document style ──────────
            String lastDateLabel = "The last date to redeem the pledged items: ";
            float pledgeFontSize = 13f; // increased from 12
            float pledgeSpacing = 18f; // tighter spacing to match reference
            BaseColor pledgeBlue = new BaseColor(0, 51, 153); // navy blue matching reference

            // Lines before the last-date line
            String[] linesBeforeLastDate = {
                    "The final due date for pledged items is only 1 year and 7 days:",
                    "Interest must be paid once every three months without fail."
            };
            // Lines after the last-date line
            String[] linesAfterLastDate = {
                    "Reason for borrowing:",
                    "My monthly income:"
            };

            float yPos = 295;

            // Print first two lines in blue
            content.beginText();
            content.setFontAndSize(bf, pledgeFontSize);
            content.setColorFill(pledgeBlue);
            for (String line : linesBeforeLastDate) {
                content.showTextAligned(Element.ALIGN_LEFT, line, 25, yPos, 0);
                yPos -= pledgeSpacing;
            }
            content.endText();

            // Print "The last date..." label in blue, then bold date in blue
            content.beginText();
            content.setFontAndSize(bf, pledgeFontSize);
            content.setColorFill(pledgeBlue);
            content.showTextAligned(Element.ALIGN_LEFT, lastDateLabel, 25, yPos, 0);
            content.endText();
            float labelWidth = bf.getWidthPoint(lastDateLabel, pledgeFontSize);
            content.beginText();
            content.setFontAndSize(bfBold, pledgeFontSize);
            content.setColorFill(BaseColor.BLACK); // date value in black bold
            content.showTextAligned(Element.ALIGN_LEFT, dueDateFormatted, 25 + labelWidth, yPos, 0);
            content.endText();
            yPos -= pledgeSpacing;

            // Print remaining lines in blue
            content.beginText();
            content.setFontAndSize(bf, pledgeFontSize);
            content.setColorFill(pledgeBlue);
            for (String line : linesAfterLastDate) {
                content.showTextAligned(Element.ALIGN_LEFT, line, 25, yPos, 0);
                yPos -= pledgeSpacing;
            }
            content.endText();

            // ── Monthly Income value – bold BLACK, inline with blue label ─────────────
            float monthlyIncomeY = yPos + pledgeSpacing;
            float monthlyIncomeLabelWidth = bf.getWidthPoint("My monthly income:", pledgeFontSize);
            content.beginText();
            content.setFontAndSize(bfBold, pledgeFontSize);
            content.setColorFill(BaseColor.BLACK); // income value in black bold
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getMonthlyIncome() != null ? bill.getMonthlyIncome() : "N/A"),
                    25 + monthlyIncomeLabelWidth + 5, monthlyIncomeY, 0);
            content.endText();

            // ── Bottom stub: bill no, customer, date, grams, amount, description ──
            // ── Bottom stub: bill no, customer, date, grams, amount, description ──
            String formattedBillNo = bill.getBillSerial() + " " + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A");

            // 1. Bill No Row (spans 3 rows visually using size 24 bold)
            content.beginText();
            content.setFontAndSize(bfBold, 11);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "No. : ", 120, 86, 0); // changed to No.
            content.endText();

            content.beginText();
            content.setFontAndSize(bfBold, 24); // rowspan 3 visually
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, formattedBillNo, 160, 82, 0);
            content.endText();

            // 2. Customer Name Row (spans 2 rows visually using size 14 bold)
            content.beginText();
            content.setFontAndSize(bfBold, 11);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Name : ", 120, 64, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(bfBold, 14); // rowspan 2 visually
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 165, 64, 0);
            content.endText();

            // 3. Date & Grams Row (shifted down to Y=48)
            DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate billDate2 = bill.getBillDate();
            String billDateFormatted1 = (billDate != null) ? billDate2.format(outputFormatter1) : "N/A";

            content.beginText();
            content.setFontAndSize(bf, 11);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Dt. " + billDateFormatted1, 120, 48, 0);
            content.endText();

            // Format grams to "9.00 gms"
            String gramsText = "N/A";
            if (bill.getGrams() != null) {
                try {
                    double g = Double.parseDouble(bill.getGrams().toString());
                    gramsText = String.format(Locale.US, "%.2f gms", g);
                } catch (Exception e) {
                    gramsText = bill.getGrams().toString() + " gms";
                }
            } else {
                gramsText = "N/A gms";
            }

            content.beginText();
            content.setFontAndSize(bfBold, 11);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, gramsText, 240, 48, 0);
            content.endText();

            // 4. Amount & Description Row (shifted down to Y=32)
            content.beginText();
            content.setFontAndSize(bfBold, 12);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Amt. Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 120, 32, 0);
            content.endText();

            // ── Bottom stub: Articles – summarize if description too long ──────────
            String stubDesc = bill.getBillDetails().get(0).getProductDescription() != null
                    ? bill.getBillDetails().get(0).getProductDescription()
                    : "N/A";
            float stubMaxWidth = 200f; // available width in the bottom stub for articles
            float stubFontSize = 11f;

            // Check if the text overflows the stub column
            if (bf.getWidthPoint(stubDesc, stubFontSize) > stubMaxWidth) {
                // Count items by splitting on comma
                int itemCount = stubDesc.split(",").length;

                // Detect product type from productTypeNo (gold=1, silver=2, else "")
                String productTypeName = "";
                if (bill.getProductTypeNo() != null) {
                    int typeNo = bill.getProductTypeNo().intValue();
                    if (typeNo == 1)
                        productTypeName = "gold";
                    else if (typeNo == 2)
                        productTypeName = "silver";
                }

                // Convert number to English word (1–20)
                String[] numberWords = { "", "one", "two", "three", "four", "five",
                        "six", "seven", "eight", "nine", "ten",
                        "eleven", "twelve", "thirteen", "fourteen", "fifteen",
                        "sixteen", "seventeen", "eighteen", "nineteen", "twenty" };
                String countWord = (itemCount > 0 && itemCount <= 20)
                        ? numberWords[itemCount]
                        : String.valueOf(itemCount);

                // Build summary e.g. "Four gold articles"
                String suffix = "article" + (itemCount > 1 ? "s" : "");
                stubDesc = (countWord + (productTypeName.isEmpty() ? " " : " " + productTypeName + " ") + suffix)
                        .substring(0, 1).toUpperCase()
                        + (countWord + (productTypeName.isEmpty() ? " " : " " + productTypeName + " ") + suffix)
                                .substring(1);
            }

            // Print the description on the right side of the Amount value (X=250) on the same Y=32 row
            content.beginText();
            content.setFontAndSize(bf, (int) stubFontSize);
            content.setColorFill(BaseColor.BLACK);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, stubDesc, 250, 32, 0);
            content.endText();

            // Monthly income is now printed inline with pledge rules above

            // ── "For SHOP_OWNER" – aligned to same row as template's "Sign of the Customer" ──
            // Note: No white rectangle needed here because there is no pre-printed "For Shop" in the new template.
            // This prevents cutting off the bottom-left corner of the navy border!

            // Print "For [SHOP_OWNER]" at the same Y as "Sign of the Customer" on the right
            content.beginText();
            content.setFontAndSize(bfBold, 12);
            content.setColorFill(new BaseColor(0, 51, 153)); // navy blue – For shopOwner
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "For " + shopOwner, 42, 142, 0);
            content.endText();

            stamper.close();
            byte[] singlePageBytes = singlePageBaos.toByteArray();

            // Create a 2-page PDF by duplicating this single page twice
            ByteArrayOutputStream doublePageBaos = new ByteArrayOutputStream();
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfCopy copy = new com.itextpdf.text.pdf.PdfCopy(doc, doublePageBaos);
            doc.open();

            // Load the generated single-page PDF
            PdfReader singleReader = new PdfReader(singlePageBytes);
            com.itextpdf.text.pdf.PdfImportedPage page = copy.getImportedPage(singleReader, 1);

            // Add the same page twice
            copy.addPage(page);
            copy.addPage(page);

            doc.close();
            singleReader.close();

            // Write the final 2-page PDF to outputFilePath
            byte[] finalBytes = doublePageBaos.toByteArray();
            fos = new FileOutputStream(outputFilePath);
            fos.write(finalBytes);
            fos.flush();
            fos.close();
            fos = null;

            return new ByteArrayInputStream(finalBytes);

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
