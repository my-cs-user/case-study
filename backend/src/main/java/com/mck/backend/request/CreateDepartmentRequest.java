package com.mck.backend.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDepartmentRequest(@NotNull @Size(max = 255) String name) {

}
