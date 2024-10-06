package com.insert.recruitment.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class ChangeStatusCommand {
  @NotNull private Boolean isChangeStatusOrder;
}
