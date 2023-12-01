import React, { createContext, useContext, useState, useEffect } from 'react'

const AuthContext = createContext()

function AuthProvider({ children }) {
  const [user, setUser] = useState(null)

  useEffect(() => {
    const storedUser = JSON.parse(sessionStorage.getItem('user'))
    setUser(storedUser)
  }, [])

  const getUser = () => {
    return JSON.parse(sessionStorage.getItem('user'))
  }

  const userIsAuthenticated = () => {
    return sessionStorage.getItem('user') !== null
  }

  const userLogin = user => {
    sessionStorage.setItem('user', JSON.stringify(user))
    setUser(user)
  }

  const userLogout = () => {
    sessionStorage.removeItem('user')
    setUser(null)
  }

  const contextValue = {
    user,
    getUser,
    userIsAuthenticated,
    userLogin,
    userLogout,
  }

  return (
    <AuthContext.Provider value={contextValue}>
      {children}
    </AuthContext.Provider>
  )
}

export default AuthContext

export function useAuth() {
  return useContext(AuthContext)
}

export { AuthProvider }