package com.nikitachizhik91.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikitachizhik91.dao.DaoException;
import com.nikitachizhik91.dao.ReportDao;
import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.model.Report;
import com.nikitachizhik91.service.DomainException;
import com.nikitachizhik91.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
    private final static Logger log = LogManager.getLogger(ReportServiceImpl.class.getName());
    @Autowired
    private ReportDao reportDao;

    public static final String[] TIME_PERIODS = { "", "Last Qtr", "Last Month", "Last Calendar Year",
            "Current Year to Date", "Current Qtr to Date", "Current Month to Date" };

    public List<Report> findAll() throws DomainException {
        log.trace("Started findAll() method.");
        List<Report> lessons = null;
        try {
            log.trace("Finding all lessons");
            lessons = reportDao.findAll();
        } catch (DaoException e) {
            log.error("Cannot find all lessons.", e);
            throw new DomainException("Cannot find all lessons.", e);
        }
        log.trace("Finished findAll() method.");
        return lessons;
    }

    public List<Report> findReportsBy(String timePeriod, Performer performer, String startDate, String endDate)
            throws DomainException, ParseException {
        log.trace("Started findReportsByDateAndPerformer() method.");
        List<Report> reports = null;
        boolean periodIsEmpty = timePeriod.isEmpty();
        boolean startDateIsEmpty = startDate.isEmpty();
        boolean endDateIsEmpty = endDate.isEmpty();
        Date[] firstAndSecondDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
        Date firstDate = null;
        Date lastDate = null;
        if (!(startDate.equals("") || endDate.equals(""))) {
            firstDate = formatter.parse(startDate);
            lastDate = formatter.parse(endDate);
        }

        try {
            // period is not empty and start or(and) end is empty
            if (!periodIsEmpty && ((startDateIsEmpty && !endDateIsEmpty) || (!startDateIsEmpty && endDateIsEmpty)
                    || (startDateIsEmpty && endDateIsEmpty))) {
                firstAndSecondDate = convertTimePeriodToDate(timePeriod);
                reports = reportDao.findReportsByDateAndPerformer(performer, firstAndSecondDate[0],
                        firstAndSecondDate[1]);
            }
            // period is empty and start or(and) end is empty
            else if (periodIsEmpty && ((startDateIsEmpty && !endDateIsEmpty) || (!startDateIsEmpty && endDateIsEmpty)
                    || (startDateIsEmpty && endDateIsEmpty))) {
                reports = find(performer, firstDate, lastDate);
                // all 3 are not empty
            } else if (!periodIsEmpty && !startDateIsEmpty && !endDateIsEmpty) {
                reports = reportDao.findReportsByDateAndPerformer(performer, firstDate, lastDate);
                // period is empty , start&end not
            } else if (periodIsEmpty && !startDateIsEmpty && !endDateIsEmpty) {
                reports = reportDao.findReportsByDateAndPerformer(performer, firstDate, lastDate);
            }
        } catch (DaoException e) {
            log.error("Cannot findReportsByDateAndPerformer.", e);
            throw new DomainException("Cannot findReportsByDateAndPerformer.", e);
        }
        log.trace("Finished findReportsByDateAndPerformer() method.");
        return reports;
    }

    private List<Report> find(Performer performer, Date firstDate, Date lastDate) throws DaoException {
        List<Report> reports;
        if (performer.getName().equals("All Performers")) {
            // find all time and all performers
            reports = reportDao.findAll();
        } else {
            // find all time and by current performer
            reports = reportDao.findReportsByDateAndPerformer(performer, firstDate, lastDate);
        }
        return reports;
    }

    private Date[] convertTimePeriodToDate(String timePeriod) throws DomainException {
        Date[] firstAndSecondDate = new Date[2];
        try {
            if (timePeriod.equals("Last Qtr")) {
                firstAndSecondDate = getLastQuarter();
            } else if (timePeriod.equals("Last Month")) {
                firstAndSecondDate = getLastMonth();
            } else if (timePeriod.equals("Last Calendar Year")) {
                firstAndSecondDate = getLastCalendarYear();
            } else if (timePeriod.equals("Current Year to Date")) {
                firstAndSecondDate = getCurrentYeartoDate();
            } else if (timePeriod.equals("Current Qtr to Date")) {
                firstAndSecondDate = getCurrentQuarterToDate();
            } else if (timePeriod.equals("Current Month to Date")) {
                firstAndSecondDate = getCurrentMonthToDate();
            } else {
                log.error("Illegal timePeriod=" + timePeriod);
                throw new DomainException("Illegal timePeriod=" + timePeriod);
            }
        } catch (Exception exception) {
            log.error("Cannot parse timePeriod=" + timePeriod, exception);
            throw new DomainException("Cannot parse timePeriod=" + timePeriod, exception);
        }
        return firstAndSecondDate;
    }

    private Date[] getCurrentMonthToDate() {
        Date[] firstAndSecondDate = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    private Date[] getCurrentQuarterToDate() {
        Date[] firstAndSecondDate = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        int month = calendar.get(Calendar.MONTH);
        if (month >= Calendar.JANUARY && month <= Calendar.MARCH) {
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else if (month >= Calendar.APRIL && month <= Calendar.JUNE) {
            calendar.set(Calendar.MONTH, 3);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else if (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, 6);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        Date startDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    private Date[] getCurrentYeartoDate() {
        Date[] firstAndSecondDate = new Date[2];
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    private Date[] getLastCalendarYear() {
        Date[] firstAndSecondDate = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 11); // 11 = december
        calendar.set(Calendar.DAY_OF_MONTH, 31); // new years eve
        Date endDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    private Date[] getLastMonth() {
        Date[] firstAndSecondDate = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    private Date[] getLastQuarter() {
        Date[] firstAndSecondDate = new Date[2];
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        firstAndSecondDate[0] = startDate;
        firstAndSecondDate[1] = endDate;
        return firstAndSecondDate;
    }

    public String[] getTimePeriods() {
        return TIME_PERIODS;
    }
}
