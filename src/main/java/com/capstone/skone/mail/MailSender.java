package com.capstone.skone.mail;

import com.capstone.skone.auction.application.AuctionService;
import com.capstone.skone.auth.application.MemberService;
import com.sun.mail.util.MailLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//https://ktko.tistory.com/entry/JAVA-SMTP%EC%99%80-Mail-%EB%B0%9C%EC%86%A1%ED%95%98%EA%B8%B0Google-Naver
public class MailSender {


    @Autowired
    private JavaMailSender mailSender;
    private MailLogger logger;


    public String sendMail(){
        String host = "smtp.naver.com";
        //admin email, password
        String adminMail = "skone_ad@naver.com";
        String adminMailPw = "skone-ad@";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.naver.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.naver.com");

        System.out.println("prop = " + prop);

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminMail, adminMailPw);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adminMail));

            //수신자 이메일(Address To_addressTo)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("tyto_alba@naver.com"));
            // 메일 제목
            message.setSubject("Skone Test Title");
            // 메일 내용
            message.setText("Scone Chat Link");
            // send the message
            Transport.send(message);
            System.out.println("Success Message Send");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("에러");
        }

        return "SEND SUCCESS";
    }

/*String from,String to, String subject, String text*/

    public String sendMailSeller(Long mailId, Authentication auth) {
        String host = "smtp.naver.com";
        //admin email, password
        String adminMail = "skone_ad@naver.com";
        String adminMailPw = "skone-ad@";
        //발신자 이메일, 비밀번호
        String user = auth.getName();
        //String password = memberService.loadUserByUsername(user).getPassword();
        //String addressTo = auctionService.getSingleAuction(mailId).getMember().getEmail();
        Object pass2 = auth.getPrincipal();
        System.out.println("pass2 = " + pass2);
        System.out.println("user = " + user);
        //System.out.println("password = " + password);
        //System.out.println("addressTo = " + addressTo);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.naver.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.naver.com");

        System.out.println("prop = " + prop);

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminMail, adminMailPw);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adminMail));

            //수신자 이메일(Address To_addressTo)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("tyto_alba@naver.com"));
            // 메일 제목
            message.setSubject("Skone Test Title");
            // 메일 내용
            message.setText("Scone Chat Link");
            // send the message
            Transport.send(message);
            System.out.println("Success Message Send");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("에러");
        }

        return "SEND SUCCESS";
    }

    public String sendMailBuyer(Long mailId, Authentication auth){
        String host = "smtp.naver.com";
        //admin email, password
        String adminMail = "skone_ad@naver.com";
        String adminMailPw = "skone-ad@";
        //발신자 이메일, 비밀번호
        String user = auth.getName();
        //String password = memberService.loadUserByUsername(user).getPassword();
        //String addressTo = auctionService.getSingleAuction(mailId).getMember().getEmail();
        Object pass2 = auth.getPrincipal();
        System.out.println("pass2 = " + pass2);
        System.out.println("user = " + user);
        //System.out.println("password = " + password);
        //System.out.println("addressTo = " + addressTo);

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.naver.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.naver.com");

        System.out.println("prop = " + prop);

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminMail, adminMailPw);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adminMail));

            //수신자 이메일(Address To_user)
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("tyto_alba@naver.com"));
            // 메일 제목
            message.setSubject("Skone Test Title");
            // 메일 내용
            message.setText("Scone Chat Link");
            // send the message
            Transport.send(message);
            System.out.println("Success Message Send");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("에러");
        }
        return "SEND SUCCESS";
    }


}
