package com.geppetto.mailchimp.serviceimpl;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.geppetto.mailchimp.dto.EmailData;
import com.geppetto.mailchimp.dto.Template;
import com.geppetto.mailchimp.service.EmailService;
import com.geppetto.mailchimp.service.TemplateService;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 25-August-2015
 *         </p>
 *
 */

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private TemplateService templateService;

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Autowired
	@Qualifier("mailSender")
	private JavaMailSender mailSender;

	@Value("${mail.fromname}")
	private String fromName;

	@Value("${mail.username}")
	private String fromEmail;

	@Override
	public boolean sendSMTPEmail(EmailData emailData) throws Exception {
		try {
			LOG.debug("Send SMTP email method has been initialized in the service layer!");

			if (emailData.getTemplateSno() != 0L) {
				Template template = this.templateService.findTemplate(emailData.getTemplateSno());

				if (template != null) {
					MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

					InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
					mimeMessageHelper.setFrom(fromAddress);
					mimeMessageHelper.setTo(emailData.getReceipiants());
					mimeMessageHelper.setSubject(template.getEmailSubject());
					mimeMessageHelper.setText(template.getModifiedCode(), true);
					mailSender.send(mimeMessage);

					LOG.debug("SMTP Email was sent successfully!");
				}
				return true;
			} else {
				LOG.debug("Email template was not found!");
				return false;
			}
		} catch (Exception e) {
			LOG.debug("Exception in service layer: " + e.getMessage());
			return false;
		}
	}
}