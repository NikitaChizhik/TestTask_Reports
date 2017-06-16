package com.nikitachizhik91.dao;

import com.nikitachizhik91.model.Performer;

public interface PerformerDao extends Crud<Performer> {

    Performer findById(int id) throws DaoException;
}
