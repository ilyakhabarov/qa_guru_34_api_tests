package models;

import lombok.Data;

@Data
public class CreateNewUserResponseModel {
    Integer id;
    String name, job, createdAt, test;
}
