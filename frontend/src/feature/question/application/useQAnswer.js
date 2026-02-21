import * as api from '../infrastructure/questionService.js';
import {useEffect, useState} from "react";

export default function useQAnswer(id){
    const [question, setQuestion] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!id) return;

        api.answer(id)
            .then(response => {
                setQuestion(response.data);
            })
            .catch(error => console.error("API error", error))
            .finally(()=>setLoading(false));
    }, [id]);

    return {
        question,
        loading,
    }
}