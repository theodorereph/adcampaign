package com.treph.compaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.treph.compaign.bean.CampaignDTO;
import com.treph.compaign.bean.CampaignResponse;

/**
 * RestController for ad compaigns
 */
@RestController
@RequestMapping("/")
public class CampaignController {
    private CampaignService campaignService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }


    @RequestMapping(value = "/ad",  method = RequestMethod.POST, consumes = "application/json", produces = "application/json" )
    public CampaignResponse createAdCampaign(@RequestBody CampaignDTO campaign) {
        CampaignResponse campaignResponse = null;

        try {
            campaignResponse = campaignService.addCampaign(campaign);
        } catch (Exception exception) {
            LOGGER.error("Error creating campaign", exception);
            campaignResponse.setErrorCode(CampaignResponse.SERVER_ERROR_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SERVER_MESSAGE);
            campaignResponse.setCampaignDTOList(null);
        }
        return campaignResponse;
    }

    @RequestMapping(value = "/ad/{partner_id}",  method = RequestMethod.GET, produces = "application/json" )
    public CampaignResponse getPartnerActiveCompaign(@PathVariable String partner_id) {
        CampaignResponse campaignResponse = null;

        try {
            campaignResponse = campaignService.getActiveCampaign(partner_id);
        } catch (Exception exception) {
            LOGGER.error("Error getting campaign for partner_id " + partner_id, exception);
            campaignResponse.setErrorCode(CampaignResponse.SERVER_ERROR_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SERVER_MESSAGE);
            campaignResponse.setCampaignDTOList(null);
        }

        return campaignResponse;
    }

    @RequestMapping(value = "/ad/allCampaigns",  method = RequestMethod.GET, produces = "application/json" )
    public CampaignResponse getAllActiveCampaigns() {
        CampaignResponse campaignResponse = null;

        try {
            campaignResponse = campaignService.getAllCampaigns();
        } catch (Exception exception) {
            LOGGER.error("Error getting all of the campaigns", exception);
            campaignResponse.setErrorCode(CampaignResponse.SERVER_ERROR_CODE);
            campaignResponse.setErrorMessage(CampaignResponse.SERVER_MESSAGE);
            campaignResponse.setCampaignDTOList(null);
        }

        return campaignResponse;
    }
}
