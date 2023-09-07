package com.WebClient.webclientdemo.service;

import com.WebClient.webclientdemo.dto.ResponseDTO;
import com.WebClient.webclientdemo.dto.webclient.UserSorgulamaDTO;
import com.WebClient.webclientdemo.model.UserSorgulama;
import com.WebClient.webclientdemo.repository.UserSorgulamaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {


    private final WebClient.Builder webClientBuilder;
    private final UserSorgulamaRepository userSorgulamaRepository;
private final ModelMapper modelMapper;


    public WebClientService(WebClient.Builder webClientBuilder, UserSorgulamaRepository userSorgulamaRepository, ModelMapper modelMapper) {
        this.webClientBuilder = webClientBuilder;
        this.userSorgulamaRepository = userSorgulamaRepository;
        this.modelMapper = modelMapper;
    }

    public UserSorgulamaDTO kisiSorgula(String tckno) {
        String url = "http://150.150.200:8080/v1/kisisorgula/" + tckno;

        ResponseDTO response = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        return response.getUserSorgulamaDTO();
    }


    // post ile bir body ile işlem yapmak istersek
    public Mono<UserSorgulamaDTO> kisiyiKaydet(UserSorgulamaDTO userSorgulamaDTO) {
        return webClientBuilder.build()
                .post()
                .uri("http://external-service-url/kendiuzantım")
                .body(Mono.just(userSorgulamaDTO), UserSorgulamaDTO.class)
                .retrieve()
                .bodyToMono(UserSorgulamaDTO.class);
    }

    // bura ile veriyi bir data base göndermek istersek cektigimiz bir veriyi ve bir den çok veri oldugunda
    // extrada model mapper ile güclendirimis halidir
    public Flux<UserSorgulamaDTO> nameGoreGetir(String tcNo) {
        Flux<UserSorgulamaDTO> externalFlux = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/v1/resepsion/" + tcNo)
                .retrieve()
                .bodyToFlux(UserSorgulamaDTO.class);

        Mono<UserSorgulama> dbMono = userSorgulamaRepository.findByTcNo(tcNo);

        return externalFlux.map(externalData -> {
            // Dış servisten gelen veriyi işleyin
            return externalData;
        }).onErrorResume(e -> {
            // Hata durumunda ne yapılacağı
            return Flux.empty();
        }).mergeWith(dbMono.flatMap(dbData -> {
            // Veritabanından gelen veriyi DTO'ya dönüştürün
            UserSorgulamaDTO dbDto = modelMapper.map(dbData, UserSorgulamaDTO.class);
            return Mono.just(dbDto);
        }));
    }
}





