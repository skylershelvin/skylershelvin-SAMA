package org.skylershelvin.SAMABackened.model.dao;

import org.skylershelvin.SAMABackened.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDAO extends ListCrudRepository<Address, Long> {

    List<Address> findByUser_Id(Long id);

}
