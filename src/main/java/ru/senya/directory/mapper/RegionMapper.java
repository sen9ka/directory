package ru.senya.directory.mapper;

import org.apache.ibatis.annotations.*;
import ru.senya.directory.entity.model.Region;
import java.util.List;
import java.util.Optional;

@Mapper
public interface RegionMapper {

    @Select("SELECT * FROM region")
    List<Region> getAllRegions();

    @Select("SELECT * FROM region WHERE id = #{id}")
    Optional<Region> findRegionById(Integer id);

    @Insert("INSERT INTO region(name, shortname) VALUES(#{name}, #{shortName})")
    void saveRegion(Region region);

    @Update("UPDATE region SET name = #{name}, shortname = #{shortName} WHERE id = #{id}")
    void updateRegion(Region region);

    @Delete("DELETE FROM region WHERE id = #{id}")
    void deleteRegion(Integer id);
}
