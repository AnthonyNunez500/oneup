package upc.edu.oneup.service.impl;

import upc.edu.oneup.model.Report;
import upc.edu.oneup.repository.ReportRepository;
import upc.edu.oneup.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(int id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Report getReportByUserId(int id) {
        return reportRepository.findByPatient_Id(id);
    }

    @Override
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(int id) {
        reportRepository.deleteById(id);
    }


}
