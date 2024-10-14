package com.mck.backend.model;

public record EmployeeDTO(Long id,
                          String name,
                          String surname,
                          Integer salary,
                          String email,
                          String phone,
                          Long department
) {

}

