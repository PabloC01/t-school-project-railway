import React from "react"
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './components/context/AuthContext'
import Home from './components/home/Home'
import Login from './components/home/Login'
import SingUp from './components/home/SingUp'
import ClientPage from './components/client/ClientPage'
import EmployeePage from './components/employee/EmployeePage'
import Navbar from './components/misc/Navbar'
import BuyTicket from "./components/client/BuyTicket"
import Profile from "./components/client/Profile"
import ErrorPage from "./components/misc/ErrorPage"

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
          <Navbar />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path="/login" element={<Login />}/>
            <Route path="/sing-up" element={<SingUp />}/>
            <Route path="/client" element={<ClientPage />}/>
            <Route path="/buy-ticket" element={<BuyTicket />}/>
            <Route path="/profile" element={<Profile />}/>
            <Route path="/employee" element={<EmployeePage />}/>
            <Route path="/error" element={<ErrorPage />}/>
          </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
