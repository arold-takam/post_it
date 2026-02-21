import {validateQuestion} from "./questionValidation.js";

export function questionEntity(content){
    const errorMsg = validateQuestion(content);
    if(errorMsg){
        throw new Error(errorMsg.join(", -"));
    }

    return content;
}