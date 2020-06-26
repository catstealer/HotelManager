package com.example.demo;

import com.example.demo.entity.Person;
import com.example.demo.model.ContactModel;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.PersonRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.example.demo.specification.SpecParams.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private Gson gson = new Gson();

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContactRepository contactRepository;

    @Test
    @WithMockUser
    public void shouldCreateContact() throws Exception {
        Person person = new Person();
        person.setFirstName("Dawid");
        person.setLastName("Witasezk");
        person = personRepository.save(person);
        ContactModel contactModel = new ContactModel();
        contactModel.setPhoneNumber("792343278");
        contactModel.setPersonId(person.getId());
        MockHttpServletRequestBuilder builder = post("/contact")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(contactModel));

          mockMvc.perform(builder)
                    .andDo(print())
                    .andExpect(status().isCreated())
                     .andReturn()
                     .getRequest()
                     .getContentAsString();
    }

    @Test
    @WithMockUser
    public void shouldSearchForRooms() throws Exception {
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
        mockMvc.perform(
                get("/rooms")
                .params(multiValueMap)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void shouldThrowExceptionsForRooms() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put(
                FROM_DATE.toString(),
                List.of("2019-05-03")
        );
        multiValueMap.put(
                TO_DATE.toString(),
                List.of("2020-05-08")
        );
        multiValueMap.put(
                AREA.toString(),
                List.of("hjk")
        );
        multiValueMap.put(
                PERSON_AMOUNT.toString(),
                List.of("1.05")
        );
        mockMvc.perform(
                get("/rooms")
                        .params(multiValueMap)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }


}
