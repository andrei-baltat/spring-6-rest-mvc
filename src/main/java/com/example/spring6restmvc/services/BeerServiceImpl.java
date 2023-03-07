package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService{
    private Map<UUID, Beer> beerMap;
    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();
        Beer galaxyCat = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1234")
                .quantityOnHand(900)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .price(new BigDecimal("12.99"))
                .build();

        Beer cranck = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Cranck")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("44523")
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .price(new BigDecimal("99.99"))
                .build();


        Beer sunshine = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine")
                .beerStyle(BeerStyle.IPA)
                .upc("111")
                .quantityOnHand(9)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .price(new BigDecimal("09.99"))
                .build();
        beerMap.put(galaxyCat.getId(), galaxyCat);
        beerMap.put(cranck.getId(), cranck);
        beerMap.put(sunshine.getId(), sunshine);

    }


    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get beer ID ");
        return beerMap.get(id);
    }
}