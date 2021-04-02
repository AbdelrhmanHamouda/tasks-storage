package com.pomos.modules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateSummaryCommand {
    @NotNull
    private Long id;
    @NotBlank
    private String summary;
}
