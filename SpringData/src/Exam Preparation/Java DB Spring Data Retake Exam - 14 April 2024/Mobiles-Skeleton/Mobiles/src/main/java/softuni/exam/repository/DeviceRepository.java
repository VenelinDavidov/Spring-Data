package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.enums.DeviceType;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {


    Optional<Device> findByBrandAndModel(String brand, String model);



    @Query("SELECT d FROM Device d WHERE d.deviceType = :deviceType " +
            "AND d.price < :price " +
            "AND d.storage >= :storage " +
            "ORDER BY LOWER(d.brand) ASC")
    List<Device> findAllByDeviceTypeAndPriceLessThanAndStorageIsGreaterThanEqualOrderByBrand(DeviceType deviceType, double price, int storage);
}
