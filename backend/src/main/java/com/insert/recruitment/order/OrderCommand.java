package com.insert.recruitment.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class OrderCommand {
  private String nameOfOrder;
  @NotNull private BigDecimal orderPrice;
  private String description;
  @NotNull @NotBlank private String receiver;
  @NotNull @NotBlank private String sender;
}
