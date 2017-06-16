package com.nikitachizhik91.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikitachizhik91.dao.DaoException;
import com.nikitachizhik91.dao.PerformerDao;
import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.service.DomainException;
import com.nikitachizhik91.service.PerformerService;

@Service
public class PerformerServiceImpl implements PerformerService {
    private final static Logger log = LogManager.getLogger(PerformerServiceImpl.class.getName());
    @Autowired
    private PerformerDao performerDao;

    public List<Performer> findAll() throws DomainException {
        log.trace("Started findAll() method.");
        List<Performer> performers = null;
        try {
            log.trace("Finding all performers");

            performers = performerDao.findAll();

        } catch (DaoException e) {
            log.error("Cannot find all performers.", e);
            throw new DomainException("Cannot find all performers.", e);
        }
        log.trace("Finished findAll() method.");
        return performers;
    }

    public Performer findById(int id) throws DomainException {
        log.trace("Started findById() method.");
        Performer performer = null;
        try {
            log.trace("Finding performer by id.");
            performer = performerDao.findById(id);
        } catch (DaoException e) {
            log.error("Cannot find performer by id=" + id, e);
            throw new DomainException("Cannot find performer by id=" + id, e);
        }
        log.trace("Finished findById() method.");
        return performer;
    }
}
