package com.infyfacebook.user.utility;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AgeConstraintValidator implements ConstraintValidator<AgeConstraint, LocalDate>{

	@Override
	public void initialize(AgeConstraint ageConstraint) {
		
	}
	
	@Override
	public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
		if(dateOfBirth == null) {
			return true;
		}
		LocalDate currentDate = LocalDate.now();
		if(Period.between(dateOfBirth, currentDate).getYears() >= 13) {
			return true;
		}
		return false;
	}

}
