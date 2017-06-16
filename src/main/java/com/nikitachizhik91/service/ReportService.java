package com.nikitachizhik91.service;

import java.text.ParseException;
import java.util.List;

import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.model.Report;

public interface ReportService {

    List<Report> findAll() throws DomainException;

    List<Report> findReportsBy(String timePeriod, Performer performer, String startDate, String endDate)
            throws DomainException, ParseException;

    String[] getTimePeriods();
}
