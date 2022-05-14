package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.RegionServ;

import java.util.List;

public interface RegionServRepo extends JpaRepository<RegionServ, Integer> {

	@Query("select r from RegionServ r where r.name LIKE :namePart")
	List<RegionServ> findByNameStartingWith(@Param("namePart")String namePart);

	List<RegionServ> findByRegionId(Integer regionId);

	@Query("select r from RegionServ r where r.name LIKE :namePart and r.region.id = :regionId")
	List<RegionServ> findByRegionIdAndNameStartsWith(@Param("regionId")Integer regionId, @Param("namePart")String namePart);
}
