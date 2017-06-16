package com.nikitachizhik91.web.controllers;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nikitachizhik91.model.Performer;
import com.nikitachizhik91.model.Report;
import com.nikitachizhik91.service.DomainException;
import com.nikitachizhik91.service.PerformerService;
import com.nikitachizhik91.service.ReportService;
import com.nikitachizhik91.web.WebException;

@Controller
public class ReportController {
    private final static Logger log = LogManager.getLogger(ReportController.class.getName());
    @Autowired
    private ReportService reportService;
    @Autowired
    private PerformerService performerService;

    @GetMapping(value = "/reports")
    public ModelAndView showAll(ModelAndView model) throws WebException {
        log.trace("Get request to find all reports");
        List<Performer> performers = null;
        try {
            performers = performerService.findAll();
        } catch (DomainException e) {
            log.error("Cannot find all reports.", e);
            throw new WebException("Cannot find all reports.", e);
        }
        model.addObject("timePeriods", reportService.getTimePeriods());
        model.addObject("performers", performers);
        model.setViewName("reports");
        log.trace("Found all reports");
        return model;
    }

    @PostMapping(value = "/reports")
    public ModelAndView findAll(@RequestParam("timePeriod") String timePeriod,
            @RequestParam("performer") int performerId, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) throws WebException {
        log.trace("Get request to find all reports");
        List<Report> reports = null;
        List<Performer> performers = null;
        Performer performer = null;
        String message = null;
        try {
            performers = performerService.findAll();
            performer = performerService.findById(performerId);
            reports = reportService.findReportsBy(timePeriod, performer, startDate, endDate);
        } catch (ParseException e) {
            message = "Date is wrong, try again.";
        } catch (DomainException e) {
            log.error("Cannot find all reports.", e);
            throw new WebException("Cannot find all reports.", e);
        }
        ModelAndView model = makeModel(reports, performers, message);
        log.trace("Found all reports");
        return model;
    }

    private ModelAndView makeModel(List<Report> reports, List<Performer> performers, String message) {
        if (!(reports == null)) {
            if (reports.isEmpty()) {
                message = "No reports found.";
            }
        }
        ModelAndView model = new ModelAndView();
        model.addObject("message", message);
        model.addObject("timePeriods", reportService.getTimePeriods());
        model.addObject("reports", reports);
        model.addObject("performers", performers);
        model.setViewName("reports");
        return model;
    }
}
