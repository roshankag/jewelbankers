package com.jewelbankers.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream generateBillExcel(List<Bill> bills) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bills");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Bill Serial");
        headerRow.createCell(1).setCellValue("Bill No");
        headerRow.createCell(2).setCellValue("Customer Name");
        headerRow.createCell(3).setCellValue("Address");
        headerRow.createCell(4).setCellValue("Weight (grams)");
        headerRow.createCell(5).setCellValue("Amount");
        headerRow.createCell(6).setCellValue("Product Description");
        headerRow.createCell(7).setCellValue("Bill Date");
        headerRow.createCell(8).setCellValue("Amount in Words");

        int rowIdx = 1;

        // Populate data rows
        for (Bill bill : bills) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(bill.getBillSerial().toString());
            row.createCell(1).setCellValue(bill.getBillNo());
            row.createCell(2).setCellValue(bill.getCustomer() != null ? bill.getCustomer().getCustomerName():"");
            StringBuffer address=new StringBuffer();
            if (bill.getCustomer() != null) {
            	address.append(bill.getCustomer().getStreet());
            	address.append(bill.getCustomer().getArea());
            	address.append(bill.getCustomer().getDistrict());
            	address.append(bill.getCustomer().getState());
            	address.append(bill.getCustomer().getCountry());
            	address.append(bill.getCustomer().getPincode());
            	address.append(bill.getCustomer().getMobileno());
            }
            row.createCell(3).setCellValue(address.toString());
            row.createCell(4).setCellValue(bill.getGrams()!= null ? bill.getGrams().toString():"");
            row.createCell(5).setCellValue(bill.getAmount());
            row.createCell(7).setCellValue(bill.getBillDate()!= null ? bill.getBillDate().toString():"");
            row.createCell(8).setCellValue(bill.getAmountInWords());

            // Add BillDetail rows below each Bill row
            for (BillDetail detail : bill.getBillDetails()) {
//                Row detailRow = sheet.createRow(rowIdx++);
                row.createCell(6).setCellValue(detail.getProductDescription());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
