package com.jewelbankers.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Customer;

@SpringBootTest(classes = com.jewelbankers.application.JewelBankers.class)
public class OldBillPdfServiceTest {

    @Autowired
    private OldBillPdfService oldBillPdfService;

    @Test
    public void testGenerateOldBillPdf() throws Exception {
        System.out.println("Starting integration test for OldBillPdfService...");

        // 1. Create a mock customer
        Customer customer = new Customer();
        customer.setCustomerid(9999L);
        customer.setCustomerName("Roshan B T Test");
        customer.setAddress("Gandhi Street, Bangalore, Karnataka");
        customer.setPincode(560001);

        // 2. Create mock bill details
        BillDetail detail = new BillDetail();
        detail.setProductDescription("Gold Ring 22ct");
        detail.setProductQuantity(1);

        // 3. Create mock bill
        Bill bill = new Bill();
        bill.setBillSerial('A');
        bill.setBillNo(1001);
        bill.setBillDate(LocalDate.now());
        bill.setCustomer(customer);
        bill.setGrams(new BigDecimal("8.50"));
        bill.setAmount(32000);
        bill.setAmountInWords("Thirty Two Thousand Rupees Only");
        bill.setPresentValue(40000);
        bill.setMonthlyIncome(25000);
        bill.setBillDetails(new ArrayList<>());
        bill.addBillDetail(detail);

        // 4. Create empty settings map (the service will fetch settings from DB itself)
        Map<String, String> settingsMap = new HashMap<>();

        // 5. Generate PDF
        ByteArrayInputStream pdfStream = oldBillPdfService.generateOldBillPdf(bill, settingsMap);
        assertNotNull(pdfStream);

        // 6. Save the generated PDF file to Samples directory for verification
        File targetFile = new File("Samples/test_generated_oldpdf.pdf");
        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[pdfStream.available()];
            int bytesRead = pdfStream.read(buffer);
            if (bytesRead > 0) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("Offline PDF generated successfully at: " + targetFile.getAbsolutePath());
    }
}
