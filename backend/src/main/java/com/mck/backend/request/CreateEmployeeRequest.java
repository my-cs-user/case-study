package com.mck.backend.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(@NotNull @Size(max = 255) String name,

                                    @NotNull @Size(max = 255) String surname,

                                    @NotNull Integer salary,

                                    @NotNull @Size(max = 255) String email,

                                    @NotNull @Size(max = 255) String phone,

                                    @NotNull @Positive Long department) {

}
