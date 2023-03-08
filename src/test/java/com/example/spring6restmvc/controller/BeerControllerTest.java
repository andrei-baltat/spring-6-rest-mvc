package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.services.BeerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired // add a mock context
    MockMvc mockMvc;

    @MockBean // put in context a beer service mock bean
    BeerService beerService;


    @Test
    @SneakyThrows
    void getBeerById() {
        UUID id = UUID.randomUUID();

        Beer galaxyCat = Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1234")
                .quantityOnHand(900)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .price(new BigDecimal("12.99"))
                .build();

        when(beerService.getBeerById(id)).thenReturn(galaxyCat);

        mockMvc.perform(get("/api/v1/beer/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}