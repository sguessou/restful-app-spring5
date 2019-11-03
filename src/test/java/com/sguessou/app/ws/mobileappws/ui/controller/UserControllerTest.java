package com.sguessou.app.ws.mobileappws.ui.controller;

import com.sguessou.app.ws.mobileappws.service.impl.UserServiceImpl;
import com.sguessou.app.ws.mobileappws.shared.dto.AddressDto;
import com.sguessou.app.ws.mobileappws.shared.dto.UserDto;
import com.sguessou.app.ws.mobileappws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    final String USER_ID = "893289hjkedwsuiuiy78";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setUserId(USER_ID);
        userDto.setFirstName("Joe");
        userDto.setPassword("12343214");
        userDto.setEmail("test@test.com");
        userDto.setAddresses(getAddressesDto());
    }

    @Test
    void testGetUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getEmail(), userRest.getEmail());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
    }

    private List<AddressDto> getAddressesDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("Turkku");
        addressDto.setCountry("Finland");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreetName("789 katu nimi");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("Turkku");
        billingAddressDto.setCountry("Finland");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("790 katu nimi");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;
    }
}