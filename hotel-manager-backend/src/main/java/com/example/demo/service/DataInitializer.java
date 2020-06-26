package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.github.javafaker.App;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Slf4j
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

	private ContactRepository contactRepository;
	private UserRepository userRepository;
	private RoomRepository roomRepository;
	private ReservationRepository reservationRepository;
	private BillRepository billRepository;
	private PersonRepository personRepository;
	private UserRoleRepository userRoleRepository;
	private TagRepository tagRepository;
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	public DataInitializer(ContactRepository contactRepository, UserRepository userRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, BillRepository billRepository, PersonRepository personRepository, UserRoleRepository userRoleRepository, TagRepository tagRepository, RoomTypeRepository roomTypeRepository) {
		this.contactRepository = contactRepository;
		this.userRepository = userRepository;
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
		this.billRepository = billRepository;
		this.personRepository = personRepository;
		this.userRoleRepository = userRoleRepository;
		this.tagRepository = tagRepository;
		this.roomTypeRepository = roomTypeRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Data initialization started");
		Faker faker = new Faker(new Locale("pl","PL"));
		Random random = new Random();
        var userRole = userRoleRepository.findUserRoleByName("USER").get();
        var userRole2 = userRoleRepository.findUserRoleByName("ADMIN").get();

		var types = Stream.of("Apartament", "Kawalerka", "Bieda Edition", "Duży", "Luksus", "Ekonomiczny")
				.map(RoomType::new)
				.collect(Collectors.toList());

		final var typesFromDb = roomTypeRepository.saveAll(types);

		var tags = IntStream.range(0, 100)
				.mapToObj(value -> faker.pokemon().name())
				.distinct()
				.map(val -> val.length() > 20 ? val.substring(0, 19) : val)
				.map(Tag::new)
				.collect(Collectors.toList());

		final var tagsFromDb = tagRepository.saveAll(tags);

		var areas = List.of(20, 30, 40, 50);

		List<AppUser> appUsers = new LinkedList<>();
		IntStream.range(0, 70).forEach(value -> {
			var witcherName = faker.lordOfTheRings()
					.character()
					.replaceAll("\\s+", "");

			if (witcherName.length() > 30)
				witcherName = witcherName.substring(0, 30);

			var witcherSurname = faker.lordOfTheRings()
					.character()
					.replaceAll("\\s+", "");

			if (witcherSurname.length() > 30)
				witcherSurname = witcherSurname.substring(0, 30);

			var contact = new Contact("792343278");

			var person = Person.builder()
					.lastName(witcherName)
					.firstName(witcherSurname)
					.contactSet(Set.of(contact))
					.build();

			contact.setPerson(person);

			var appUser = AppUser.builder()
					.role(Set.of(userRole))
					.password("{noop}adminadmin")
					.email(witcherName + "@wp.pl")
					.person(person)
					.build();

			person.setAppUser(appUser);

			var usersCount = appUsers.stream()
					.filter(val -> val.getEmail().equals(appUser.getEmail()))
					.count();

			if (usersCount == 0)
				appUsers.add(appUser);
		});
		userRepository.saveAll(appUsers);


		personRepository.findAll().forEach(person -> IntStream.range(0, 3).forEach(x -> {
			var type = typesFromDb.get(random.nextInt(typesFromDb.size()));
			var tag = tagsFromDb.get(random.nextInt(tags.size()));
			var area = areas.get(random.nextInt(areas.size()));
			double price = random.nextInt(200) + 1;
			var amount = random.nextInt(4) + 1;
			var roomNumber = Integer.toString(random.nextInt(300));
			var quote = faker.yoda().quote();
			quote = quote.length() >= 200 ? quote.substring(0, 200) : quote;
			quote = quote.length() <= 24 ? quote + quote : quote;

			Room room = Room.builder()
					.roomTypeSet(Set.of(type))
					.tagSet(Set.of(tag))
					.area(area)
					.description(quote)
					.price(price)
					.personAmount(amount)
					.roomNumber(roomNumber)
					.build();
			roomRepository.save(room);

			var reservation = Reservation.builder()
					.room(room)
					.fromDate(LocalDate.now().plusDays(random.nextInt(14)))
					.toDate(LocalDate.now().plusDays(random.nextInt(14) + 14))
					.build();

			long number = (long)(random.nextGaussian() * 8) + 15;
			if(number < 0 || number > 32) number = random.nextInt(32);
			var bill = Bill.builder()
					.price(price * ChronoUnit.DAYS.between(reservation.getFromDate(), reservation.getToDate()))
					.tenant(person)
					.reservation(reservation)
//					.date(LocalDate.now().minusDays(random.nextInt(30)))
					.date(LocalDate.now().minusDays(number))
					.build();
			reservation.setBill(bill);

			billRepository.save(bill);

		}));


	}

}
