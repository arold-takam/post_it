import * as api from '../infrastructure/questionService.js';
import {useEffect, useState} from "react";
import {questionEntity} from "../domaine/questionEntity.js";

export default function useQUpdate(id, updateData){
    const [question, setQuestion] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!id) return;
        const quest = questionEntity(updateData.content);

        api.update(id, quest)
            .then(response => {
                setQuestion(response.data);
            })
            .catch(error => console.error("API error: ", error))
            .finally(() => setLoading(false));
    }, [id, updateData]);

    return {
        question,
        loading
    }
}