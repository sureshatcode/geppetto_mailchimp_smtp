package com.geppetto.mailchimp.serviceimpl;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geppetto.mailchimp.dao.CampaignDao;
import com.geppetto.mailchimp.dto.Campaign;
import com.geppetto.mailchimp.service.CampaignService;
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
public class CampaignServiceImpl implements CampaignService {

	private static final Logger LOG = LoggerFactory.getLogger(CampaignServiceImpl.class);

	@Autowired
	private CampaignDao campaignDao;

	public void setCampaignDao(CampaignDao campaignDao) {
		this.campaignDao = campaignDao;
	}

	@Autowired
	private TemplateService templateService;

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Override
	@Transactional
	public ArrayList<Campaign> findAllCampaigns() throws Exception {
		LOG.debug("Find all campaigns method has been initialized in the service layer!");
		return this.campaignDao.findAllCampaigns();
	}

	@Override
	@Transactional
	public Campaign findCampaign(long campaignSno) throws Exception {
		LOG.debug("Find campaign method has been initialized in the service layer!");
		return this.campaignDao.findCampaign(campaignSno);
	}

	@Override
	@Transactional
	public Campaign createCampaign(Campaign campaign) throws Exception {
		LOG.debug("Create campaign method has been initialized in the service layer!");

		if (campaign.getEmailTemplate() != null) {
			campaign.setEmailTemplate(this.templateService.createTemplate(campaign.getEmailTemplate()));

			if (campaign.getEmailTemplate().getTemplateSno() != 0L) {
				campaign.setCreatedDate(new Date());
				campaign.setUpdatedDate(new Date());

				return this.campaignDao.createCampaign(campaign);
			}
		}
		return null;
	}

	@Override
	@Transactional
	public Campaign updateCampaign(Campaign campaign) throws Exception {
		LOG.debug("Update campaign method has been initialized in the service layer!");

		campaign.setUpdatedDate(new Date());

		if (campaign.getEmailTemplate() != null) {
			this.templateService.updateTemplate(campaign.getEmailTemplate());
		}

		return this.campaignDao.updateCampaign(campaign);
	}

	@Override
	@Transactional
	public boolean deleteCampaign(long campaignSno) throws Exception {
		LOG.debug("Delete campaign method has been initialized in the service layer!");

		Campaign campaign = this.findCampaign(campaignSno);

		boolean result = false;
		if (campaign != null) {
			result = this.campaignDao.deleteCampaign(campaignSno);
			if (result) {
				result = this.templateService.deleteTemplate(campaign.getEmailTemplate().getTemplateSno());
			}
		}
		return result;
	}
}