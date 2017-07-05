package com.treph.compaign.bean;

/**
 * Created by Theodore on 6/30/2017.
 */
public class Campaign {
    private final String partnerId;
    private final int durationInSeconds;
    private final String content;
    private final long creationTime;

    public Campaign(CampaignDTO campaignDTO) {
        partnerId = campaignDTO.getPartner_id();
        durationInSeconds = campaignDTO.getDuration();
        content = campaignDTO.getAd_content();
        creationTime = System.currentTimeMillis();
    }


    public String getPartnerId() {
        return partnerId;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public boolean isActive() {
        long durationTime = creationTime + (durationInSeconds * 1000);
        return (durationTime >= System.currentTimeMillis()) ? true : false;
    }
}
