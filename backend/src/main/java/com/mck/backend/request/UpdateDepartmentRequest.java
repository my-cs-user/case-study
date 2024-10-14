package com.mck.backend.request;

import jakarta.validation.constraints.Size;

public record UpdateDepartmentRequest(@Size(max = 255) String name) {

}
