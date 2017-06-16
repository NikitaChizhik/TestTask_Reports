package com.nikitachizhik91.service;

import java.util.List;

import com.nikitachizhik91.model.Performer;

public interface PerformerService {

    List<Performer> findAll() throws DomainException;

    Performer findById(int id) throws DomainException;
}
