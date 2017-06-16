package com.nikitachizhik91.dao.hibernate;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nikitachizhik91.dao.DaoException;
import com.nikitachizhik91.model.Performer;

@Repository
public class PerformerDaoHibernate {
    private final static Logger log = LogManager.getLogger(PerformerDaoHibernate.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Performer> findAll() throws DaoException {
        log.trace("Started findAll() method.");
        List<Performer> performers = null;
        try (Session session = sessionFactory.openSession()) {
            performers = (List<Performer>) session.createQuery("from Performer").list();
        }
        log.info("Found all performers :");
        log.trace("Finished findAll() method.");
        return performers;
    }

    public Performer findById(int id) throws DaoException {
        log.trace("Started findById() method.");
        Performer performer = null;
        try (Session session = sessionFactory.openSession()) {
            performer = session.get(Performer.class, id);
        }
        log.info("Found the Performer :" + performer);
        log.trace("Finished findById() method.");
        return performer;
    }
}
