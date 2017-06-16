package com.nikitachizhik91.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nikitachizhik91.dao.DaoException;
import com.nikitachizhik91.dao.ReportDao;
import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.model.Report;

@Repository
public class ReportDaoHibernate implements ReportDao {
    private final static Logger log = LogManager.getLogger(ReportDaoHibernate.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Report> findAll() throws DaoException {
        log.trace("Started findAll() method.");
        List<Report> reports = null;
        try (Session session = sessionFactory.openSession()) {
            reports = (List<Report>) session.createQuery("from Report").list();
        }
        log.info("Found all reports :");
        log.trace("Finished findAll() method.");
        return reports;
    }

    @SuppressWarnings("unchecked")
    public List<Report> findReportsByDateAndPerformer(Performer performer, Date startDate, Date endDate)
            throws DaoException {
        log.trace("Started findReportsByDateAndPerformer().");
        List<Report> reports;
        if (performer.getName().equals("All Performers")) {
            try (Session session = sessionFactory.openSession()) {
                reports = (List<Report>) session.createQuery("from Report where date between  :startDate and :endDate")
                        .setParameter("startDate", startDate).setParameter("endDate", endDate).list();
            }
            log.info("Got " + reports.size() + " reports for " + performer + " and date between " + startDate + " and "
                    + endDate);
            log.trace("Finished findReportsByDateAndPerformer() method.");
        } else {
            try (Session session = sessionFactory.openSession()) {
                reports = (List<Report>) session
                        .createQuery(
                                "from Report where performer = :performer and date between  :startDate and :endDate")
                        .setParameter("performer", performer).setParameter("startDate", startDate)
                        .setParameter("endDate", endDate).list();
            }
            log.info("Got " + reports.size() + " reports for performer=" + performer + " and date between " + startDate
                    + " and " + endDate);
            log.trace("Finished findReportsByDateAndPerformer() method.");
        }
        return reports;
    }
}
