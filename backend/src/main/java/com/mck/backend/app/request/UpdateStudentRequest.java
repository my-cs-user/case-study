package com.mck.backend.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateStudentRequest(@Size(max = 255) String name,

                                   @Size(max = 255) String surname,

                                   @Email @Size(max = 255) String email,

                                   @Size(max = 255) String phone,

                                   List<@Positive Long> courses) {

}
