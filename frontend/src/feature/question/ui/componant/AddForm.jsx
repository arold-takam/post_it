import * as api from '../../infrastructure/questionService.js';
import {useState} from "react";

export default function AddForm({displayed ,setDisplayed}) {
    const [formData, setFormData] = useState({
        content: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            if (formData) {
                await api.create(formData);
                alert("Question added successfully !");
                window.location.reload();
            }
        }catch (e) {
            console.error("Error: ", e);
        }
    }

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name] : e.target.value
        })
    }

    const hiddenAdd = () =>{
        setDisplayed(false);
    }

    return (
        <section className={`${displayed ? "add ": "hiddenAdd"}`}>
            <div className="up">
                <button type="button" className="back" onClick={hiddenAdd}>BACK</button>
            </div>
            <form className="formAdd" onSubmit={handleSubmit}>
                <label htmlFor="content">Enter the new question</label>
                <textarea name="content" id="content" className="content" value={formData.content} onChange={handleChange} required></textarea>
                <button type="submit" className="addBtn">ADD IT</button>
            </form>
        </section>
    );
}