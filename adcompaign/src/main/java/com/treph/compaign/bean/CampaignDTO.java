package com.treph.compaign.bean;

/**
 * Created by Theodore on 6/30/2017.
 */
public class CampaignDTO {
    private String partner_id;
    private int duration;
    private String ad_content;

    public CampaignDTO() {}

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAd_content() {
        return ad_content;
    }

    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }
}
