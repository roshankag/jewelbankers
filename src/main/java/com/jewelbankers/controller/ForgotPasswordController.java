package com.jewelbankers.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.dto.ForgotPasswordRequest;
import com.jewelbankers.entity.User;
import com.jewelbankers.services.UserDetailsServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/jewelbankersapi/forgot-password")
@CrossOrigin("http://localhost:4200")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;
     
    @Autowired
    private UserDetailsServiceImpl userService;
     
    @PostMapping("/send-mail")
    public ResponseEntity<Map<String, String>> processForgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        Map<String, String> response = new HashMap<>();
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:4200/reset-password?token=" + token;
            sendEmail(email, resetPasswordLink);
            response.put("message", "We have sent a reset password link to your email. Please check.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            response.put("error", ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (UnsupportedEncodingException | MessagingException e) {
            response.put("error", "Error while sending email: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    public void sendEmail(String recipientEmail, String link) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom("roshankag2003@gmail.com", "Jewel Bankers");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
     
    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, String>> showResetPasswordForm(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        User customer = userService.getByResetPasswordToken(token);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        if (customer == null) {
            response.put("message", "Invalid Token");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Valid Token");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> processResetPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String password = requestBody.get("password");
        
        Map<String, String> response = new HashMap<>();
        User user = userService.getByResetPasswordToken(token);
        
        if (user == null) {
            response.put("message", "Invalid Token");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {           
            userService.updatePassword(user, password);
            response.put("message", "You have successfully changed your password.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }   
}
