package com.edu.trip.data_view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDataView {
    private String token;
    private String username;

    private String role;
}
