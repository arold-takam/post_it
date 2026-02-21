import * as api from '../infrastructure/questionService.js';
import {useEffect, useState} from "react";
import {questionEntity} from "../domaine/questionEntity.js";

export default function useQCreate(q){
    const [question, setQuestion] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const qData = questionEntity(q.content);

        api.create(qData)
            .then((response) => {
                setQuestion(response.data);
            })
            .catch(error => console.log("API ERROR: ", error))
            .finally(() => setLoading(false));
    }, [q]);

    return {
        question,
        loading
    }
}