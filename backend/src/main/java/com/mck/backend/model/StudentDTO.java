package com.mck.backend.model;

import java.util.List;

public record StudentDTO(Long id,
                         String name,
                         String surname,
                         String email,
                         String phone,
                         List<Long> courses
) {

}

