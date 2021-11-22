package com.fih.ishareing.service.email;

import com.fih.ishareing.service.email.model.MailRequestVO;

public interface EmailService {
    boolean sendEmail(MailRequestVO request);
}
