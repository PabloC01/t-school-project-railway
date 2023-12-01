import React from "react"
import { Link, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function Navbar(){
    const Auth = useAuth()
    const user = Auth.getUser()

    const location = useLocation()

    const logout = () => {
        Auth.userLogout()
    }

    const notLoggedStyle = () => {
        return user ? { "display": "none" } : { "display": "block" }
    }

    const loggedStyle = () => {
        return user ? { "display": "block" } : { "display": "none" }
    }

    const clientStyle = () => {
        return user && user.role === 'C' ? { "display": "block" } : { "display": "none" }
    }

    const employeeStyle = () => {
        return user && user.role === 'E' ? { "display": "block" } : { "display": "none" }
    }

    return (
        <div style={{minWidth: location.pathname === "/employee" ? "1450px" : "0px"}}>
            <nav className="navbar navbar-expand-lg" data-bs-theme="dark" style={{backgroundColor: "#0a2171"}}>
                <div className="container-fluid">
                    <span className="navbar-brand mb-0 h1">Railway</span>
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <Link className="nav-link active" aria-current="page" exact="true" to="/" style={notLoggedStyle()}>Home</Link>
                        <Link className="nav-link" aria-current="page" to="/client" style={clientStyle()}>Search schedules</Link>
                        <Link className="nav-link" aria-current="page" to="/employee" style={employeeStyle()}>Administration</Link>
                    </ul>
                    <ul className="navbar-nav mb-2 mb-lg-0">
                        <Link className="nav-link" to="/login" style={notLoggedStyle()}>Login</Link>
                        <Link className="nav-link" to="/sing-up" style={notLoggedStyle()}>Sing Up</Link>
                        <Link className="nav-link" to="/profile" style={clientStyle()}>Profile</Link>
                        <Link className="nav-link" style={loggedStyle()} onClick={logout}>Logout</Link>
                    </ul>
                </div>
            </nav>
        </div>
    )
}

export default Navbar