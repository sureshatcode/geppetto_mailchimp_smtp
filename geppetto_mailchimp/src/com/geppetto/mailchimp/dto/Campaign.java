package com.geppetto.mailchimp.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 28-July-2015
 *         </p>
 *
 */

@Entity
@Table(name = "campaigns")
@SqlResultSetMapping(name = "CampaignResultSet", entities = { @EntityResult(entityClass = Campaign.class) })
public class Campaign {

	public Campaign() {
	}

	public Campaign(long campaignSno, String campaignTitle, String campaignLabel, String campaignDescription,
			Template emailTemplate, long createdBy, Date createdDate, long updatedBy, Date updatedDate) {
		this.campaignSno = campaignSno;
		this.campaignTitle = campaignTitle;
		this.campaignLabel = campaignLabel;
		this.campaignDescription = campaignDescription;
		this.emailTemplate = emailTemplate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "campaign_sno")
	private long campaignSno;

	@Column(name = "campaign_title")
	private String campaignTitle;

	@Column(name = "campaign_label")
	private String campaignLabel;

	@Column(name = "campaign_description")
	private String campaignDescription;

	@OneToOne
	@JoinColumn(name = "template_id")
	private Template emailTemplate;

	@Column(name = "created_by")
	private long createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "updated_by")
	private long updatedBy;

	@Column(name = "updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	public long getCampaignSno() {
		return campaignSno;
	}

	public void setCampaignSno(long campaignSno) {
		this.campaignSno = campaignSno;
	}

	public String getCampaignTitle() {
		return campaignTitle;
	}

	public void setCampaignTitle(String campaignTitle) {
		this.campaignTitle = campaignTitle;
	}

	public String getCampaignLabel() {
		return campaignLabel;
	}

	public void setCampaignLabel(String campaignLabel) {
		this.campaignLabel = campaignLabel;
	}

	public String getCampaignDescription() {
		return campaignDescription;
	}

	public void setCampaignDescription(String campaignDescription) {
		this.campaignDescription = campaignDescription;
	}

	public Template getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(Template emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Campaign [campaignSno=" + campaignSno + ", campaignTitle=" + campaignTitle + ", campaignLabel="
				+ campaignLabel + ", campaignDescription=" + campaignDescription + ", emailTemplate=" + emailTemplate
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}
}