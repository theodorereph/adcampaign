package com.treph.compaign;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;

import com.treph.compaign.bean.CampaignDTO;
import com.treph.compaign.bean.CampaignResponse;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Theodore on 7/4/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=AdCompaignServer.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampaignControllerTest {
    private static final String PARTNER_ID_1 = "partner_1";
    private static final int DURATION_1 = 10 ;
    private static final String CONTENT_1 = "content_1";

    private static final String PARTNER_ID_2 = "partner_2";
    private static final int DURATION_2 = 20 ;
    private static final String CONTENT_2 = "content_2";

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${local.server.port}")
    private int port;


//    @Before
//    public void setupMock() {
//        CampaignResponse campaignResponseGetAll = createGetAllCampaignResponse();
//        when(campaignService.getAllCampaigns()).thenReturn(campaignResponseGetAll);
////        when(campaignService.getActiveCampaign(PARTNER_ID_1)).thenReturn()
//    }

    @Test
    public void createCompaignTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String uri = "http://localhost:" + port + "/ad";
        URI url = new URI(uri);
        String jsonMessage = "{\"partner_id\":\"partner_1\", \"duration\":\"10\",\"ad_content\":\"content\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonMessage, headers);
        ResponseEntity<CampaignResponse> responseEntity = restTemplate.postForEntity(url, entity, CampaignResponse.class);
        CampaignResponse campaignResponse = responseEntity.getBody();
        assertEquals(responseEntity.getStatusCode().value(), 200);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
    }

    @Test
    public void getActivePartnerCampaignTest() throws Exception {
        String uri = "http://localhost:" + port + "/ad/" + PARTNER_ID_1;
        URI url = new URI(uri);
        CampaignResponse campaignResponse = restTemplate.getForObject(url, CampaignResponse.class);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
        assertEquals(campaignResponse.getCampaignDTOList().size(), 1);
    }

    @Test
    public void getAllCampaignsTest() throws Exception {
        String uri = "http://localhost:" + port + "/ad/allCampaigns";
        URI url = new URI(uri);
        CampaignResponse campaignResponse = restTemplate.getForObject(url, CampaignResponse.class);
        assertEquals(campaignResponse.getErrorCode(), CampaignResponse.SUCCESS_CODE);
        assertEquals(campaignResponse.getCampaignDTOList().size(), 1);
    }
}
