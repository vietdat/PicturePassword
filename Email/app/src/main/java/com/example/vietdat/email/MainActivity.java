package com.example.vietdat.email;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//fpwrsaolfrmubeet -- gg pass
        final Button send = (Button) this.findViewById(R.id.sendMail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SendMailActivity", "Send Button Clicked.");
                String fromEmail = "levietdatqn@gmail.com";
                String fromPassword = "fpwrsaolfrmubeet";
                String toEmails = "levietdat01091994@gmail.com";
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                String emailSubject = "test thu";
                String emailBody = "lhohfash hask khong c gi";
                new SendMailTask(MainActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
            }
        });
    }

    public static Message createGmailMessage(Context aContext ,final String userName, final String password, final String mailDestination, String subject, String textMessage) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        Message message = null;
        try
        {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(mailDestination));
            message.setSubject(subject);
            message.setContent(textMessage, "text/html; charset=utf-8");
        }
        catch (MessagingException e)
        {
            Toast.makeText(aContext, "Error",Toast.LENGTH_SHORT).show();
        }

        return message;
    }
}
