package com.mck.backend.app.request;

import jakarta.validation.constraints.Size;

public record UpdateCourseRequest(@Size(max = 255) String name) {

}
