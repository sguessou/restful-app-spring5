package com.sguessou.app.ws.mobileappws.ui.controller;

import com.sguessou.app.ws.mobileappws.exceptions.UserServiceException;
import com.sguessou.app.ws.mobileappws.service.AddressService;
import com.sguessou.app.ws.mobileappws.service.UserService;
import com.sguessou.app.ws.mobileappws.shared.dto.AddressDto;
import com.sguessou.app.ws.mobileappws.shared.dto.UserDto;
import com.sguessou.app.ws.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.sguessou.app.ws.mobileappws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(
            path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
            )
    public UserRest getUser(@PathVariable String id) {

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(
            path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updateUser = userService.updateUser(id, userDto);

        BeanUtils.copyProperties(updateUser, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public OperationStatusModel deleteUser(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(
            path = "/{id}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public List<AddressesRest> getUserAddresses(@PathVariable String id) {

        List<AddressesRest> returnValue = new ArrayList<>();

        List<AddressDto> addressesDto = addressService.getAddresses(id);

        if (addressesDto != null && !addressesDto.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            returnValue = new ModelMapper().map(addressesDto, listType);
        }


        return returnValue;
    }

    @GetMapping(
            path = "/{userId}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public AddressesRest getUserAddress(@PathVariable String addressId) {

        AddressDto addressesDto = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(addressesDto, AddressesRest.class);
    }


}
