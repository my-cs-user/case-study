package com.mck.backend.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateEmployeeRequest(@Size(max = 255) String name,

                                    @Size(max = 255) String surname,

                                    Integer salary,

                                    @Size(max = 255) String email,

                                    @Size(max = 255) String phone,

                                    @Positive Long department) {

}
