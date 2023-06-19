package w3schools.dto;

import lombok.Data;

@Data
public class Customer {
    private String customerID;
    private String customerName;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
