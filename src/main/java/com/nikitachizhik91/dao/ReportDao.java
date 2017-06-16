package com.nikitachizhik91.dao;

import java.util.Date;
import java.util.List;

import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.model.Report;

public interface ReportDao extends Crud<Report> {

    List<Report> findReportsByDateAndPerformer(Performer performer, Date startDate, Date endDate) throws DaoException;
}
