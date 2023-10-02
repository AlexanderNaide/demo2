package com.example.demo.model;

import jakarta.persistence.Id;

public record Supplier(
        @Id
        Integer id
) {
}
