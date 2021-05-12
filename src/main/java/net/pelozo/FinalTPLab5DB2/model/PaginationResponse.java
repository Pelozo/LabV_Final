package net.pelozo.FinalTPLab5DB2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> implements Serializable {

    private List<T> response;
    private int totalResponse;
    private double totalElements;
}
