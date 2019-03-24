package yummy.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    static Properties properties;
    static Message msg;
    static Transport transport;

    public MailUtil(){
        properties = new Properties();
        properties.setProperty("mail.debug","true");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");

        Session session = Session.getInstance(properties);

        msg = new MimeMessage(session);

        try{
            msg.setSubject("Yummy注册邮箱验证码");
            msg.setFrom(new InternetAddress("3045430392@qq.com"));
            transport = session.getTransport();
            transport.connect("3045430392@qq.com","hykbiskmzdjcdgfg");
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    public void sendMail(String address,String text)throws AddressException,MessagingException{
        msg.setText(text);
        transport.sendMessage(msg,new Address[]{new InternetAddress(address)});
        transport.close();
    }
}
