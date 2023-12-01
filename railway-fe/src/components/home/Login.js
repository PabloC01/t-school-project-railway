import React, { useState } from "react"
import { Navigate } from "react-router-dom"
import { useAuth } from '../context/AuthContext'

function Login() {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const [isError, setIsError] = useState(false);

    const Auth = useAuth()

    const handleSubmit = async e => {
        e.preventDefault()
        
        await fetch(`${process.env.REACT_APP_URL}/api/v1/auth/login`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(response => {
            if(response.ok){
                response.json()
                .then(json => {
                    Auth.userLogin(json)

                    setUsername('')
                    setPassword('')
                    setIsError(false)

                    if(json.role === "C"){
                        window.location.href = "/client"
                    }
                    else{
                        window.location.href = "/employee"
                    }
                })
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
                        <div className="pb-3 d-grid">
                            <button type="submit" className="btn btn-lg" style={{backgroundColor: "#c7d2f0"}}>Login</button>
                            {isError && <span>The username or password provided are incorrect!</span>}
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login