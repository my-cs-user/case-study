package com.mck.backend.app.request;

import jakarta.validation.constraints.Size;

public record UpdateDepartmentRequest(@Size(max = 255) String name) {

}
