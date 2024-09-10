package com.ltrlabs.system.api;

import com.ltrlabs.auth.service.AuthService;
import com.ltrlabs.configuration.ResourceConfig;
import com.ltrlabs.customer.service.CustomerService;
import com.ltrlabs.order.service.OrderService;
import com.ltrlabs.order.service.dto.ItemDTO;
import com.ltrlabs.order.service.dto.OrderDTO;
import com.ltrlabs.order.service.exception.ItemNotValidException;
import com.ltrlabs.order.service.exception.NoOrderFoundException;
import com.ltrlabs.product.service.ProductService;
import com.ltrlabs.product.service.dto.ProductDTO;
import com.ltrlabs.product.service.exception.ProductNotValidException;
import com.ltrlabs.user.service.UserService;
import com.ltrlabs.user.service.dto.UserDTO;
import com.ltrlabs.user.service.exception.InvalidPasswordException;
import com.ltrlabs.user.service.exception.NoSuchUserException;
import com.ltrlabs.user.service.exception.UserExistException;
import com.ltrlabs.user.service.exception.UserNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SystemAPI {

    private final OrderService orderService;
    private final AuthService authService;
    private final CustomerService customerService;
    private final UserService userService;
    private final ProductService productService;
    private final ResourceConfig resourceConfig;

    public Response<UUID> registerUser(UserDTO user) {
        UUID userID;
        Response<UUID> response = new Response<>();

        try {
            userID = authService.registerUser(user);
            response.setValue(userID);
            response.setError(false);
        } catch (UserExistException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.USER_EXIST);
        } catch (InvalidPasswordException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.PASSWORD_INVALID);
        } catch (UserNotValidException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.USER_NOT_VALID);
        }
        return response;
    }

    public Response<UUID> loginUser(String email, String password, boolean isCustomer) {
        UUID userID;
        Response<UUID> response = new Response<>();

        try {
            userID = authService.logInUser(email, password, isCustomer);
            response.setValue(userID);
            response.setError(false);
        } catch (NoSuchUserException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.NO_USER);
        } catch (InvalidPasswordException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.PASSWORD_INVALID);
        }
        return response;
    }

    public Response<UUID> addProduct(ProductDTO product) {
        Response<UUID> response = new Response<>();
        try {
            UUID productId = productService.addProduct(product);
            response.setError(false);
            response.setValue(productId);
        } catch (ProductNotValidException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.PRODUCT_NOT_VALID);
        }
        return response;
    }

    public Response<UUID> createOrderForCustomer(UUID customerID) {
        Response<UUID> response = new Response<>();
        if (!customerService.isCustomerRegistered(customerID)) {
            response.setError(true);
            response.setErrorCode(ErrorCode.NO_USER);
            return response;
        } else {
            UUID orderID = orderService.createNewOrderForCustomer(customerID);
            response.setError(false);
            response.setValue(orderID);
        }
        return response;
    }

    public Response<UUID> addItemToOrder(UUID orderID, ItemDTO itemDTO) {
        Response<UUID> response = new Response<>();
        try {
            UUID itemID = orderService.addItemToOrder(orderID, itemDTO);
            response.setError(false);
            response.setValue(itemID);
        } catch (ItemNotValidException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.ITEM_NOT_VALID);
        } catch (NoOrderFoundException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.NO_ORDER);
        } catch (ProductNotValidException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.PRODUCT_NOT_VALID);
        }
        return response;
    }

    public void removeItemFromOrder(UUID itemID, UUID orderId) {
        try {
            orderService.removeItemFromOrder(itemID, orderId);
        } catch (NoOrderFoundException ex) {

        }
    }

    public Response<OrderDTO> getOrder(UUID orderId) {
        Response<OrderDTO> response = new Response<>();
        try {
            OrderDTO order = orderService.getOrder(orderId);
            response.setError(false);
        } catch (NoOrderFoundException ex) {
            response.setError(true);
            response.setErrorCode(ErrorCode.NO_ORDER);
        }
        return response;
    }

    public Response<List<OrderDTO>> getCustomerOrders(UUID customerID) {
        Response<List<OrderDTO>> response = new Response<>();
        response.setError(false);
        response.setValue(orderService.getCustomerOrders(customerID));
        return response;
    }
}
