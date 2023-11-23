import { FormGroup , ValidatorFn, ValidationErrors, AbstractControl } from "@angular/forms";

export class ConfirmPasswordValidator{
    static matchPassword(): ValidatorFn {
        return (control : AbstractControl) : {[key: string]: boolean} | null => {
            const password = control.get('password');
            let confirmPassword = control.get('confirmPassword');
            if(password?.value !== confirmPassword?.value){
                return {'passwordMismatch' : true};
            }
            return null;
        };
    }
}