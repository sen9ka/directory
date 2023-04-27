package ru.senya.directory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.senya.directory.controller.exceptionHandler.exception.RegionNotFoundException;
import ru.senya.directory.entity.model.Region;
import ru.senya.directory.mapper.RegionMapper;
import ru.senya.directory.service.RegionService;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

	@Mock
	private RegionMapper regionMapper;

	@InjectMocks
	private RegionService regionService;

	private Region region;

	@BeforeEach
	void setUp() {
		region = Region.builder()
				.id(1)
				.name("Test Region")
				.shortName("TR")
				.build();
	}

	@Test
	@DisplayName("Сравнение ожидаемого списка регионов с действительным")
	void getAllRegionsTest() {
		List<Region> expectedRegions = Arrays.asList(region);
		when(regionMapper.getAllRegions()).thenReturn(expectedRegions);

		List<Region> actualRegions = regionService.getAllRegions();

		verify(regionMapper).getAllRegions();
		assertEquals(expectedRegions, actualRegions);
	}

	@Test
	@DisplayName("Сравнение ожидаемого региона с действительным")
	void findRegionByIdTest() {
		when(regionMapper.findRegionById(anyInt())).thenReturn(Optional.of(region));

		Region actualRegion = regionService.findRegionById(1);

		verify(regionMapper).findRegionById(1);
		assertEquals(region, actualRegion);
	}

	@Test
	@DisplayName("Выбрасывание ошибки RegionNotFoundException при отсутствии региона с указанным D")
	void findRegionByIdThrowsExceptionTest() {
		when(regionMapper.findRegionById(anyInt())).thenReturn(Optional.empty());

		assertThrows(RegionNotFoundException.class, () -> {
			regionService.findRegionById(1);
		});

		verify(regionMapper).findRegionById(1);
	}

	@Test
	@DisplayName("Проверка работоспособности сохранения региона")
	void saveRegionTest() {
		regionService.saveRegion(region);

		verify(regionMapper).saveRegion(region);
	}

	@Test
	@DisplayName("Проверка работоспособности обновления региона")
	void updateRegionTest() {
		Region updatedRegion = region.toBuilder()
				.name("Updated Region")
				.shortName("UR")
				.build();
		when(regionMapper.findRegionById(anyInt())).thenReturn(Optional.of(region));

		Region actualRegion = regionService.updateRegion(updatedRegion);

		verify(regionMapper).findRegionById(1);
		verify(regionMapper).updateRegion(updatedRegion);
		assertEquals(updatedRegion, actualRegion);
	}

	@Test
	@DisplayName("Проверка работоспособности удаления региона")
	void deleteRegionTest() {
		when(regionMapper.findRegionById(anyInt())).thenReturn(Optional.of(region));
		when(regionMapper.getAllRegions()).thenReturn(Arrays.asList(region));

		List<Region> actualRegions = regionService.deleteRegion(1);

		verify(regionMapper).findRegionById(1);
		verify(regionMapper).deleteRegion(1);
		verify(regionMapper).getAllRegions();
		assertEquals(Arrays.asList(region), actualRegions);
	}

	@Test
	@DisplayName("Выбрасывание исключения RegionNotFoundException при попытке удалить несуществующий регион")
	void deleteRegionWithInvalidIdTest() {
		when(regionMapper.findRegionById(anyInt())).thenReturn(Optional.empty());

		assertThrows(RegionNotFoundException.class, () -> {
			regionService.deleteRegion(1);
		});

		verify(regionMapper).findRegionById(1);
		verify(regionMapper, never()).deleteRegion(anyInt());
		verify(regionMapper, never()).getAllRegions();
	}
}