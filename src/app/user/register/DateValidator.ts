import { FormControl } from "@angular/forms";

export class DateValidator{
    static checkDate(checkDate : FormControl) : {'dateOfBirth': true} | null {
        let value = "" + checkDate.value;
        let valueDate: Date = new Date(value);
        
        const thirteenYearsAgo = new Date();
        thirteenYearsAgo.setFullYear(thirteenYearsAgo.getFullYear() - 13);
        if (valueDate > thirteenYearsAgo) {
            return {'dateOfBirth': true};
        }
        return null;
    }
}