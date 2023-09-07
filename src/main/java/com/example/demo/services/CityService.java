package com.example.demo.services;

import com.example.demo.model.CityDto;
import com.example.demo.model.CityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
//@RequiredArgsConstructor
public class CityService {
    private WebClient webClient;

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CityDto getCityDto(String ip){
        CityRequest request = webClient.get()
//                .uri(authServiceIntegrationProperties.getUrlAuthService() + "/api/v1/cart")
//                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", "Token 9fe2b2e5ad5f3160c13c2d3179b84db9a20c3a91")
                .retrieve()
//                .onStatus(
//                        HttpStatusCode::is4xxClientError,
//                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
//                )
                .bodyToMono(CityRequest.class)
                .block();
        assert request != null;
//        System.out.println(request.getLocation().getValue());
        return new CityDto(request.getLocation().getValue());
    }
}
