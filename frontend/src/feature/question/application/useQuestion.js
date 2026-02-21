import * as api from '../infrastructure/questionService.js';
import {useEffect, useState} from "react";

export default function UseQuestion(){
    const [list, setList] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        api.get()
            .then(data => {
                // data est déjà le tableau grâce au service
                setList(data);
            })
            .catch(error => {
                console.error("API Error: ", error);
                setList([]);
            })
            .finally(() => {
                setLoading(false); // <--- CRUCIAL pour que le test s'arrête
            });
    }, []);

    return { list, loading };
}