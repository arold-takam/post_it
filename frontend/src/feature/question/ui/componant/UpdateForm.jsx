import delIcon from "../../../../assets/delete.png";
import {useEffect, useState} from "react";
import useQDetails from "../../application/useQDetails.js";
import * as api from "../../infrastructure/questionService.js";

export default function UpdateForm({updDisplayed, setUpdDisplayed, id}){

    const [updateData, setUpdateData] = useState({
        id: 0,
        answered: false,
        content: ""
    });

    const {question} = useQDetails(id);

    const hiddenUpd = () =>{
        setUpdDisplayed(false);
    }

    useEffect(() => {
        if (question){
            console.log(question);

            // eslint-disable-next-line react-hooks/set-state-in-effect
            setUpdateData({
                id: question.id,
                answered: question.answered,
                content: question.content
            });
        }
    }, [question]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // ✅ Utilisation d'un appel direct au service (Pragmatique & Réaliste)
            const response = await api.update(id, {
                content: updateData.content,
                answered: updateData.answered
            });

            console.log("Mise à jour réussie : ", response.data);

            // Fermer le formulaire après succès
            setUpdDisplayed(false);

            alert("Question modified successfully !");
            window.location.reload();

        } catch (error) {
            console.error("Erreur lors de la mise à jour :", error);
        }
    }
    const handleChange = (e) => {
        const { name, value } = e.target;

        setUpdateData({
            ...updateData,
            [name]: name === "answered" ? (value === "true") : value,
        })
    }

    const  handleDelete = async (id) => {
        // console.log("ok delete: ", id);
        try {
            if (!id) return;

            const deleted = await api.deleteQuestion(id);

            if (deleted) {
                alert("Question deleted successfully!");
            }

            window.location.reload();
        }catch(err){
            console.error("Error: ", err);
        }
    }

    return(
        <section className={`${updDisplayed ? "update " : "hiddenUpd"}`}>
            <div className="up">
                <button type="button" className="back" onClick={hiddenUpd}>BACK</button>
            </div>
            <form className="formUpdate" onSubmit={handleSubmit}>
                <div className="area">
                    <label htmlFor="content">Update its content</label>
                    <textarea
                        name="content"
                        id="content"
                        className="content"
                        value={updateData.content}
                        onChange={handleChange}
                    ></textarea>
                </div>

                <div className="put">
                    <p>It is answered:</p>
                    <div className="choise">
                        <input
                            type="radio"
                            name="answered"
                            id="yes"
                            value="true"
                            checked={updateData.answered === true}
                            onChange={handleChange}
                        />
                        <label htmlFor="yes">Yes</label>
                    </div>
                    <div className="choise">
                        <input
                            type="radio"
                            name="answered"
                            id="no"
                            value="false"
                            checked={updateData.answered === false}
                            onChange={handleChange}
                        />
                        <label htmlFor="no">No</label>
                    </div>
                </div>

                <div className="cta">
                    <button type="button" className="del" onClick={() => handleDelete(id)}>
                        <img src={delIcon} alt="Del icon"/>
                        <p>DELETE IT</p>
                    </button>
                    <button type="submit" className="updBtn"><p>MODIFY IT</p></button>
                </div>
            </form>
        </section>
    );
}