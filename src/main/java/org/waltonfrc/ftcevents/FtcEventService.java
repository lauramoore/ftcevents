package org.waltonfrc.ftcevents;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FtcEventService {
    private String apiUrl = "https://frc-api.firstinspires.org/v3.0/2023/matches/GAGai?tournamentLevel=Qualification&start=1";

    @Value("${frc.username}")
	private String m_username;
	@Value("${frc.token}")
    private String m_token;

    private final RestTemplate m_restTemplate;
    private final ObjectMapper m_objectMapper;

    

    public FtcEventService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        m_restTemplate = restTemplate;
        m_objectMapper = objectMapper;
    }

    public List<Match> getListOfMatches() {
        String url = apiUrl;
        HttpHeaders headers = createHeaders(m_username, m_token );
         HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> responseObject = m_restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        if(responseObject.getBody() == null) throw new RuntimeException("empty response body");
        JsonNode matchData = responseObject.getBody().at("/Matches");
        if(matchData == null) return Collections.emptyList();
        try {
            return m_objectMapper.readValue(
            m_objectMapper.treeAsTokens(matchData),
            new TypeReference<List<Match>>() {});
        } catch(IOException e) {
           throw new RuntimeException(e.toString());
        }    

    }

     private HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        return headers;
    }
}






