package com.mck.backend.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateStudentRequest(@NotNull @Size(max = 255) String name,

		@NotNull @Size(max = 255) String surname,

		@NotNull @Email @Size(max = 255) String email,

		@NotNull @Size(max = 255) String phone,

		@NotNull List<@Positive Long> courses) {
}
