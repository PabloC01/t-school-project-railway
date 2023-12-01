import React, { useState } from "react"
import { Navigate } from "react-router-dom"
import { useAuth } from '../context/AuthContext'

function Login() {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [name, setName] = useState();
    const [surname, setSurname] = useState();
    const [birthDate, setBirthDate] = useState();
    const [isError, setIsError] = useState(false);

    const Auth = useAuth()

    const handleSubmit = async e => {
        e.preventDefault();

        await fetch(`${process.env.REACT_APP_URL}/api/v1/auth/sing-up`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password,
                role: "C",
                name: name,
                surname: surname,
                birthDate: birthDate
            })
        })
        .then(response => {
            if(response.ok){
                response.json()
                .then(json => {
                    Auth.userLogin(json)
    
                    setUsername('')
                    setPassword('')
                    setName('')
                    setSurname('')
                    setBirthDate('')
                    setIsError(false)
                });
            }
            else {
                setIsError(true)
            }
        })
        .catch(() => window.location.href = "/error")
    }

    if(Auth.userIsAuthenticated()){
        return <Navigate to={'/'} />
    }

    return (
        <div>
            <div style={{position:"absolute", width: "100%", height: "94vh", backgroundImage: "url(/home_bg.jpg)", backgroundRepeat: "no-repeat", backgroundSize: "cover", backgroundAttachment: "fixed", filter: "blur(6px)"}}></div>
            <div className="container pt-5">
                <div className="row row-cols-auto justify-content-md-center">
                    <form className="border border-black border-2 rounded-3" style={{backgroundColor: "#FFFFFF", filter: "blur(0px)"}} onSubmit={handleSubmit}>
                        <div className="mb-3 pt-2">
                            <label className="form-label">Username</label>
                            <input type="text" className="form-control form-control-lg" onChange={e => setUsername(e.target.value)} required/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Password</label>
                            <input type="password" className="form-control form-control-lg" onChange={e => setPassword(e.target.value)} required/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Name</label>
                            <input type="text" className="form-control form-control-lg" onChange={e => setName(e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Surname</label>
                            <input type="text" className="form-control form-control-lg" onChange={e => setSurname(e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Birth Date</label>
                            <input type="date" className="form-control form-control-lg" onChange={e => setBirthDate(e.target.value)}/>
                        </div>
                        <div className="pb-3 d-grid">
                            <button type="submit" className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Sing Up</button>
                            {isError && <span>The username is taken!</span>}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login