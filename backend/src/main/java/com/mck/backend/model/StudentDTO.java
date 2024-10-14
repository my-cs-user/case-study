package com.mck.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {

	@Positive
	private Long id;

	@NotNull
	@Size(max = 255)
	private String name;

	@NotNull
	@Size(max = 255)
	private String surname;

	@NotNull
	@Size(max = 255)
	private String email;

	@NotNull
	@Size(max = 255)
	private String phone;

	@NotNull
	private List<@Positive Long> courses;

}
