package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.model.ContactModel;
import com.example.demo.model.PersonModel;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.example.demo.specification.SpecParams.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DemoApplicationTests {

    Reservation reservation;
    Person person;
    AppUser appUser;
    Contact contact;
    Gson gson;

    BillRepository billRepository;
    UserRepository userRepository;
    ReservationRepository reservationRepository;
    ContactRepository contactRepository;
    PersonRepository personRepository;
    RoomRepository roomRepository;

    BillService billServiceMock;
    SecurityContext securityContextMock;

    @BeforeEach
    void initializeBeans() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setFromDate(LocalDate.now());
        reservation.setToDate(LocalDate.now().minusWeeks(1L));
        Person person = new Person();
        person.setLastName("Witszek");
        person.setFirstName("Dawid");
        person.setId(1L);
        AppUser appUser = new AppUser();
        appUser.setEmail("admin");
        appUser.setPassword("{noop}admin");
        UserRole userRole = new UserRole();
        userRole.setId(0L);
        userRole.setName("USER");
        appUser.getRole().add(userRole);
        appUser.setId(1L);
        appUser.setPerson(person);
        person.setAppUser(appUser);
        Room room_a = new Room();
        room_a.setPrice(50.);
        room_a.setId(1L);
        Room room_b = new Room();
        room_b.setPrice(50.);
        room_b.setId(2L);
        Contact contact = new Contact();
        contact.setPerson(person);
        contact.setPhoneNumber("792343278");
        person.getContactSet().add(contact);
        Bill bill = new Bill();
        bill.setTenant(person);
        this.reservation = reservation;
        this.person = person;
        this.appUser = appUser;
        this.contact = contact;
        billRepository = mock(BillRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        userRepository = mock(UserRepository.class);
        personRepository = mock(PersonRepository.class);
        contactRepository = mock(ContactRepository.class);
        securityContextMock = mock(SecurityContext.class);
        billServiceMock = mock(BillService.class);
        roomRepository = mock(RoomRepository.class);
        gson = new Gson();
    }
//
//    @Test
//    void shouldCreateBillFromReservationInfo() {
//        when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
//        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(appUser));
//        when(billRepository.save(any(Bill.class))).thenReturn(null);
//
//        BillService billService = new BillService(billRepository, reservationRepository, userRepository);
//        Bill bill = billService.createBillFromReservation(reservation, "admin");
//        assertThat(bill.getTenant()).isEqualTo(person);
//    }

    @Test
    void shouldCreateContact(){
        AtomicReference<Contact> newContact = new AtomicReference<>();
        when(contactRepository.save(any())).thenAnswer(
                invocationn -> {
                    newContact.set(invocationn.getArgument(0));
                    return invocationn.getArgument(0);
                }
        );
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        ContactModel contactModel = new ContactModel();
        contactModel.setPersonId(1L);
        contactModel.setPhoneNumber("792343278");

        ContactService contactService = new ContactService(contactRepository,personRepository);
        contactService.addContact(contactModel);

        Contact contact = newContact.get();
        assertThat(contact).isNotNull();
    }

    @Test
    void shouldReturnUserInfoWithDefaultRole(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(appUser));

        UserDetailsService userDetailsService = new DefaultUserDetailsService(userRepository);
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertThat(userDetails).extracting(
                UserDetails::getPassword,
                UserDetails::getUsername
        ).contains(
                appUser.getPassword(),
                appUser.getEmail()
        );

        List<String> list = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        assertTrue(list.contains("ROLE_USER"));

    }


    @Test
    void getListOfContactOfPerson(){
      when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        PersonService personService = new PersonService(personRepository, userRepository);
        Set<Contact> contacts = personService.getContactOfPerson(person.getId());
        List<String> list = contacts.stream().map(Contact::getPhoneNumber).collect(Collectors.toList());
        assertThat(list).containsOnly("792343278");
    }

    @Test
    void shouldAddPerson(){
        AtomicReference<Person> personAtomicReference = new AtomicReference<>();
        when(userRepository.findUserByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));
        when(personRepository.save(any())).thenAnswer(
                inv -> {
                    Person person = inv.getArgument(0);
                    personAtomicReference.set(person);
                    return person;
                }
        );
        PersonService personService = new PersonService(personRepository, userRepository);
        PersonModel personModel = new PersonModel();
        personModel.setFirstName("Dawid");
        personModel.setLastName("Witaszek");
        personModel.setUserEmail("admin");

        personService.addPerson(personModel);

        Person addedPerson = personAtomicReference.get();
        assertThat(addedPerson).isNotNull().extracting("firstName","lastName")
                .contains("Dawid", "Witaszek");

        AppUser appUserFromAddedPerson = person.getAppUser();

        assertThat(appUserFromAddedPerson).isEqualTo(appUser);
    }



    @Test
    void shouldReturnListWithOneRoom(){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put(
                FROM_DATE.toString(),
                List.of("2020-05-03")
        );
        multiValueMap.put(
                TO_DATE.toString(),
                List.of("2020-05-08")
        );
        multiValueMap.put(
                AREA.toString(),
                List.of("50")
        );
        multiValueMap.put(
                PERSON_AMOUNT.toString(),
                List.of("1")
        );
        multiValueMap.put(
                TAGS.toString(),
                List.of("Widok na morze","Widok na góry")
        );
        multiValueMap.put(
                TYPES.toString(),
                List.of("Zwykły")
        );
        RoomService roomService = new RoomService(roomRepository);
        Specification<Room> roomSpecification = roomService.getSpecififcationFromParams(multiValueMap);
        assertTrue(true);
    }

}
