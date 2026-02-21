import './Home.css';
import checkIcon from '../../../../../assets/check_circle.png';
import {useEffect, useState} from "react";
import Header from "../../componant/Header.jsx";
import AddForm from "../../componant/AddForm.jsx";
import UpdateForm from "../../componant/UpdateForm.jsx";
import useQuestion from "../../../application/useQuestion";

export default function Home(){
    const {list} = useQuestion();
    const filters = ['ALL', 'DONE', 'NOT'];

    // 1. Déclarations des states d'abord
    const [displayed, setDisplayed] = useState(false);
    const [updDisplayed, setUpdDisplayed] = useState(false);
    const [activeFilter, setActiveFilter] = useState("ALL");
    const [selectedId, setSelectedId] = useState(null);

    // 2. Calcul sécurisé (On s'assure que list est TOUJOURS traité comme un tableau)
    const safeList = Array.isArray(list) ? list : [];

    const filteredList = safeList.filter((item) => {
        if (!item) return false; // Protection supplémentaire
        if (activeFilter === 'ALL') return true;
        if (activeFilter === 'DONE') return item.answered === true;
        if (activeFilter === 'NOT') return item.answered === false;
        return true;
    });

    const showUpd = (id) => {
        setSelectedId(id);
        setUpdDisplayed(true);
    }

    useEffect(()=>{
        console.log(list);
    }, [list]);

    return (
        <section className="home">
            <AddForm displayed={displayed} setDisplayed={setDisplayed}/>
            <UpdateForm updDisplayed={updDisplayed} setUpdDisplayed={setUpdDisplayed} id = {selectedId}/>
            <Header setDisplayed={setDisplayed}/>
            <section className="hero">
                <h1>Question Here</h1>
                <ul className="filterZone">
                    {filters.map((f) => (
                        <li key={f}>
                            <button className={`${f.toLowerCase()} ${activeFilter === f ? 'active' : ''}`} onClick={() => setActiveFilter(f)}>
                                {f}
                            </button>
                        </li>
                    ))}
                </ul>
            </section>
            <section className="screen">
                <ul>
                    {filteredList.length > 0 ? (
                        filteredList.map((f) => (
                            <li key={f.id} onClick={() => showUpd(f.id)}>
                                <button type="button" className={f.answered ? 'check active' : 'check'}>
                                    <img src={checkIcon} alt="" className="answer"/>
                                </button>
                                <p>{f.content}</p>
                            </li>
                        ))
                    ) : (
                        <p className="empty-msg">No questions found for this category.</p>
                    )}
                </ul>
            </section>
        </section>
    );
}