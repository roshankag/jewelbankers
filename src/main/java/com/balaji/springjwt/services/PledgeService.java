package com.balaji.springjwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balaji.springjwt.models.BillUpload;
import com.balaji.springjwt.models.FileUploadResponse;
import com.balaji.springjwt.models.Pledge;
import com.balaji.springjwt.repository.BillUploadRepository;
import com.balaji.springjwt.repository.PledgeRepository;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PledgeService {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private BillUploadRepository billUploadRepository;

    public List<Pledge> getAllPledges() {
        return pledgeRepository.findAll();
    }

    public Optional<Pledge> getPledgeById(Long id) {
        return pledgeRepository.findById(id);
    }

    public List<Pledge> getPledgesByCustomerId(Long customerId) {
        return pledgeRepository.findByCustomer_CustomerId(customerId);
    }

    public Pledge savePledge(Pledge pledge) {
        return pledgeRepository.save(pledge);
    }

    public void generateAndSendBill(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pledge ID"));

        // Check if bill is already uploaded
        if (pledge.getBillUpload() != null) {
            // Bill already uploaded, send link to WhatsApp
            sendWhatsAppMessage(pledge.getBillUpload().getLink(), pledge.getCustomer().getPhone());
            return;
        }

        try {
            // Generate the PDF
            

            // Upload the PDF file
            String filePath = pdfService.generateAndSaveBillPdf(pledge); // Retrieve the actual path
            FileUploadResponse response = fileUploadService.uploadFile(filePath);
            System.out.println("File uploaded");
            // Save the response to the database
            BillUpload billUpload = new BillUpload();
            billUpload.setUploadId(response.getFileId());
            billUpload.setPledge(pledge);
            billUpload.setLink(response.getLink());
            billUpload.setExpires(response.getExpires());
            billUpload.setAutoDelete(response.isAutoDelete());
            // billUpload.setPledge(pledge);

            billUploadRepository.save(billUpload);

            // Associate bill upload with the pledge
            pledge.setBillUpload(billUpload);
            pledgeRepository.save(pledge);

            // Send WhatsApp message with the link
            sendWhatsAppMessage(response.getLink(), pledge.getCustomer().getPhone());

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }


    private void sendWhatsAppMessage(String link, String phone) {
        // TODO Auto-generated method stub
        System.out.println(link+" "+phone);
        // throw new UnsupportedOperationException("Unimplemented method 'sendWhatsAppMessage'");
    }

    public Pledge updatePledge(Long id, Pledge pledgeDetails) {
        Pledge pledge = pledgeRepository.findById(id).orElseThrow(() -> new RuntimeException("Pledge not found"));

        pledge.setBillNo(pledgeDetails.getBillNo());
        pledge.setProductType(pledgeDetails.getProductType());
        pledge.setAmount(pledgeDetails.getAmount());
        pledge.setRateOfInterest(pledgeDetails.getRateOfInterest());
        pledge.setPresentValue(pledgeDetails.getPresentValue());
        pledge.setBillDate(pledgeDetails.getBillDate());
        pledge.setGrams(pledgeDetails.getGrams());
        pledge.setProductQuantity(pledgeDetails.getProductQuantity());
        pledge.setProductDescription(pledgeDetails.getProductDescription());
        pledge.setAmountInWords(pledgeDetails.getAmountInWords());
        pledge.setInterestPledge(pledgeDetails.getInterestPledge());
        pledge.setTotalAmount(pledgeDetails.getTotalAmount());

        return pledgeRepository.save(pledge);
    }

    public void deletePledge(Long id) {
        Pledge pledge = pledgeRepository.findById(id).orElseThrow(() -> new RuntimeException("Pledge not found"));
        pledgeRepository.delete(pledge);
    }
}
