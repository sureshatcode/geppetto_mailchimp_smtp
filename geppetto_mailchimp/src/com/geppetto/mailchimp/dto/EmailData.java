package com.geppetto.mailchimp.dto;

import java.util.Arrays;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 26-August-2015
 *         </p>
 *
 */

public class EmailData {
	private String[] receipiants;
	private long templateSno;

	public String[] getReceipiants() {
		return receipiants;
	}

	public void setReceipiants(String[] receipiants) {
		this.receipiants = receipiants;
	}

	public long getTemplateSno() {
		return templateSno;
	}

	public void setTemplateSno(long templateSno) {
		this.templateSno = templateSno;
	}

	@Override
	public String toString() {
		return "EmailData [receipiants=" + Arrays.toString(receipiants) + ", templateSno=" + templateSno + "]";
	}
}