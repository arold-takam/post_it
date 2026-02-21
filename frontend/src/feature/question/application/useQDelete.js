import * as api from '../infrastructure/questionService.js';
import {useEffect, useState} from "react";

export default function useQDelete(id){
    const [deleted, setDeleted] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(()=>{
        if(!id) return;

        api.deleteQuestion(id)
            .then(response => {
                setDeleted(response.data);
            })
            .catch(error => console.error("API error: ", error))
            .then(() => setLoading(false));
    });

    return {
        deleted,
        loading
    }
}