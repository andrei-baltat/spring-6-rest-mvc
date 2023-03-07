package com.example.spring6restmvc.services;

import com.example.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    void updateBeerById(UUID beerId, Beer beer);

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, Beer beer);
}
