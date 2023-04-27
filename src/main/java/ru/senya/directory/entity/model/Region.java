package ru.senya.directory.entity.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @Schema(description = "Идентификатор")
    private Integer id;

    @Schema(description = "Наименование")
    private String name;

    @Schema(description = "Сокращенное наименование")
    private String shortName;
}
