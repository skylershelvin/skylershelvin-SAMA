package org.skylershelvin.SAMABackened.model.dao;

import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.UserOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserOrderDAO extends ListCrudRepository <UserOrder, Long> {
    List<UserOrder> findByUser(LocalUser user);
}
