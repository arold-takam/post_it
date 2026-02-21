
import addIcon from '../../../../assets/add_icon.png';

export default function Header({setDisplayed}) {
    const showAdd = () =>{
        setDisplayed(true);
    }

    return (
        <header>
            <a href="#" className="logo">POST IT</a>
            <button type="button" className={"addQuestion"} onClick={showAdd}>
                <p>ADD</p>
                <img src={addIcon} alt="Add Icon"/>
            </button>
        </header>
    );
}