package net.pelozo.FinalTPLab5DB2.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pelozo.FinalTPLab5DB2.model.Client;
import net.pelozo.FinalTPLab5DB2.model.Residence;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private Long id;

    private String username;

    private String email;

    private String dni;

    private String firstName;

    private String lastName;

    private List<Residence> residences;

    public static ClientDto from(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .username(client.getUsername())
                .dni(client.getDni())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
    }


}