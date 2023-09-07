package com.WebClient.webclientdemo.controller;


import com.WebClient.webclientdemo.dto.webclient.UserSorgulamaDTO;
import com.WebClient.webclientdemo.service.WebClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/vericek/")
public class WebClientController {

    private final WebClientService webClientService;

    public WebClientController(WebClientService webClientService) {
        this.webClientService = webClientService;
    }


    /// sadece bir tckno ile bir soorgu atmak istersek ama karsı entpoint in karsılıgı olmalı

    @GetMapping("/sorgula/{tckno}")
    public UserSorgulamaDTO sorgula(@PathVariable String tckno) {
        return webClientService.kisiSorgula(tckno);
    }

//veriyi  body olarak göndermek istersek   MONO sayesidne tek der alırız
    @PostMapping("/bodypost")
    public Mono<ResponseEntity<UserSorgulamaDTO>> karalisteyeEkle(@RequestBody UserSorgulamaDTO userSorgulamaDTO) {
        return webClientService.kisiyiKaydet(userSorgulamaDTO)
                .map(savedUser -> ResponseEntity.ok(savedUser))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @GetMapping("/{tcNo}")
    public Flux<UserSorgulamaDTO> getUser(@PathVariable String tcNo) {
        return webClientService.nameGoreGetir(tcNo);
    }






}
