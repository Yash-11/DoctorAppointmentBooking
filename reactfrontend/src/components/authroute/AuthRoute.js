import { Navigate } from 'react-router-dom';
import { useContext } from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext'; 

const AuthRoute = ({role,  children }) => {

  if (localStorage.getItem('token') === null) {
    return <Navigate to="/login" />;
  }

  if (localStorage.getItem('role')!=role) {
    return <Navigate to="/login" />;
  }

  return children;
};

export default AuthRoute;
