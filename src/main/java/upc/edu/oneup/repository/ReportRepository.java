package upc.edu.oneup.repository;

import upc.edu.oneup.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
   // boolean existsByUser_Id(int id);
    Report findByPatient_Id(int id);
}
