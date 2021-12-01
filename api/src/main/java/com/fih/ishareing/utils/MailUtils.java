package com.fih.ishareing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailUtils {
    private static Logger logger = LoggerFactory.getLogger(MailUtils.class);

    @Value("${spring.mail.host}")
    private String smtpHost;
//    @Value("${spring.mail.port}")
//    private String smtpPort;
    @Value("${spring.mail.username}")
    private String smtpUsername;
    @Value("${spring.mail.password}")
    private String smtpPassword;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String smtpEnable;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private String smtpRequired;
    @Value("${serverHost}")
    private String serverHost;

    private String resetEmailTemplate = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>微樂志工</title></head><body>" +
            "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "            <div style=\"float: left; font-size: 26px;\">微樂志工 會員密碼重新設定</div>\n" +
            "            <div style=\"float: right;\">* 再一個步驟就完成囉</div>\n" +
            "            <div style=\"clear: both;\"></div>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            <br />\n" +
            "            哈囉~親愛的使用者：<br />\n" +
            "            <br />\n" +
            "            請使用電腦點擊 <a href=\"%s\">這個連結</a> 後，重新設定您的密碼。<br />\n" +
            "            若無法點選瀏覽，請複製以下網址至您的瀏覽器，即可瀏覽：<br />\n" +
            "            <br />\n" +
            "            <a href=\"%s\">%s</a><br />\n" +
            "            <br />\n" +
            "            <br />\n" +
            "            微樂志工 客服敬上<br />\n" +
            "            <br />\n" +
            "        </div>" +
            "</body></html>";

    private String addNpoEmailTemplate = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>微樂志工</title></head><body>" +
            "<div style=\"background: #f0873b; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "        <div style=\"float: left; font-size: 26px;\">微樂志工 成為夥伴</div>\n" +
            "        <div style=\"float: right;\">* 再兩個步驟就完成囉</div>\n" +
            "        <div style=\"clear: both;\"></div>\n" +
            "    </div>\n" +
            "    <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "        <br />\n" +
            "    您好，<br />\n" +
            "        <br />\n" +
            "    感謝您申請成為夥伴，加入<微樂志工>媒合平台，<br />\n" +
            "    快到 <a href=\"%s\">這個地方</a> 填寫詳細資料，讓大家更加認識你！<br />\n" +
            "    完整填寫資料後，管理員會再寄出審核通知，請隨時留意信箱。<br />\n" +
            "    若無法點選瀏覽，請複製以下網址至您的瀏覽器，謝謝。<br />\n" +
            "        <br />\n" +
            "        <a href=\"%s\">%s</a><br />\n" +
            "        <br />\n" +
            "        <img src=\"https://www.isharing.tw/static/images/join_3.png\"><br />\n" +
            "        <br />\n" +
            "    想詢問更多問題，歡迎<a href=\"mailto:TCCPA@taiwanmobile.com\">聯絡我們</a>，謝謝您！<br />\n" +
            "        <br />\n" +
            "        <b>祝您有個美好的一天! <微樂志工>敬啟</b><br />\n" +
            "        <br />\n" +
            "    微樂志工網站：<a href=\"%s\">%s</a><br />\n" +
            "        <br />\n" +
            "    </div>\n" +
            "    <div style=\"background: #f0873b; padding: 10px; color: white; text-align: center; font-family: 微軟正黑體; font-size: 13px;\">\n" +
            "        <div style=\"\"><a href=\"%s\">微樂志工網站</a> | <a href=\"https://itunes.apple.com/tw/app/wei-le-zhi-gong/id823341128?l=zh&mt=8\">iPhone App</a> | <a href=\"https://play.google.com/store/apps/details?id=com.taiwanmobile.volunteers\">Android App</a></div>\n" +
            "        <div style=\"\">微樂志工 版權所有 &copy; 2013 willo All Rights Reserved.</div>\n" +
            "    </div>" +
            "</body></html>";

    private String npoVerifiedEmailTemplate = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>微樂志工</title></head><body>" +
            "<div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            管理者您好\n" +
            "            <br />\n" +
            "                    新的活動社團申請「成為夥伴」，待您完成審核。<br />\n" +
            "                    附上邀請連結: <a href=\"%s\">%s</a><br /></div>" +
            "</body></html>";

    private String unRegisterEventEmailTemplate = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "            <div style=\"float: left; font-size: 26px;\">微樂志工 取消報名通知</div>\n" +
            "            <div style=\"clear: both;\"></div>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            親愛的夥伴您好：<br />\n" +
            "            %s  已取消報名%s ，請至<a href=\"%s \">%s </a>\n" +
            "            查詢，謝謝！<br />\n" +
            "            <br />\n" +
            "            微樂志工www.isharing.tw\n" +
            "        </div>";

    private String unRegisterSupplyEventNpoEmailTemplate = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "            <div style=\"float: left; font-size: 26px;\">微樂志工 取消捐贈通知</div>\n" +
            "            <div style=\"clear: both;\"></div>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            親愛的夥伴您好：<br />\n" +
            "            %s 已取消捐贈物資予 貴單位，如有疑問請直接聯絡捐贈者，謝謝！<br/ >\n" +
            "            <br/ >\n" +
            "            <b>取消捐贈資訊</b><br/ >\n" +
            "            <br/ >\n" +
            "            物資訊息名稱：%s  <br />\n" +
            "            取消捐贈物資內容：%s  <br />\n" +
            "            取消捐贈者：%s  <br />\n" +
            "            聯絡電話： %s  <br />\n" +
            "            E-mail: %s  <br />\n" +
            "            請至<a href=\"%s \">%s </a>查詢，謝謝！<br />\n" +
            "            <br />\n" +
            "            ---<br />\n" +
            "            微樂志工平台 系統通知信<br />\n" +
            "        </div>";

    private String unRegisterSupplyEventMemberEmailTemplate = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "        <div style=\"float: left; font-size: 26px;\">微樂志工平台 取消捐贈通知</div>\n" +
            "        <div style=\"clear: both;\"></div>\n" +
            "    </div>\n" +
            "    <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "        <br />\n" +
            "        親愛的 %s  您好：<br />\n" +
            "        收到您取消捐贈物資予%s ，如有疑問請直接聯絡%s ，謝謝！<br />\n" +
            "        <br /><br />\n" +
            "        <b>取消捐贈資訊</b>\n" +
            "        <br /><br />\n" +
            "        物資訊息名稱: %s  <a href=%s >%s </a> <br />\n" +
            "        取消捐贈物資內容: %s  <br />\n" +
            "        <br />\n" +
            "        受贈單位聯絡資訊：%s <br />\n" +
            "        電話：%s  <br />\n" +
            "        地址：%s  <br />\n" +
            "        官方網站： %s  <br />\n" +
            "        <br />\n" +
            "        <br />\n" +
            "        敬祝<br />\n" +
            "        闔家平安  健康喜樂<br />\n" +
            "        <br />\n" +
            "        <br />\n" +
            "        %s  敬上<br />\n" +
            "    </div>";

    private String volunteerRegisterMailTemplateMember = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "        <div style=\"float: left; font-size: 26px;\">微樂志工平台 完成報名通知</div>\n" +
            "        <div style=\"clear: both;\"></div>\n" +
            "    </div>\n" +
            "    <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "        親愛的%s 您好：<br />\n" +
            "        感謝您使用微樂志工平台報名 %s  活動，以實際行動支持需要幫助的弱勢族群。<br />\n" +
            "        <br />\n" +
            "        活動主題：%s <br />\n" +
            "        活動連結：%s <br />\n" +
            "        <br />\n" +
            "        志工須知：<br />\n" +
            "        %s \n" +
            "        <br />\n" +
            "        微樂志工www.isharing.tw\n" +
            "    </div>";
    private String volunteerRegisterMailTemplateNpo = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "            <div style=\"float: left; font-size: 26px;\">微樂志工 新增報名通知</div>\n" +
            "            <div style=\"clear: both;\"></div>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            親愛的夥伴您好：<br />\n" +
            "            %s  已完成報名%s ，請至<a href=\"%s \">%s </a>\n" +
            "            查詢，謝謝！<br />\n" +
            "            <br />\n" +
            "            微樂志工www.isharing.tw\n" +
            "        </div>";

    private String itemRegisterMailTemplateMember = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "        <div style=\"float: left; font-size: 26px;\">微樂志工平台 捐贈通知</div>\n" +
            "        <div style=\"clear: both;\"></div>\n" +
            "    </div>\n" +
            "    <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "        <br />\n" +
            "        親愛的 %s  您好：<br />\n" +
            "        感謝您使用微樂志工平台以實際行動捐贈物資支持仍在困境中需要幫助的弱勢族群。<br />\n" +
            "        後續將由%s 的服務人員與您聯繫確認相關事宜。<br />\n" +
            "        <br />\n" +
            "        <b>捐贈資訊</b><br />\n" +
            "        物資訊息名稱: %s  (<a href=\"%s\">%s</a>) <br />\n" +
            "        %s  <br /><br />\n" +
            "        受贈單位聯絡資訊: %s  <br />\n" +
            "        電話：%s  <br />\n" +
            "        地址：%s  <br />\n" +
            "        官方網站： %s  <br />\n" +
            "        <br />\n" +
            "        %s  <br />\n" +
            "        台灣大哥大與%s 由衷致上萬分謝意，因著有您的支持，成為推動他們突破困境、向希望邁進的動力。\n" +
            "        <br />\n" +
            "        <br />\n" +
            "        敬祝<br />\n" +
            "        闔家平安  健康喜樂<br />\n" +
            "    </div>";
    private String itemRegisterMailTemplateNpo = "<div style=\"background: #ec6c00; padding: 10px; color: white; font-family: 微軟正黑體;\">\n" +
            "            <div style=\"float: left; font-size: 26px;\">微樂志工平台 物資捐贈通知</div>\n" +
            "            <div style=\"clear: both;\"></div>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 20px; 0 20px 0; font-family: 微軟正黑體;\">\n" +
            "            <br /><br />\n" +
            "            %s   物資捐贈通知<br />\n" +
            "            %s  已通知捐贈 %s ，煩請儘速與對方聯繫後續事宜。<br /><br />\n" +
            "            %s  聯絡資訊<br /><br />\n" +
            "            電話: %s  <br />\n" +
            "            E-mail: %s  <br />\n" +
            "            備註: %s  <br />\n" +
            "            <br />\n" +
            "            後台物資捐贈列表網址: <a href=\"%s \">%s </a><br />\n" +
            "        </div>";


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
//        mailSender.setPort(Integer.valueOf(smtpPort));
        if (!smtpUsername.equalsIgnoreCase("")) {
            mailSender.setUsername(smtpUsername);
        }
        if (!smtpPassword.equalsIgnoreCase("")) {
            mailSender.setPassword(smtpPassword);
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", smtpAuth);
//        props.put("mail.smtp.starttls.enable", smtpEnable);
//        props.put("mail.debug", "true");

        return mailSender;
    }

    public String getResetEmailTemplate(String content) {
        return String.format(resetEmailTemplate, content, content, content);
    }

    public String getAddNpoEmailTemplate(String content) {
        return String.format(addNpoEmailTemplate, content, content, content, serverHost, serverHost, serverHost);
    }

    public String getNpoVerifiedEmailTemplate(String content) {
        return String.format(npoVerifiedEmailTemplate, content, content);
    }

    public String getUnRegisterEventEmailTemplate(String userName, String eventSubject, String serverHostBackend) {
        return String.format(unRegisterEventEmailTemplate, userName, eventSubject, serverHostBackend, serverHostBackend);
    }

    public String getUnRegisterSupplyEventNpoEmailTemplate(String userName, String eventSubject, String itemNote, String userPhone, String userEmail, String serverHostBackend) {
        return String.format(unRegisterSupplyEventNpoEmailTemplate, userName, eventSubject, itemNote, userName, userPhone, userEmail, serverHostBackend, serverHostBackend);
    }

    public String getUnRegisterSupplyEventMemberEmailTemplate(String userName, String npoName, String eventSubject, String eventUrl, String itemNote, String npoEmail, String npoPhone, String npoAddress, String npoWebsite) {
        return String.format(unRegisterSupplyEventMemberEmailTemplate, userName, npoName, npoName, eventSubject, eventUrl, eventUrl, itemNote, npoEmail, npoPhone, npoAddress, npoWebsite, npoName);
    }

    public String getVolunteerRegisterMailTemplateMember(String userName, String eventSubject, String eventUrl, String npoVolunteerTrainingDescription) {
        return String.format(volunteerRegisterMailTemplateMember, userName, eventSubject, eventSubject, eventUrl, npoVolunteerTrainingDescription);
    }

    public String getVolunteerRegisterMailTemplateNpo(String userName, String eventSubject, String serverHostBackend) {
        return String.format(volunteerRegisterMailTemplateNpo, userName, eventSubject, serverHostBackend, serverHostBackend);
    }

    public String getItemRegisterMailTemplateMember(String userName, String npoName, String eventSubject, String eventUrl, String eventSkillsDescription, String npoEmail, String npoPhone, String npoAddress, String npoWebsite, String itemNote) {
        eventSkillsDescription = (eventSkillsDescription.equals("")) ? "" : "物資內容: " + eventSkillsDescription;
        npoName = (npoName.equals("")) ? "" : "<b>捐贈須知:</b> " + npoName;
        return String.format(itemRegisterMailTemplateMember, userName, npoName, eventSubject, eventUrl, eventUrl, eventSkillsDescription, npoEmail, npoPhone, npoAddress, npoWebsite, itemNote, npoName);
    }

    public String getItemRegisterMailTemplateNpo(String eventSubject, String userName, String userPhone, String userEmail, String itemNote, String serverHostBackend) {
        return String.format(itemRegisterMailTemplateNpo, eventSubject, userName, eventSubject, userName, userPhone, userEmail, itemNote, serverHostBackend, serverHostBackend);
    }

    public void sendMail(String to, String subject, String content) {
        Long time = System.currentTimeMillis();
        logger.info("sendMail {}: {}, {}, {}, {}", time, "isharing@isharing.tw", to, subject, content);
        JavaMailSender mailSender = getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom("isharing@isharing.tw");
            if (to.contains(",")) {
                String[] toList = to.split(",");
                helper.setTo(toList);
            } else {
                helper.setTo(to);
            }
            helper.setSubject(subject);
            helper.setText("text/html", content);

            mailSender.send(message);
            logger.info("sendMail {}: end", time);
        } catch (MessagingException e) {
            logger.info("sendMail {}: error {}", time, e.getMessage());
        }
    }
}
