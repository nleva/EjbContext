package ru.sendto.ejb.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sendto.dto.Dto;

@Data
@EqualsAndHashCode(callSuper = false) 
public class ErrorDto extends Dto {
	String error;
}