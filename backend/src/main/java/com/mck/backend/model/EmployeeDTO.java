package com.mck.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDTO {

  private Long id;

  @NotNull
  @Size(max = 255)
  private String name;

  @NotNull
  @Size(max = 255)
  private String surname;

  @NotNull
  private Integer salary;

  @NotNull
  @Size(max = 255)
  private String email;

  @NotNull
  @Size(max = 255)
  private String phone;

  @NotNull
  private Long department;

}
