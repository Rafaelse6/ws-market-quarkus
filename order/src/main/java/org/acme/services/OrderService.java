package org.acme.services;

import org.acme.client.CustomerClient;
import org.acme.client.ProductClient;
import org.acme.dto.CustomerDTO;
import org.acme.dto.OrderDTO;
import org.acme.dto.ProductDTO;
import org.acme.entity.OrderEntity;
import org.acme.repository.OrderRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    private OrderRepository orderRepository;

    @Inject
    @RestClient
    private CustomerClient customerClient;

    @Inject
    @RestClient
    private ProductClient productClient;

    public List<OrderDTO> getAllOrders(){

        List<OrderDTO> orders = new ArrayList<>();

        orderRepository.findAll().stream().forEach(item->{
            orders.add(mapEntityToDTO(item));
        });

        return orders;
    }

    public void saveNewOrder(OrderDTO orderDTO){

        CustomerDTO customerDTO = customerClient.getCustomerById(orderDTO.getCustomerId());

        if(customerDTO.getName().equals(orderDTO.getCustomerName())
                && productClient.getProductById(orderDTO.getProductId()) != null){
            orderRepository.persist(mapDTOToEntity(orderDTO));
        } else {
            throw new NotFoundException();
        }

    }

    private OrderDTO mapEntityToDTO(OrderEntity item) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setCustomerId(item.getCustomerId());
        orderDTO.setCustomerName(item.getCustomerName());
        orderDTO.setProductId(item.getProductId());
        orderDTO.setOrderValue(item.getOrderValue());

        return orderDTO;
    }

    private OrderEntity mapDTOToEntity(OrderDTO item) {

        OrderEntity order = new OrderEntity();

        order.setCustomerId(item.getCustomerId());
        order.setCustomerName(item.getCustomerName());
        order.setProductId(item.getProductId());
        order.setOrderValue(item.getOrderValue());

        return order;
    }

}

