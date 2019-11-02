package com.sguessou.app.ws.mobileappws.service.impl;

import com.sguessou.app.ws.mobileappws.io.entity.AddressEntity;
import com.sguessou.app.ws.mobileappws.io.entity.UserEntity;
import com.sguessou.app.ws.mobileappws.io.repositories.AddressRepository;
import com.sguessou.app.ws.mobileappws.io.repositories.UserRepository;
import com.sguessou.app.ws.mobileappws.service.AddressService;
import com.sguessou.app.ws.mobileappws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity:addresses)
        {
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if (addressEntity != null)
        {
            returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
        }

        return returnValue;
    }
}
