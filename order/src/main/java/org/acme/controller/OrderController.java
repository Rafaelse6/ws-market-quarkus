package org.acme.controller;

import org.acme.dto.OrderDTO;
import org.acme.services.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/orders")
public class OrderController {

    @Inject
    private OrderService orderService;

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @POST
    @Transactional
    public Response saveNewOrder(OrderDTO order){
        try {
            orderService.saveNewOrder(order);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.serverError().build();
        }
    }

}
