package com.example.spring6restmvc.controller;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.model.BeerStyle;
import com.example.spring6restmvc.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    final static String URL = "/api/v1/beer";

    @Autowired // add a mock context
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean // put in context a beer service mock bean
    BeerService beerService;

    @Test
    @SneakyThrows
    void testCreateNewBeer() {

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

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(galaxyCat);
        mockMvc.perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(galaxyCat)))
                        .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"));

    }

    @Test
    @SneakyThrows
    void testForUpdateBeer(){
        UUID id = UUID.randomUUID();

        Beer beer = Beer.builder()
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


        mockMvc.perform(put(URL + "/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class)); // will verify one interaction
    }

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

        mockMvc.perform(get(URL + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(galaxyCat.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(galaxyCat.getBeerName())));
    }


    @Test
    @SneakyThrows
    void testListBeers() {
        UUID id = UUID.randomUUID();

        Beer galaxyCat1 = Beer.builder()
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

        Beer galaxyCat2 = Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy cat1")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1234")
                .quantityOnHand(900)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .price(new BigDecimal("12.99"))
                .build();

        when(beerService.listBeers()).thenReturn(List.of(
                galaxyCat1, galaxyCat2
        ));

        mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }
}