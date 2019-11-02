package com.sguessou.app.ws.mobileappws.service;

import com.sguessou.app.ws.mobileappws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(String userId);
    AddressDto getAddress(String addressId);
}
