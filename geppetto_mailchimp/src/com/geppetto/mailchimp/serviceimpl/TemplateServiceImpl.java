package com.geppetto.mailchimp.serviceimpl;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geppetto.mailchimp.dao.TemplateDao;
import com.geppetto.mailchimp.dto.BaseTemplate;
import com.geppetto.mailchimp.dto.Template;
import com.geppetto.mailchimp.service.TemplateService;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 28-July-2015
 *         </p>
 *
 */

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {

	private static final Logger LOG = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Autowired
	private TemplateDao templateDao;

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	@Transactional
	public ArrayList<BaseTemplate> findAllBaseTemplates() throws Exception {
		LOG.debug("Find all base templates method has been initialized in the service layer!");
		return this.templateDao.findAllBaseTemplates();
	}

	@Override
	@Transactional
	public BaseTemplate findBaseTemplate(long templateSno) throws Exception {
		LOG.debug("Find base template method has been initialized in the service layer!");
		return this.templateDao.findBaseTemplate(templateSno);
	}

	@Override
	@Transactional
	public ArrayList<Template> findAllTemplates() throws Exception {
		LOG.debug("Find all templates method has been initialized in the service layer!");
		return this.templateDao.findAllTemplates();
	}

	@Override
	@Transactional
	public Template findTemplate(long templateSno) throws Exception {
		LOG.debug("Find template method has been initialized in the service layer!");
		return this.templateDao.findTemplate(templateSno);
	}

	@Override
	@Transactional
	public Template createTemplate(Template template) throws Exception {
		LOG.debug("Create template method has been initialized in the service layer!");

		BaseTemplate baseTemplate = this.templateDao.findBaseTemplate(template.getBaseTemplateId());

		if (baseTemplate != null) {
			template.setSourceCode(baseTemplate.getSourceCode());
		} else {
			template.setBaseTemplateId(template.getTemplateSno());
		}

		String replacingContent = template.getSourceCode().replace("*|BODYHEADER|*", template.getBodyHeader());
		replacingContent = replacingContent.replace("*|BODYSUBJECT|*", template.getBodySubject());
		replacingContent = replacingContent.replace("*|BODYCONTENT|*", template.getBodyContent());
		replacingContent = replacingContent.replace("*|BODYFOOTER|*", template.getBodyFooter());
		replacingContent = replacingContent.replace("*|APPNAME|*", template.getAppName());
		replacingContent = replacingContent.replace("*|EMAILADDRESS|*", template.getEmailAddress());

		template.setModifiedCode(replacingContent);
		template.setCreatedBy(9999);
		template.setUpdatedBy(9999);
		template.setCreatedDate(new Date());
		template.setUpdatedDate(new Date());
		return this.templateDao.createTemplate(template);
	}

	@Override
	@Transactional
	public Template updateTemplate(Template template) throws Exception {
		LOG.debug("Update template method has been initialized in the service layer!");

		String replacingContent = template.getSourceCode().replace("*|BODYHEADER|*", template.getBodyHeader());
		replacingContent = replacingContent.replace("*|BODYSUBJECT|*", template.getBodySubject());
		replacingContent = replacingContent.replace("*|BODYCONTENT|*", template.getBodyContent());
		replacingContent = replacingContent.replace("*|BODYFOOTER|*", template.getBodyFooter());
		replacingContent = replacingContent.replace("*|APPNAME|*", template.getAppName());
		replacingContent = replacingContent.replace("*|EMAILADDRESS|*", template.getEmailAddress());

		template.setModifiedCode(replacingContent);
		template.setUpdatedBy(9999);
		template.setUpdatedDate(new Date());

		return this.templateDao.updateTemplate(template);
	}

	@Override
	@Transactional
	public boolean deleteTemplate(long templateSno) throws Exception {
		LOG.debug("Delete template method has been initialized in the service layer!");
		return this.templateDao.deleteTemplate(templateSno);
	}
}