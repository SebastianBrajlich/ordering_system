package com.ltrlabs.configuration;

import com.ltrlabs.utils.ResourceManager;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Optional;

@Component
@Getter
public class ResourceConfig {

    @Value("${storage_dir_name}")
    private String storageDirName;
    @Value("${customers_file_name}")
    private String customersFileName;
    @Value("${orders_file_name}")
    private String ordersFileName;
    @Value("${products_file_name}")
    private String productsFileName;
    @Value("${order_items_file_name}")
    private String itemsFileName;
    @Value("${address_file_name}")
    private String addressFileName;
    @Value("${users_file_name}")
    private String usersFileName;

    public String getCustomerFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + customersFileName;
    }

    public String getOrdersFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + ordersFileName;
    }

    public String getProductsFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + productsFileName;
    }

    public String getItemsFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + itemsFileName;
    }

    public String getUsersFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + usersFileName;
    }

    public String getAddressFilePath() {
        return System.getProperty("user.home") + "/" + storageDirName + "/" + addressFileName;
    }

    @PostConstruct
    private void resourceInit() {
        createStorage();
    }

    private void createStorage() {
        Optional<Path> directory = ResourceManager.createDirectory(getStorageDirName());
        if (directory.isPresent()) {
            Path path = directory.get();
            ResourceManager.createFile(path, getCustomersFileName());
            ResourceManager.createFile(path, getOrdersFileName());
            ResourceManager.createFile(path, getProductsFileName());
            ResourceManager.createFile(path, getItemsFileName());
            ResourceManager.createFile(path, getUsersFileName());

        }
    }
}
