package upc.edu.oneup.repository;

import upc.edu.oneup.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Device findByPatient_Id(int id);
}
