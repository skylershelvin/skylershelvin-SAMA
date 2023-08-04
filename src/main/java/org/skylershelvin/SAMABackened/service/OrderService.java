package org.skylershelvin.SAMABackened.service;

import org.skylershelvin.SAMABackened.model.LocalUser;
import org.skylershelvin.SAMABackened.model.UserOrder;
import org.skylershelvin.SAMABackened.model.dao.UserOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {

    private UserOrderDAO userOrderDAO;


    public OrderService(UserOrderDAO userOrderDAO) {
        this.userOrderDAO = userOrderDAO;
    }

    public List<UserOrder> getOrders(LocalUser user){
        return userOrderDAO.findByUser(user);
    }
}
