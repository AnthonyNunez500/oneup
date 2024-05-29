package upc.edu.oneup.service;

import upc.edu.oneup.model.Report;

import java.util.List;

public interface ReportService {
    List<Report> getAllReports();
    Report getReportById(int id);
    Report getReportByUserId(int id);
    Report saveReport(Report report);
    void deleteReport(int id);
}
