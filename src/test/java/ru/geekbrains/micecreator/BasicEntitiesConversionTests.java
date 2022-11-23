package ru.geekbrains.micecreator;


import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.geekbrains.micecreator.dto.basic.full.AccommTypeDto;
import ru.geekbrains.micecreator.dto.basic.full.AirlineDto;
import ru.geekbrains.micecreator.dto.basic.full.AirportDto;
import ru.geekbrains.micecreator.dto.basic.full.CountryDto;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;
import ru.geekbrains.micecreator.dto.basic.full.RegionDto;
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;
import ru.geekbrains.micecreator.dto.basic.full.RoomDto;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.models.basic.AccommodationType;
import ru.geekbrains.micecreator.models.basic.Airline;
import ru.geekbrains.micecreator.models.basic.Airport;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.HotelServ;
import ru.geekbrains.micecreator.models.basic.Location;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.models.basic.RegionServ;
import ru.geekbrains.micecreator.models.basic.Room;
import ru.geekbrains.micecreator.models.currency.Currency;
import ru.geekbrains.micecreator.repository.AccommodationTypeRepo;
import ru.geekbrains.micecreator.repository.AirlineRepo;
import ru.geekbrains.micecreator.repository.AirportRepo;
import ru.geekbrains.micecreator.repository.CountryRepo;
import ru.geekbrains.micecreator.repository.HotelRepo;
import ru.geekbrains.micecreator.repository.HotelServRepo;
import ru.geekbrains.micecreator.repository.LocationRepo;
import ru.geekbrains.micecreator.repository.RegionRepo;
import ru.geekbrains.micecreator.repository.RegionServRepo;
import ru.geekbrains.micecreator.repository.RoomRepo;
import ru.geekbrains.micecreator.repository.currency.CurrencyRepo;
import ru.geekbrains.micecreator.service.AccommodationTypeService;
import ru.geekbrains.micecreator.service.AirlineService;
import ru.geekbrains.micecreator.service.AirportService;
import ru.geekbrains.micecreator.service.CountryService;
import ru.geekbrains.micecreator.service.HotelServService;
import ru.geekbrains.micecreator.service.HotelService;
import ru.geekbrains.micecreator.service.LocationService;
import ru.geekbrains.micecreator.service.RegionServService;
import ru.geekbrains.micecreator.service.RegionService;
import ru.geekbrains.micecreator.service.RoomService;
import ru.geekbrains.micecreator.service.currency.CurrencyService;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicEntitiesConversionTests {

	private static final Logger logger = LogManager.getLogger(SimpleTypeService.class);
	private static final Appender mockAppender = mock(Appender.class);

	private static final RoomRepo roomRepo = mock(RoomRepo.class);
	private static final HotelRepo hotelRepo = mock(HotelRepo.class);
	private static final HotelServRepo hotelServRepo = mock(HotelServRepo.class);
	private static final LocationRepo locationRepo = mock(LocationRepo.class);
	private static final RegionRepo regionRepo = mock(RegionRepo.class);
	private static final CountryRepo countryRepo = mock(CountryRepo.class);
	private static final RegionServRepo regionServRepo = mock(RegionServRepo.class);
	private static final AirlineRepo airlineRepo = mock(AirlineRepo.class);
	private static final AirportRepo airportRepo = mock(AirportRepo.class);
	private static final AccommodationTypeRepo accommodationTypeRepo = mock(AccommodationTypeRepo.class);
	private static final CurrencyRepo currencyRepo = mock(CurrencyRepo.class);

	private static final AccommodationTypeService accommodationTypeService = new AccommodationTypeService(accommodationTypeRepo);
	private static final AirlineService airlineService = new AirlineService(airlineRepo);
	private static final CurrencyService currencyService = new CurrencyService(currencyRepo);
	private static final CountryService countryService = new CountryService(countryRepo, currencyService);
	private static final RegionService regionService = new RegionService(regionRepo, countryService);
	private static final LocationService locationService = new LocationService(locationRepo, regionService);
	private static final HotelService hotelService = new HotelService(hotelRepo, locationService);
	private static final RoomService roomService = new RoomService(roomRepo, hotelService);
	private static final RegionServService regionServService = new RegionServService(regionServRepo, regionService);
	private static final HotelServService hotelServService = new HotelServService(hotelServRepo, hotelService);
	private static final AirportService airportService = new AirportService(airportRepo, regionService);

	private static final AccommodationType storedAccommodationType = new AccommodationType(0, "DBL", 2);
	private static final Airline storedAirline = new Airline(0, "AEROFLOT", "Описание авиакомпании");
	private static final Currency storedCurrency = new Currency(0, "USD");
	private static final Country storedCountry = new Country(0, "ИСПАНИЯ", storedCurrency, new ArrayList<>());
	private static final Region storedRegion = new Region(0, "КАТАЛОНИЯ", storedCountry, "reg.jpg", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	private static final Airport storedAirport = new Airport(0, "BCN", "БАРСЕЛОНА", storedRegion);
	private static final Location storedLocation = new Location(0, "КОСТА БРАВА", "Описание локации", "loc.jpg", storedRegion, new ArrayList<>());
	private static final Hotel storedHotel = new Hotel(0, "DON JUAN", "Описание отеля", "htl_1.jpg", "htl_2.jpg", storedLocation, new ArrayList<>(), new ArrayList<>());
	private static final HotelServ storedHotelServ = new HotelServ(0, "БАНКЕТ", "Описание услуги", "hs_1.jpg", "hs_2.jpg", storedHotel);
	private static final RegionServ storedRegionServ = new RegionServ(0, "ЭКСКУРСИЯ", "Описание услуги", "rs_1.jpg", "rs_2.jpg", storedRegion);
	private static final Room storedRoom = new Room(0, "STANDARD", "Описание номера", "room_1.jpg", "room_2.jpg", storedHotel);

	@BeforeAll
	public static void setUp() {
		logger.addAppender(mockAppender);

		storedCountry.getRegions().add(storedRegion);
		storedRegion.getLocations().add(storedLocation);
		storedRegion.getAirports().add(storedAirport);
		storedRegion.getServices().add(storedRegionServ);
		storedLocation.getHotels().add(storedHotel);
		storedHotel.getServices().add(storedHotelServ);
		storedHotel.getRooms().add(storedRoom);

		when(roomRepo.findById(0)).thenReturn(java.util.Optional.of(storedRoom));
		when(hotelRepo.findById(0)).thenReturn(java.util.Optional.of(storedHotel));
		when(hotelServRepo.findById(0)).thenReturn(java.util.Optional.of(storedHotelServ));
		when(locationRepo.findById(0)).thenReturn(java.util.Optional.of(storedLocation));
		when(regionRepo.findById(0)).thenReturn(java.util.Optional.of(storedRegion));
		when(countryRepo.findById(0)).thenReturn(java.util.Optional.of(storedCountry));
		when(regionServRepo.findById(0)).thenReturn(java.util.Optional.of(storedRegionServ));
		when(airlineRepo.findById(0)).thenReturn(java.util.Optional.of(storedAirline));
		when(airportRepo.findById(0)).thenReturn(java.util.Optional.of(storedAirport));
		when(accommodationTypeRepo.findById(0)).thenReturn(java.util.Optional.of(storedAccommodationType));
		when(currencyRepo.findByName("USD")).thenReturn(List.of(storedCurrency));
	}

	@Test
	void RoomConversionTest() {
		RoomDto dto = new RoomDto();
		dto.setName(storedRoom.getName());
		dto.setDescription(storedRoom.getDescription());
		dto.setImageOne(storedRoom.getImageOne());
		dto.setImageTwo(storedRoom.getImageTwo());
		dto.setHotelId(storedRoom.getHotel().getId());

		Room roomEntity = new Room();

		when(roomRepo.save(any(Room.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Room room = (Room) args[0];
				roomEntity.setId(room.getId());
				roomEntity.setName(room.getName());
				roomEntity.setDescription(room.getDescription());
				roomEntity.setImageOne(room.getImageOne());
				roomEntity.setImageTwo(room.getImageTwo());
				roomEntity.setHotel(room.getHotel());
				return storedRoom;
			}
		});
		RoomDto dtoOut;
		assertThrows(BadInputException.class, () -> roomService.editEntity(dto));
		dto.setId(storedRoom.getId());
		assertThrows(BadInputException.class, () -> roomService.createEntity(dto));

		dtoOut = roomService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), roomEntity.getId());
		Assertions.assertEquals(dto.getName(), roomEntity.getName());
		Assertions.assertEquals(dto.getDescription(), roomEntity.getDescription());
		Assertions.assertNull(roomEntity.getImageOne());
		Assertions.assertNull(roomEntity.getImageTwo());
		Assertions.assertEquals(dto.getHotelId(), roomEntity.getHotel().getId());

		Assertions.assertEquals(dtoOut.getId(), storedRoom.getId());
		Assertions.assertEquals(dtoOut.getName(), storedRoom.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedRoom.getDescription());
		Assertions.assertEquals(dtoOut.getImageOne(), storedRoom.getImageOne());
		Assertions.assertEquals(dtoOut.getImageTwo(), storedRoom.getImageTwo());
		Assertions.assertEquals(dtoOut.getHotelId(), storedRoom.getHotel().getId());
	}

	@Test
	void HotelConversionTest() {
		HotelDto dto = new HotelDto();
		dto.setName(storedHotel.getName());
		dto.setDescription(storedHotel.getDescription());
		dto.setImageOne(storedHotel.getImageOne());
		dto.setImageTwo(storedHotel.getImageTwo());
		dto.setLocationId(storedHotel.getLocation().getId());

		Hotel hotelEntity = new Hotel();

		when(hotelRepo.save(any(Hotel.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Hotel hotel = (Hotel) args[0];
				hotelEntity.setId(hotel.getId());
				hotelEntity.setName(hotel.getName());
				hotelEntity.setDescription(hotel.getDescription());
				hotelEntity.setImageOne(hotel.getImageOne());
				hotelEntity.setImageTwo(hotel.getImageTwo());
				hotelEntity.setLocation(hotel.getLocation());
				return storedHotel;
			}
		});
		HotelDto dtoOut;
		assertThrows(BadInputException.class, () -> hotelService.editEntity(dto));
		dto.setId(storedHotel.getId());
		assertThrows(BadInputException.class, () -> hotelService.createEntity(dto));

		dtoOut = hotelService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), hotelEntity.getId());
		Assertions.assertEquals(dto.getName(), hotelEntity.getName());
		Assertions.assertEquals(dto.getDescription(), hotelEntity.getDescription());
		Assertions.assertNull(hotelEntity.getImageOne());
		Assertions.assertNull(hotelEntity.getImageTwo());
		Assertions.assertEquals(dto.getLocationId(), hotelEntity.getLocation().getId());

		Assertions.assertEquals(dtoOut.getId(), storedHotel.getId());
		Assertions.assertEquals(dtoOut.getName(), storedHotel.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedHotel.getDescription());
		Assertions.assertEquals(dtoOut.getImageOne(), storedHotel.getImageOne());
		Assertions.assertEquals(dtoOut.getImageTwo(), storedHotel.getImageTwo());
		Assertions.assertEquals(dtoOut.getLocationId(), storedHotel.getLocation().getId());
	}

	@Test
	void HotelServConversionTest() {
		HotelServiceDto dto = new HotelServiceDto();
		dto.setName(storedHotelServ.getName());
		dto.setDescription(storedHotelServ.getDescription());
		dto.setImageOne(storedHotelServ.getImageOne());
		dto.setImageTwo(storedHotelServ.getImageTwo());
		dto.setHotelId(storedHotelServ.getHotel().getId());

		HotelServ hotelServEntity = new HotelServ();

		when(hotelServRepo.save(any(HotelServ.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				HotelServ hotelServ = (HotelServ) args[0];
				hotelServEntity.setId(hotelServ.getId());
				hotelServEntity.setName(hotelServ.getName());
				hotelServEntity.setDescription(hotelServ.getDescription());
				hotelServEntity.setImageOne(hotelServ.getImageOne());
				hotelServEntity.setImageTwo(hotelServ.getImageTwo());
				hotelServEntity.setHotel(hotelServ.getHotel());
				return storedHotelServ;
			}
		});
		HotelServiceDto dtoOut;
		assertThrows(BadInputException.class, () -> hotelServService.editEntity(dto));
		dto.setId(storedHotelServ.getId());
		assertThrows(BadInputException.class, () -> hotelServService.createEntity(dto));

		dtoOut = hotelServService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), hotelServEntity.getId());
		Assertions.assertEquals(dto.getName(), hotelServEntity.getName());
		Assertions.assertEquals(dto.getDescription(), hotelServEntity.getDescription());
		Assertions.assertNull(hotelServEntity.getImageOne());
		Assertions.assertNull(hotelServEntity.getImageTwo());
		Assertions.assertEquals(dto.getHotelId(), hotelServEntity.getHotel().getId());

		Assertions.assertEquals(dtoOut.getId(), storedHotelServ.getId());
		Assertions.assertEquals(dtoOut.getName(), storedHotelServ.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedHotelServ.getDescription());
		Assertions.assertEquals(dtoOut.getImageOne(), storedHotelServ.getImageOne());
		Assertions.assertEquals(dtoOut.getImageTwo(), storedHotelServ.getImageTwo());
		Assertions.assertEquals(dtoOut.getHotelId(), storedHotelServ.getHotel().getId());
	}

	@Test
	void LocationConversionTest() {
		LocationDto dto = new LocationDto();
		dto.setName(storedLocation.getName());
		dto.setDescription(storedLocation.getDescription());
		dto.setImageOne(storedLocation.getImageOne());
		dto.setRegionId(storedLocation.getRegion().getId());

		Location locationEntity = new Location();

		when(locationRepo.save(any(Location.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Location location = (Location) args[0];
				locationEntity.setId(location.getId());
				locationEntity.setName(location.getName());
				locationEntity.setDescription(location.getDescription());
				locationEntity.setImageOne(location.getImageOne());
				locationEntity.setRegion(location.getRegion());
				return storedLocation;
			}
		});
		LocationDto dtoOut;
		assertThrows(BadInputException.class, () -> locationService.editEntity(dto));
		dto.setId(storedLocation.getId());
		assertThrows(BadInputException.class, () -> locationService.createEntity(dto));

		dtoOut = locationService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), locationEntity.getId());
		Assertions.assertEquals(dto.getName(), locationEntity.getName());
		Assertions.assertEquals(dto.getDescription(), locationEntity.getDescription());
		Assertions.assertNull(locationEntity.getImageOne());
		Assertions.assertEquals(dto.getRegionId(), locationEntity.getRegion().getId());

		Assertions.assertEquals(dtoOut.getId(), storedLocation.getId());
		Assertions.assertEquals(dtoOut.getName(), storedLocation.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedLocation.getDescription());
		Assertions.assertEquals(dtoOut.getImageOne(), storedLocation.getImageOne());
		Assertions.assertEquals(dtoOut.getRegionId(), storedLocation.getRegion().getId());
	}

	@Test
	void RegionConversionTest() {
		RegionDto dto = new RegionDto();
		dto.setName(storedRegion.getName());
		dto.setImageOne(storedRegion.getImageOne());
		dto.setCountryId(storedRegion.getCountry().getId());

		Region regionEntity = new Region();

		when(regionRepo.save(any(Region.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Region region = (Region) args[0];
				regionEntity.setId(region.getId());
				regionEntity.setName(region.getName());
				regionEntity.setImageOne(region.getImageOne());
				regionEntity.setCountry(region.getCountry());
				return storedRegion;
			}
		});
		RegionDto dtoOut;
		assertThrows(BadInputException.class, () -> regionService.editEntity(dto));
		dto.setId(storedRegion.getId());
		assertThrows(BadInputException.class, () -> regionService.createEntity(dto));

		dtoOut = regionService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), regionEntity.getId());
		Assertions.assertEquals(dto.getName(), regionEntity.getName());
		Assertions.assertNull(regionEntity.getImageOne());
		Assertions.assertEquals(dto.getCountryId(), regionEntity.getCountry().getId());

		Assertions.assertEquals(dtoOut.getId(), storedRegion.getId());
		Assertions.assertEquals(dtoOut.getName(), storedRegion.getName());
		Assertions.assertEquals(dtoOut.getImageOne(), storedRegion.getImageOne());
		Assertions.assertEquals(dtoOut.getCountryId(), storedRegion.getCountry().getId());
	}

	@Test
	void CountryConversionTest() {
		CountryDto dto = new CountryDto();
		dto.setName(storedCountry.getName());
		dto.setCurrency(storedCountry.getCurrency().getName());

		Country countryEntity = new Country();

		when(countryRepo.save(any(Country.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Country country = (Country) args[0];
				countryEntity.setId(country.getId());
				countryEntity.setName(country.getName());
				countryEntity.setCurrency(country.getCurrency());
				return storedCountry;
			}
		});
		CountryDto dtoOut;
		assertThrows(BadInputException.class, () -> countryService.editEntity(dto));
		dto.setId(storedCountry.getId());
		assertThrows(BadInputException.class, () -> countryService.createEntity(dto));

		dtoOut = countryService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), countryEntity.getId());
		Assertions.assertEquals(dto.getName(), countryEntity.getName());
		Assertions.assertEquals(dto.getCurrency(), countryEntity.getCurrency().getName());

		Assertions.assertEquals(dtoOut.getId(), storedCountry.getId());
		Assertions.assertEquals(dtoOut.getName(), storedCountry.getName());
		Assertions.assertEquals(dtoOut.getCurrency(), storedCountry.getCurrency().getName());
	}

	@Test
	void RegionServConversionTest() {
		RegionServiceDto dto = new RegionServiceDto();
		dto.setName(storedRegionServ.getName());
		dto.setDescription(storedRegionServ.getDescription());
		dto.setImageOne(storedRegionServ.getImageOne());
		dto.setImageTwo(storedRegionServ.getImageTwo());
		dto.setRegionId(storedRegionServ.getRegion().getId());

		RegionServ regionServEntity = new RegionServ();

		when(regionServRepo.save(any(RegionServ.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				RegionServ regionServ = (RegionServ) args[0];
				regionServEntity.setId(regionServ.getId());
				regionServEntity.setName(regionServ.getName());
				regionServEntity.setDescription(regionServ.getDescription());
				regionServEntity.setImageOne(regionServ.getImageOne());
				regionServEntity.setImageTwo(regionServ.getImageTwo());
				regionServEntity.setRegion(regionServ.getRegion());
				return storedRegionServ;
			}
		});
		RegionServiceDto dtoOut;
		assertThrows(BadInputException.class, () -> regionServService.editEntity(dto));
		dto.setId(storedRegionServ.getId());
		assertThrows(BadInputException.class, () -> regionServService.createEntity(dto));

		dtoOut = regionServService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), regionServEntity.getId());
		Assertions.assertEquals(dto.getName(), regionServEntity.getName());
		Assertions.assertEquals(dto.getDescription(), regionServEntity.getDescription());
		Assertions.assertNull(regionServEntity.getImageOne());
		Assertions.assertNull(regionServEntity.getImageTwo());
		Assertions.assertEquals(dto.getRegionId(), regionServEntity.getRegion().getId());

		Assertions.assertEquals(dtoOut.getId(), storedRegionServ.getId());
		Assertions.assertEquals(dtoOut.getName(), storedRegionServ.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedRegionServ.getDescription());
		Assertions.assertEquals(dtoOut.getImageOne(), storedRegionServ.getImageOne());
		Assertions.assertEquals(dtoOut.getImageTwo(), storedRegionServ.getImageTwo());
		Assertions.assertEquals(dtoOut.getRegionId(), storedRegionServ.getRegion().getId());
	}

	@Test
	void AirlineConversionTest() {
		AirlineDto dto = new AirlineDto();
		dto.setName(storedAirline.getName());
		dto.setDescription(storedAirline.getDescription());

		Airline airlineEntity = new Airline();

		when(airlineRepo.save(any(Airline.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Airline airline = (Airline) args[0];
				airlineEntity.setId(airline.getId());
				airlineEntity.setName(airline.getName());
				airlineEntity.setDescription(airline.getDescription());
				return storedAirline;
			}
		});
		AirlineDto dtoOut;
		assertThrows(BadInputException.class, () -> airlineService.editEntity(dto));
		dto.setId(storedAirline.getId());
		assertThrows(BadInputException.class, () -> airlineService.createEntity(dto));

		dtoOut = airlineService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), airlineEntity.getId());
		Assertions.assertEquals(dto.getName(), airlineEntity.getName());
		Assertions.assertEquals(dto.getDescription(), airlineEntity.getDescription());

		Assertions.assertEquals(dtoOut.getId(), storedAirline.getId());
		Assertions.assertEquals(dtoOut.getName(), storedAirline.getName());
		Assertions.assertEquals(dtoOut.getDescription(), storedAirline.getDescription());
	}

	@Test
	void AirportConversionTest() {
		AirportDto dto = new AirportDto();
		dto.setName(storedAirport.getName());
		dto.setCode(storedAirport.getCode());
		dto.setRegionId(storedAirport.getRegion().getId());

		Airport airportEntity = new Airport();

		when(airportRepo.save(any(Airport.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Airport airport = (Airport) args[0];
				airportEntity.setId(airport.getId());
				airportEntity.setName(airport.getName());
				airportEntity.setCode(airport.getCode());
				airportEntity.setRegion(airport.getRegion());
				return storedAirport;
			}
		});
		AirportDto dtoOut;
		assertThrows(BadInputException.class, () -> airportService.editEntity(dto));
		dto.setId(storedAirport.getId());
		assertThrows(BadInputException.class, () -> airportService.createEntity(dto));

		dtoOut = airportService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), airportEntity.getId());
		Assertions.assertEquals(dto.getName(), airportEntity.getName());
		Assertions.assertEquals(dto.getCode(), airportEntity.getCode());
		Assertions.assertEquals(dto.getRegionId(), airportEntity.getRegion().getId());

		Assertions.assertEquals(dtoOut.getId(), storedAirport.getId());
		Assertions.assertEquals(dtoOut.getName(), storedAirport.getName());
		Assertions.assertEquals(dtoOut.getCode(), storedAirport.getCode());
		Assertions.assertEquals(dtoOut.getRegionId(), storedAirport.getRegion().getId());
	}

	@Test
	void AccommodationTypeConversionTest() {
		AccommTypeDto dto = new AccommTypeDto();
		dto.setName(storedAccommodationType.getName());
		dto.setPaxNumber(storedAccommodationType.getPaxNumber());

		AccommodationType accommodationTypeEntity = new AccommodationType();

		when(accommodationTypeRepo.save(any(AccommodationType.class))).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				AccommodationType accommodationType = (AccommodationType) args[0];
				accommodationTypeEntity.setId(accommodationType.getId());
				accommodationTypeEntity.setName(accommodationType.getName());
				accommodationTypeEntity.setPaxNumber(accommodationType.getPaxNumber());
				return storedAccommodationType;
			}
		});
		AccommTypeDto dtoOut;
		assertThrows(BadInputException.class, () -> accommodationTypeService.editEntity(dto));
		dto.setId(storedAccommodationType.getId());
		assertThrows(BadInputException.class, () -> accommodationTypeService.createEntity(dto));

		dtoOut = accommodationTypeService.editEntity(dto);
		Assertions.assertEquals(dto.getId(), accommodationTypeEntity.getId());
		Assertions.assertEquals(dto.getName(), accommodationTypeEntity.getName());
		Assertions.assertEquals(dto.getPaxNumber(), accommodationTypeEntity.getPaxNumber());

		Assertions.assertEquals(dtoOut.getId(), storedAccommodationType.getId());
		Assertions.assertEquals(dtoOut.getName(), storedAccommodationType.getName());
		Assertions.assertEquals(dtoOut.getPaxNumber(), storedAccommodationType.getPaxNumber());
	}

}
